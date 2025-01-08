package com.springboot.entrename.api.hour;

import com.springboot.entrename.domain.hour.HourEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HourAssembler {
    public HourDto toHourResponse(HourEntity hourEntity) {
        return HourDto.builder()
            .id_hour(hourEntity.getIdHour())
            .slot_hour(hourEntity.getSlotHour())
            .build();
    }
}
