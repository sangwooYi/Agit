package com.ssafy.api.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.ssafy.api.dto.QEventResDto is a Querydsl Projection type for EventResDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QEventResDto extends ConstructorExpression<EventResDto> {

    private static final long serialVersionUID = 845867599L;

    public QEventResDto(com.querydsl.core.types.Expression<String> eventTitle, com.querydsl.core.types.Expression<String> eventContent, com.querydsl.core.types.Expression<String> teamName, com.querydsl.core.types.Expression<java.time.LocalDate> startDate, com.querydsl.core.types.Expression<java.time.LocalDate> endDate, com.querydsl.core.types.Expression<Long> dDay, com.querydsl.core.types.Expression<Long> teamId) {
        super(EventResDto.class, new Class<?>[]{String.class, String.class, String.class, java.time.LocalDate.class, java.time.LocalDate.class, long.class, long.class}, eventTitle, eventContent, teamName, startDate, endDate, dDay, teamId);
    }

}

