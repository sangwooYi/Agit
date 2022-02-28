package com.ssafy.api.dto;


import com.ssafy.db.entity.Event;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("EventDto")
public class EventDto {
    Long id;
    String eventTitle;
    String eventContent;
    String teamName;

    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime createDate;
    LocalDateTime updateDate;

    public Event toEntity(){
        Event build = Event.builder()
                .eventTitle(eventTitle)
                .eventContent(eventContent)
                .startDate(startDate)
                .endDate(endDate)
                .teamName(teamName)
                .build();
        return build;
    }

    @Builder
    public EventDto(Event event){
        this.id = event.getId();
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.teamName = event.getTeamName();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.createDate = event.getCreatedDate();
        this.updateDate = event.getUpdatedDate();
    }
}
