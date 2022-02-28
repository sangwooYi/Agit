package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "UserTeam",
        uniqueConstraints={
        @UniqueConstraint(
                columnNames={"team_id","user_id"}
        )
})
public class UserTeam extends BaseEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    //1 활성화, 0 비활성화
    int state=1;


    public void setTeam(Team team){
        this.team = team;
        if (team!=null&&!team.getUserTeams().contains(this)){
            team.getUserTeams().add(this);
        }
    }

    public void setUser(User user){
        this.user = user;
        if (user!=null&&!user.getUserTeams().contains(this)){
            user.getUserTeams().add(this);
        }
    }

}
