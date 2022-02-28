package com.ssafy.db.repository;


import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
}
