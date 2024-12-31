package com.springboot.entrename.domain.inscription;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.activity.ActivityService;
import com.springboot.entrename.api.inscription.InscriptionDto;
import com.springboot.entrename.api.notification.NotificationDto;
import com.springboot.entrename.domain.notification.NotificationService;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.exception.NotificationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InscriptionSagaServiceImpl implements InscriptionSagaService {
    private final UserService userService;
    private final ActivityService activityService;
    private final InscriptionService inscriptionService;
    private final NotificationService notificationService;
    private UserEntity user;
    private ActivityEntity activity;
    private InscriptionEntity inscription;

    @Transactional
    @Override
    public InscriptionEntity createInscriptionSaga(final InscriptionDto data) {
        try {
            // Paso 1: Obtiene el usuario actual
            user = userService.getCurrentUser();

            // Paso 2: Comprueba si el usuario ya está inscrito en la actividad
            Integer userInscriptionCount = inscriptionService.getInscriptionsCountByUserAndActivityAndStates(
                user.getIdUser(),
                data.getId_activity(),
                List.of(1, 2));
            if (userInscriptionCount > 0) {
                throw new AppException(Error.USER_ALREADY_INSCRIBED);
            }

            // Paso 3: Valida la disponibilidad de plazas en la actividad
            activity = activityService.getActivityById(data.getId_activity());
            if (activity.getSpots_available() <= 0) {
                throw new AppException(Error.ACTIVITY_NOT_AVAILABLE);
            }

            // Paso 4: Crea la inscripción a la actividad en estado pendiente (state = 1)
            inscription = inscriptionService.createInscription(activity, user);

            // Paso 5: Reduce en 1 las plazas disponibles en la actividad
            activityService.updateSpotsAvilableActivity(activity, -1);

            // Paso 6: Notifica al profesor
            NotificationDto instructorNotification = notificationService.buildNotification(
                activity.getIdUserInstructor().getEmail(),
                "Nueva inscripción en " + activity.getNameActivity(),
                "instructor",
                user,
                activity,
                inscription,
                null);
            notificationService.sendNotification(instructorNotification);

            // Paso 7: Confirma la inscripción (state = 2)
            inscriptionService.updateInscriptionStatus(inscription, 2);
            
            // Paso 8: Notifica al usuario
            NotificationDto clientNotification = notificationService.buildNotification(
                user.getEmail(),
                "Confirmación inscripción en " + activity.getNameActivity(),
                "client",
                user,
                activity,
                inscription,
                null);
            notificationService.sendNotification(clientNotification);

            return inscription;

        } catch (Exception error) {
            // Compensación: Libera plazas, marcar inscripción como eliminada, notifica al administrador
            if (error instanceof NotificationException) {
                handleCompensation(user, activity, inscription, error);
            }

            throw error;
        }
    }

    @Transactional
    private void handleCompensation(UserEntity user, ActivityEntity activity, InscriptionEntity inscription, Exception error) {
        try {
            // Libera plazas disponibles en la actividad
            activityService.updateSpotsAvilableActivity(activity, 1);

            // Marca la inscripción como eliminada (state = -1)
            inscriptionService.updateInscriptionStatus(inscription, -1);

            // Notifica al administrador
            UserEntity admin = userService.getAdminUser();

            NotificationDto adminNotification = notificationService.buildNotification(
                admin.getEmail(),
                "Fallo durante la inscripción en " + activity.getNameActivity(),
                "admin",
                user,
                activity,
                inscription,
                error.getMessage());
            notificationService.sendNotification(adminNotification);

        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR);
        }
    }
}
