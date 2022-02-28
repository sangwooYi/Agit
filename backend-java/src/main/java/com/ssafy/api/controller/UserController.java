package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CTokenForbiddenException;
import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.advice.exception.CUserNotFoundException;
import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;

import com.ssafy.db.entity.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;
import springfox.documentation.annotations.ApiIgnore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 * 주석한줄(윤희영)
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;


	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "아이디, PW, 닉네임, 생일, 이메일에 대한 정보를 필수로 전달 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 1000, message = "사용자 중복"),
        @ApiResponse(code = 500, message = "서버 오류")
    })

	public ResponseEntity<? extends BaseResponseBody> register (@RequestBody @ApiParam(value="회원가입 정보", required = true) UserDto userDto) throws Exception {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		userDto.setEmailType(EmailType.Local);
		try {
			userService.createUser(userDto);
		} catch (DataIntegrityViolationException e) {
			//회원중복시 예외처리
			throw new CUserDuplicateException();
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}


	@GetMapping("/{userId}")
	@ApiOperation(value = "아이디중복체크", notes = "아이디 존재 유무를 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 1000, message = "이미 존재하는 ID"),
			@ApiResponse(code = 200, message = "사용할 수 있는 ID")
	})
	public ResponseEntity<? extends BaseResponseBody> validateId(@ApiParam(value = "userId", required = true) @PathVariable("userId") String userId) {
		User user = userService.getUserByUserId(userId);
		// 아이디의 유저가 존재한다면 사용 불가능한 ID
		if (user != null) {
			throw new CUserDuplicateException();
		}

		// 해당 아이디의 유저가 존재하지 않는다면 사용 가능한 ID
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용할 수 있는 ID"));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })

	public ResponseEntity<UserDto> getUserInfo(@ApiIgnore Authentication authentication){

		UserDto userDto;
		try{
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			String userId = userDetails.getUsername();
			User user = userService.getUserByUserId(userId);
			userDto =  new UserDto(user);
		}catch(Exception e){
			//잘못된 접근일때
			throw new CTokenForbiddenException();
		}

		return ResponseEntity.status(200).body(userDto);
	}


	@PatchMapping()
	public ResponseEntity<? extends  BaseResponseBody> updateUser(@RequestBody @ApiParam(value="회원 정보 수정", required = true)UserDto userDto){
		if(!userService.updateUserByUserId(userDto.getUserId(), userDto)){
			//수정하고자 하는 회원이 없을때 예외처리
			throw new CUserNotFoundException();
		};
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));

	}

	@PostMapping("/event/{teamId}")
	public ResponseEntity<? extends BaseResponseBody> addEvent(@RequestBody @ApiParam(value="기념일 등록", required = true) EventDto eventDto,
															   @PathVariable("teamId") Long teamId){

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@ApiIgnore Authentication authentication){
		String userId;
		try{
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			userId = userDetails.getUsername();
		}catch(Exception e){
			//잘못된 접근일때
			throw new CTokenForbiddenException();
		}

		if(!userService.deleteUserByUserId(userId)){
			//삭제하고자 하는 회원이 없을때 예외처리
			throw new CUserNotFoundException();
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
	}

	@GetMapping(path="profileimg/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("userId") Long userId) throws IOException {
		String filePath="";
		if(userId==0){
			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\dist\\img\\"+ "defaultprofile.PNG";
		}else{
			User user = userService.getUserById(userId);

			filePath = System.getProperty("user.home") + "\\files"+"\\userProfile"+ File.separator + user.getProfileImg();
		}


		byte[] returnValue = null;
		ByteArrayOutputStream baos =null;
		FileInputStream fis = null;

		try{
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(filePath);

			byte[] buf = new byte[1024];
			int read = 0;

			while((read=fis.read(buf,0,buf.length))!=-1){
				baos.write(buf,0,read);
			}

			returnValue = baos.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(baos!=null){
				baos.close();
			}

			if(fis!=null){
				fis.close();
			}
		}
		return ResponseEntity.status(200).body(returnValue);
	}

	@PostMapping("/image")
	@ApiOperation(value = "유저 프로필 이미지 생성", notes = "유저 프로필 이미지 변경")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증실패"),
	})

	public ResponseEntity<? extends BaseResponseBody> register (
			@RequestParam(value="upfile", required = false) MultipartFile file, @ApiIgnore Authentication authentication) throws Exception {
		UserDto userDto = new UserDto();
		String userId = "";
		User user=null;
		try{
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			userId = userDetails.getUsername();
			user = userService.getUserByUserId(userId);
		}catch (DataIntegrityViolationException e) {
			//회원중복시 예외처리
			throw new CUserDuplicateException();
		} catch(Exception e){
			//잘못된 접근일때
			throw new CTokenForbiddenException();
		}

		String realPath = System.getProperty("user.home") + "\\files" + "\\userProfile";
		String saveFolder = realPath;

		File folder = new File(saveFolder);
		if(!folder.exists()) folder.mkdirs();

		String originalFileName = file.getOriginalFilename();
		String saveFileName = UUID.randomUUID().toString().replace("-", "") + originalFileName.substring(originalFileName.lastIndexOf('.'));
		System.out.println(saveFileName);

		user.setProfileImg(saveFileName);
		file.transferTo(new File(folder, saveFileName));

		userService.updateUserProfileByUserId(userId, user);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping("/teamList")
	@ApiOperation(value = "유저가 가입된 팀 리스트", notes = "JWT 토큰을 통해 조회")
	@ApiResponses({
			@ApiResponse(code = 1000, message = "이미 존재하는 ID"),
			@ApiResponse(code = 200, message = "사용할 수 있는 ID")
	})
	public ResponseEntity<List<TeamDto>> teamListUserJoined(@ApiIgnore Authentication authentication){

		Long userId;
		try{
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			userId = userDetails.getUser().getId();
		}catch(Exception e){
			//잘못된 접근일때
			throw new CTokenForbiddenException();
		}

		List<TeamDto> teamDtoList = userService.getTeamListUserJoined(userId);

		return ResponseEntity.status(200).body(teamDtoList);

	}
}
