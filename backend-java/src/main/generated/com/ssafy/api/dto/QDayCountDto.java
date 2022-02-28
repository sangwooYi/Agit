package com.ssafy.api.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.ssafy.api.dto.QDayCountDto is a Querydsl Projection type for DayCountDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QDayCountDto extends ConstructorExpression<DayCountDto> {

    private static final long serialVersionUID = -373305342L;

    public QDayCountDto(com.querydsl.core.types.Expression<Long> count, com.querydsl.core.types.Expression<java.time.LocalDate> uploadDate) {
        super(DayCountDto.class, new Class<?>[]{long.class, java.time.LocalDate.class}, count, uploadDate);
    }

}

