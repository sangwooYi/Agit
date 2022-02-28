package com.ssafy.api.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;

import com.ssafy.db.entity.EmailType;
import com.ssafy.db.entity.Team;
import com.ssafy.db.repository.UserTeamRepositroySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	private final NetHttpTransport transport = new NetHttpTransport();
	private final JsonFactory jsonFactory = new GsonFactory();
	@Autowired
	UserTeamRepositroySupport userTeamRepositroySupport;

	@Override
	public boolean createUser(UserDto userDto){
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		if(userDto.getPassword()!=null){
			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		userRepository.save(userDto.toEntity()).getId();
		return true;
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = null;
		if(userRepositorySupport.findUserByUserId(userId).isPresent()) {
			user = userRepositorySupport.findUserByUserId(userId).get();
		}

		return user;
	}

	public boolean  updateUserByUserId(String userId, UserDto userDto){
		User user = getUserByUserId(userId);
		if(user==null){
			//해당유저가 존재하지 않을때
			return false;
		}
		user.setNickName(userDto.getNickName());
		user.setBirthDay(userDto.getBirthDay());
		userRepository.save(user);
		return true;
	}

	public boolean deleteUserByUserId(String userId){
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = getUserByUserId(userId);
		if(user==null){
			//해당유저가 존재하지 않을때.
			return false;
		}

		userRepository.delete(user);
		return true;
	}

	@Override
	public UserDto tokenVerify(String idToken) {

		System.out.println("idToken : " + idToken);

		GoogleIdTokenVerifier gitVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
				.setAudience(Arrays.asList("1013528004085-99200t264rmtj2ef3m5ett7ogua9nuh6.apps.googleusercontent.com"))
				.build();

		GoogleIdToken git = null;

		try {
			git = gitVerifier.verify(idToken);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserDto user=null;
		if (git == null) {
			System.out.println("Google ID Token is invalid");
		}else {
			GoogleIdToken.Payload payload = git.getPayload();
			user = new UserDto();
			// Print user identifier & Get profile information from payload
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);
			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name");

			user.setUserId(email);
			user.setName(name);
			user.setEmailType(EmailType.Google);
			user.setNickName(name);
			user.setProfileImg(pictureUrl);

			System.out.println(user.getUserId());
			System.out.println(user.getName());
			System.out.println(user.getNickName());
			System.out.println(pictureUrl);
			System.out.println(user.getProfileImg());

		}
		return user;
	}
	public List<TeamDto> getTeamListUserJoined(Long userId){
			List<Team> teamList = userTeamRepositroySupport.findTeamListByUserId(userId).get();
			List<TeamDto> teamDtoList = new ArrayList<>();
			for (Team team : teamList) {
				TeamDto teamDto = new TeamDto(team);
				teamDtoList.add(teamDto);

		}
		return teamDtoList;
	}

	@Override
	public User getUserById(Long userId) {
		User user = userRepository.findById(userId).get();
		return user;
	}

	@Override
	public void updateUserProfileByUserId(String userId, User user) {
		userRepository.save(user);
	}


}
