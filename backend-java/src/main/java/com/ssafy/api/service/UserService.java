package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	boolean createUser(UserDto userDto);
	User getUserByUserId(String userId);
	boolean updateUserByUserId(String userId, UserDto userDto);
	boolean deleteUserByUserId(String userId);

	//googleToken확인
	UserDto tokenVerify(String idToken);
	List<TeamDto> getTeamListUserJoined(Long userId);

	User getUserById(Long userId);

	void updateUserProfileByUserId(String userId, User user);
}
