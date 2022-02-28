package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.UserTeam;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    boolean createTeam(TeamDto teamDto, String userId);
    boolean addMember(Long teamId, String userId);
    List<UserDto> userListInTeam(Long teamId);
    List<UserDto> userListInTeam2(Long teamId);

    TeamDto getTeamById(Long teamId);

    Optional<UserTeam> getTeamMemberState(Long teamId, String userId);

    void changeTeamMemberConfirm(Long teamId, String userId);

    void changeTeamMemberReject(Long teamId, String userId);

    boolean checkStateIfTeamPossible(Long teamId);

    void changeConfStateToTrue(Long teamId);
    void changeConfStateToFalse(Long teamID);

}
