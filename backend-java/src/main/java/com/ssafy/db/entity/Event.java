package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="event")
@EntityListeners(AuditingEntityListener.class)
public class Event extends BaseEntity{

    @Column(columnDefinition="varchar(100)")
    String eventTitle;
    String eventContent;
    String teamName;

    LocalDate startDate;
    LocalDate endDate;

    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 연관 관계 메소드
    public void setTeam(Team team){
        this.team = team;
        if (!team.getEvents().contains(this)){
            team.getEvents().add(this);
        }
    }


    public Event(){};

    @Builder
    public Event(Long id, String eventTitle, String eventContent, String teamName, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.teamName = teamName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
