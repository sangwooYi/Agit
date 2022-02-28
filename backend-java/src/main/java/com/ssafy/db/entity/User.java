package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(
        name = "USER_ID_UNIQUE", columnNames = {"userId"}
)})
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String userId;
    String name;
    String nickName;
    //생일
    LocalDate birthDay;
    String profileImg;

    @Enumerated(EnumType.STRING)
    private EmailType emailType;

//    int year;
//    int month;
//    int day;
    
    @CreatedDate
    LocalDateTime cDate;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;


    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();
    // 관계 메서드


    public void addArticle(Article article){
        this.articles.add(article);
        if (article.getUser() != this){
            article.setUser(this);
        }
    }

    @OneToMany(mappedBy = "user")
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image){
        this.images.add(image);
        if(image.getUser() != this){
            image.setUser(this);
        }
    }

    @OneToMany(mappedBy = "user")
    private List<Video> videos = new ArrayList<>();

    private void addVideos(Video video){
        this.videos.add(video);
        if(video.getUser() != this){
            video.setUser(this);
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTeam> userTeams = new ArrayList<>();

    private void addUserTeams(UserTeam userTeam){
        this.userTeams.add(userTeam);
        if(userTeam.getUser() != this){
            userTeam.setUser(this);
        }
    }

    // 일정-유저 다대다 관계 보류
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
//    private List<UserEvent> userEvents = new ArrayList<>();
//
//    private void addUserEvents(UserEvent userEvent){
//        this.userEvents.add(userEvent);
//        if(userEvent.getUser() != this){
//            userEvent.setUser(this);
//        }
//    }

    public User(){}

    @Builder
    public User(Long id, String userId, String name, String nickName, LocalDate birthDay, String password, EmailType emailType, String profileImg){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.birthDay = birthDay;
        this.nickName = nickName;
        this.password = password;
        this.emailType = emailType;
        this.profileImg = profileImg;
    }

}