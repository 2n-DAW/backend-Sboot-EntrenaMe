package com.springboot.entrename.api.inscription;

import com.springboot.entrename.domain.inscription.InscriptionService;
import com.springboot.entrename.domain.inscription.InscriptionSagaService;
import com.springboot.entrename.api.security.authorization.CheckSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {
    private final InscriptionService inscriptionService;
    private final InscriptionSagaService inscriptionSagaService;
    private final InscriptionAssembler inscriptionAssembler;

    @GetMapping
    @CheckSecurity.Protected.canManage
    public InscriptionDto.InscriptionWrapper getAllInscriptions() {
        var inscriptions = inscriptionService.getAllInscriptions();
        return inscriptionAssembler.toInscriptionsList(inscriptions);
    }

    @GetMapping("/detailed")
    @CheckSecurity.Protected.canManage
    public InscriptionDto.InscriptionWrapper getAllDetailedInscriptions() {
        var inscriptions = inscriptionService.getAllInscriptions();
        return inscriptionAssembler.toInscriptionsListWithUserAndActivity(inscriptions);
    }

    @GetMapping("/{slug}")
    @CheckSecurity.Protected.canManage
    public InscriptionDto getInscription(@PathVariable String slug) {
        var inscription = inscriptionService.getInscription(slug);
        return inscriptionAssembler.toInscriptionResponse(inscription);
    }

    @GetMapping("/detailed/{slug}")
    @CheckSecurity.Protected.canManage
    public InscriptionDto getDetailedInscription(@PathVariable String slug) {
        var inscription = inscriptionService.getInscription(slug);
        return inscriptionAssembler.toInscriptiongWithUserAndActivityResponse(inscription);
    }

    @GetMapping("/user")
    @CheckSecurity.Protected.canManage
    public InscriptionDto.InscriptionWrapper getAllInscriptionsFromUser() {
        var inscription = inscriptionService.getAllInscriptionsFromUser();
        return inscriptionAssembler.toInscriptionsListWithUserAndActivity(inscription);
    }

    @PostMapping("/create")
    @CheckSecurity.Protected.canManage
    public InscriptionDto createInscriptionSaga(@RequestBody InscriptionDto data) {
        var inscription = inscriptionSagaService.createInscriptionSaga(data);
        return inscriptionAssembler.toInscriptiongWithUserAndActivityResponse(inscription);
    }

    @PutMapping("/delete/{slugInscription}")
    @CheckSecurity.Inscriptions.canDelete
    public InscriptionDto deleteInscription(@PathVariable String slugInscription) {
        var inscription = inscriptionService.deleteInscription(slugInscription);
        return inscriptionAssembler.toInscriptiongWithUserAndActivityResponse(inscription);
    }
}
