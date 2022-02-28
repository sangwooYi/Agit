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
@Table(name="video")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Video extends BaseEntity {

    String originalFileName;
    String filePath;
    String fileName;

    @CreatedDate
    LocalDateTime createDate;
    @LastModifiedDate
    LocalDateTime updateDate;
    LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void setUser(User user){
        this.user = user;
        if(!user.getVideos().contains(this)){
            user.getVideos().add(this);
        }
    }

    public void setTeam(Team team){
        this.team = team;
        if(!team.getVideos().contains(this)){
            team.getVideos().add(this);
        }
    }

    public Video(){}

    @Builder
    public Video(Long id, String originalFileName, String filePath, String fileName, LocalDate uploadDate){
        this.id = id;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
    }

}
