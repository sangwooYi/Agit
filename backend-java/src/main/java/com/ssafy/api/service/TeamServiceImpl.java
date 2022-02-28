package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;

import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserTeam;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserTeamRepository userTeamRepository;

    @Autowired
    UserTeamRepositroySupport userTeamRepositroySupport;

    @Override
    public boolean createTeam(TeamDto teamDto, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        if (user == null){
            return false;
        }
        teamDto.setConfState(false);
        Team team = teamDto.toEntity();
        System.out.println(team);
        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user.get());
        userTeam.setTeam(team);

        userTeamRepository.save(userTeam);
        return true;
    }

    @Override
    public boolean addMember(Long teamId, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        if(user == null || team == null){
            return false;
        }
        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user.get());
        userTeam.setTeam(team.get());
        userTeam.setState(0);
        userTeamRepository.save(userTeam);
        return true;
    }

    @Override
    public List<UserDto> userListInTeam(Long teamId) {
        List<Long> userFKs = userTeamRepositroySupport.findUserFKListByTeamId(teamId).get();
        List<UserDto> userDtoList = new ArrayList<>();

        for(Long userFk : userFKs){
            User user = userRepository.findById(userFk).get();
            UserDto userDto = new UserDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Override
    public List<UserDto> userListInTeam2(Long teamId) {
        List<User> userList = userTeamRepositroySupport.findUserListByTeamId(teamId).get();
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user : userList){
            UserDto userDto = new UserDto(user);
            if(userDto.getProfileImg()!=null&&!userDto.getProfileImg().substring(0,4).equals("http")){
                String url = "http://localhost:8080/api/v1/user/profileimg/"+userDto.getId();
                userDto.setProfileImg(url);
            }
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Override
    public TeamDto getTeamById(Long teamId) {
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        TeamDto teamDto = new TeamDto(team.get());
        return teamDto;
    }

    @Override
    public Optional<UserTeam> getTeamMemberState(Long teamId, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<UserTeam> userTeam = null;
        if (user == null){
            return null;
        }else{
            userTeam = userTeamRepositroySupport.findTeamUserStateByTeamIdAndUserId(teamId, user.get().getId());
        }
        return userTeam;
    }

    @Override
    public void changeTeamMemberConfirm(Long teamId, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<UserTeam> userTeam = userTeamRepositroySupport.findTeamUserStateByTeamIdAndUserId(teamId, user.get().getId());

        userTeam.get().setState(1);
        userTeamRepository.save(userTeam.get());
    }

    @Override
    public void changeTeamMemberReject(Long teamId, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        UserTeam userTeam = userTeamRepositroySupport.findTeamUserStateByTeamIdAndUserId(teamId, user.get().getId()).get();
        user.get().getUserTeams().remove(userTeam);
        team.get().getUserTeams().remove(userTeam);
        userTeam.setTeam(null);
        userTeam.setUser(null);


        userTeamRepository.delete(userTeam);
    }

    @Override
    public boolean checkStateIfTeamPossible(Long teamId) {
        List<User> userList = userTeamRepositroySupport.findUserListByTeamId(teamId).get();
        if(userList.size()>=6){
            return false;
        }
        return true;
    }

    @Override
    public void changeConfStateToTrue(Long teamId) {
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        team.setConfState(true);
        teamRepository.save(team);
    }

    @Override
    public void changeConfStateToFalse(Long teamId) {
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        team.setConfState(false);
        teamRepository.save(team);
    }

}
