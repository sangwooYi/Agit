package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "team")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Team extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String teamName; //팀명

    @CreatedDate
    LocalDateTime teamCdate; //생성일
    String teamDescription; //한줄소개
    String teamPicture; // 팀 썸네일
    String teamBoss; //팀장 이름

    Boolean confState; // 회의 개설 여부

    public Team(){}

    @OneToMany(mappedBy = "team")
    private List<Article> articles = new ArrayList<>();

    // 관계 메서드
    private void addArticle(Article article){
        this.articles.add(article);
        // 현재 article의 team FK 값이 this가 아니면 다시 세팅
        if (article.getTeam() != this) {
            article.setTeam(this);
        }
    }


    @OneToMany(mappedBy = "team")
    private List<Image> images = new ArrayList<>();

    private void addImage(Image image){
        this.images.add(image);
        if(image.getTeam() != this){
            image.setTeam(this);
        }
    }

    @OneToMany(mappedBy = "team")
    private List<Video> videos = new ArrayList<>();

    private void addVideos(Video video){
        this.videos.add(video);
        if (video.getTeam() != this){
            video.setTeam(this);
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    private List<UserTeam> userTeams = new ArrayList<>();

    private void addUserTeams(UserTeam userTeam){
        this.userTeams.add(userTeam);
        if(userTeam.getTeam() != this){
            userTeam.setTeam(this);
        }
    }

    @OneToMany(mappedBy = "team")
    private List<Event> events = new ArrayList<>();

    private void addEvents(Event event){
        this.events.add(event);
        if(event.getTeam() != this){
            event.setTeam(this);
        }
    }

    @Builder
    public Team(Long id, String teamName,  String teamDescription, String teamPicture,  String teamBoss, Boolean confState){
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamPicture = teamPicture;
        this.teamBoss = teamBoss;
        this.confState = confState;
    }

}