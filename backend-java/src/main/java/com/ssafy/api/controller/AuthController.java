package com.ssafy.api.controller;

import com.ssafy.api.dto.UserDto;
import com.ssafy.api.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserLoginDto
				.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
	public ResponseEntity<UserLoginDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserDto loginInfo) {
		String userId = loginInfo.getUserId();
		String password = loginInfo.getPassword();

		User user = userService.getUserByUserId(userId);
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(UserLoginDto.of(200, "Success", JwtTokenUtil.getToken(userId)));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답
		return ResponseEntity.status(401).body(UserLoginDto.of(401, "Invalid Password", null));
	}

	@PostMapping("/token")
	@ApiOperation(value = "Google OAuth 2.0 토큰", notes = "Google OAuth 2.0 토큰 확인")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginDto
					.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginDto> tokenVerify(@RequestBody UserLoginDto userLoginDto) {
		//google에서 받은 idToken
		System.out.println("RequestBody value : " + userLoginDto.getAccessToken());
		UserDto userDto = userService.tokenVerify(userLoginDto.getAccessToken());

		User user = userService.getUserByUserId(userDto.getUserId());
		if(user==null){
			//회원가입 필요할때
			userService.createUser(userDto);
		}

		return ResponseEntity.ok(UserLoginDto.of(200, "Success", JwtTokenUtil.getToken(userDto.getUserId())));
	}
}
