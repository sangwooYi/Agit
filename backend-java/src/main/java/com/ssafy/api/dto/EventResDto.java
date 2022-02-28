package com.ssafy.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("EventResDto")
public class EventResDto {

    Long dDay;
    Long teamId;
    String eventTitle;
    String eventContent;
    String teamName;
    LocalDate startDate;
    LocalDate endDate;

    @QueryProjection
    public EventResDto(String eventTitle, String eventContent, String teamName, LocalDate startDate, LocalDate endDate, Long dDay, Long teamId){
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.teamName = teamName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dDay = dDay;
        this.teamId = teamId;
    }
}
