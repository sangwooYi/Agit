package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="article")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String title;
    @Column(columnDefinition="varchar(1000)")
    String content;
    String writer;
    String nickName;
    String teamName;
    // 등록일
    LocalDate uploadDate;


    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 연관 관계 메소드
    public void setUser(User user){
        this.user = user;
        if (!user.getArticles().contains(this)){
            user.getArticles().add(this);
        }
    }

    public void setTeam(Team team){
        this.team = team;
        if (!team.getArticles().contains(this)){
            team.getArticles().add(this);
        }
    }

    public Article(){}

    @Builder
    public Article(Long id, String title, String content, String writer, String nickName, String teamName, LocalDate uploadDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        // 작성자 아이디
        this.writer = writer;
        // Team 이름
        this.teamName = teamName;
        this.uploadDate = uploadDate;
    }

}
