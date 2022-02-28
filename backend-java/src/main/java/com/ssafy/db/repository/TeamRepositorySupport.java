package com.ssafy.db.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QTeam;
import com.ssafy.db.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QTeam qTeam = QTeam.team;

    public Optional<Team> findTeamByTeamName(String teamName){
        Team team = jpaQueryFactory.select(qTeam).from(qTeam)
                .where(qTeam.teamName.eq(teamName)).fetchOne();
        if(team == null) return Optional.empty();
        return Optional.ofNullable(team);
    }

    public Optional<Team> findTeamByTeamId(Long teamId){
        Team team = jpaQueryFactory.select(qTeam).from(qTeam)
                .where(qTeam.id.eq(teamId)).fetchOne();
        if(team==null) return Optional.empty();
        return Optional.ofNullable(team);
    }
}
