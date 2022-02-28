package com.ssafy.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("DayCountDto")
public class DayCountDto {
    Long count;
    LocalDate uploadDate;

    @QueryProjection
    public DayCountDto(Long count, LocalDate uploadDate){
        this.count = count;
        this.uploadDate = uploadDate;
    }

}
