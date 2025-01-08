package com.springboot.entrename.api.month;

import com.springboot.entrename.domain.month.MonthEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthAssembler {
    public MonthDto toMonthResponse(MonthEntity monthEntity) {
        return MonthDto.builder()
            .id_month(monthEntity.getIdMonth())
            .n_month(monthEntity.getNameMonth())
            .slug_month(monthEntity.getSlugMonth())
            .build();
    }
}
