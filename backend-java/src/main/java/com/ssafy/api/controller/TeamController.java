package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.*;
import com.ssafy.api.dto.*;

import com.ssafy.api.service.*;
import com.ssafy.db.entity.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
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
import java.util.*;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ImageService imageService;

    @Autowired
    VideoService videoService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    EventService eventService;

    @PostMapping()
    @ApiOperation(value = "팀생성", notes = "팀정보를 통해 팀 생성한다. 로그인 해야 팀 생성 가능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 1000, message = "팀 중복"),
            @ApiResponse(code = 500, message = "서버 오류")
    })

    public ResponseEntity<? extends BaseResponseBody> register (
            @RequestParam(value="teamPicture", required = false) MultipartFile file, @RequestParam(value="teamName") String teamName, @RequestParam(value="teamDescription")String teamDescription, @ApiIgnore Authentication authentication) throws Exception {
            TeamDto teamDto = new TeamDto();
            String userId = "";
        try{
            SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
            userId = userDetails.getUsername();
            User user = userService.getUserByUserId(userId);
            // 디폴트로 team Boss는 팀 생성자로 설정
            teamDto.setTeamBoss(user.getName());
            teamDto.setTeamName(teamName);
            teamDto.setTeamDescription(teamDescription);

        }catch (DataIntegrityViolationException e) {
            //회원중복시 예외처리
            throw new CUserDuplicateException();
        } catch(Exception e){
            //잘못된 접근일때
            throw new CTokenForbiddenException();
        }

        if(file==null){
            teamService.createTeam(teamDto, userId);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        String realPath = System.getProperty("user.home") + "\\files" + "\\teamProfile";
        String saveFolder = realPath;

        File folder = new File(saveFolder);
        if(!folder.exists()) folder.mkdirs();

        String originalFileName = file.getOriginalFilename();
        String saveFileName = UUID.randomUUID().toString().replace("-", "") + originalFileName.substring(originalFileName.lastIndexOf('.'));
        System.out.println(saveFileName);
        teamDto.setTeamPicture(saveFileName);
        file.transferTo(new File(folder, saveFileName));

        teamService.createTeam(teamDto, userId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping(path="profileimg/{teamId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("teamId") Long teamId) throws IOException {
        TeamDto teamDto = teamService.getTeamById(teamId);

        String filePath = System.getProperty("user.home") + "\\files"+"\\teamProfile"+ File.separator + teamDto.getTeamPicture();
        File target = new File(filePath);

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

    @GetMapping("/check/{teamId}")
    @ApiOperation(value = "회원 팀 가입여부 확인", notes = "회원이 팀에 어떤 상태인지를 확인한다. 0 : 비활성화 1: 활성화 2: 접근불가")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 1000, message = "팀 중복"),
            @ApiResponse(code = 500, message = "서버 오류")
    })

    public ResponseEntity<? extends BaseResponseBody> checkTeamMember (
            @PathVariable @ApiParam(value="팀가입 정보", required = true) Long teamId, @ApiIgnore Authentication authentication) throws Exception {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        //해당 유저가 team에 포함되어있는지 확인
        Optional<UserTeam> userTeam = teamService.getTeamMemberState(teamId, userId);
        if(userTeam.isEmpty()){
            System.out.println("접근불가한 User");
            throw new CTokenForbiddenException();
            //return ResponseEntity.status(1002).body(BaseResponseBody.of(1002, "접근불가한 User"));
        }
        //활성화 상태 확인
        if(userTeam.get().getState()==0){
            System.out.println("비활성화 상태");
            throw new CUserInactiveException();
        }
        System.out.println("활성화상태");
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/info/{teamId}")
    @ApiOperation(value = "team정보가져오기", notes = "teamID 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<TeamDto> getTeamInfo(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){
        TeamDto teamdto = teamService.getTeamById(teamId);
        return ResponseEntity.status(200).body(teamdto);
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "team에서 작성한 전체 calendar", notes = "teamID 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<BoardDto> getTeamsBoardList(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){
        BoardDto boardDto = new BoardDto();

        List<ArticleDto> articleDtoList = articleService.getArticleListById(teamId);
        List<ImageDto> imageDtoList = imageService.getImageListById(teamId);
        List<VideoDto> videoDtoList = videoService.getVideoListById(teamId);
        boardDto.setArticleList(articleDtoList);
        boardDto.setImageList(imageDtoList);
        boardDto.setVideoList(videoDtoList);
        return ResponseEntity.status(200).body(boardDto);
    }

    //추가된 회원에게 email을 전송하고
    //해당팀에 회원추가
    @GetMapping("/{teamId}/member/{userId}")
    @ApiOperation(value = "해당 팀에 특정 유저 추가", notes = "teamID, userId 이용하여 추가")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addMember(@PathVariable("teamId") Long teamId, @PathVariable("userId") String userId){

        User user = userService.getUserByUserId(userId);
        if(user==null){
            throw new CUserNotFoundException();
        }
        try{
            teamService.addMember(teamId, userId);
        }catch (Exception e){
            throw new CUserInviteDuplicateException();
        }

        MailDto mailDto = emailService.sendTeamAddEmail(userId,teamId);
        emailService.mailSend(mailDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }


    @GetMapping("/confirm/{teamId}")
    @ApiOperation(value = "team state변경", notes = "수락시 team member state 1로 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> confirmTeamMember(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId, @ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        if(!teamService.checkStateIfTeamPossible(teamId)){
            throw new CTooManyTeamMemberException();
        }
        //수락시, 회원 state변경
        teamService.changeTeamMemberConfirm(teamId, userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    //거절시, 회원 팀에서 삭제
    @GetMapping("/reject/{teamId}")
    @ApiOperation(value = "team 거절", notes = "거절시 userteam table에서 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> rejectTeamMember(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId, @ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        teamService.changeTeamMemberReject(teamId, userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{teamId}/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 게시글 조회 (일까지 들어감)", notes = "team name, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })

    public ResponseEntity<BoardDto> getTeamsArticleListAtDate(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "uploadDate", required = true) @PathVariable("uploadDate") String uploadDate){

        List<ArticleDto> articleDto = articleService.getTeamsArticleListAtDate(uploadDate, teamId);
        List<ImageDto> imageDto = imageService.getImageListAtDateByTeamId(uploadDate,teamId);
        List<VideoDto> videoDto = videoService.getVideoListAtDateByTeamId(uploadDate, teamId);
        List<EventResDto> eventResDto = eventService.getTeamEventListInDate(uploadDate, teamId);
        BoardDto boardDto = new BoardDto();
        boardDto.setArticleList(articleDto);
        boardDto.setImageList(imageDto);
        boardDto.setVideoList(videoDto);
        boardDto.setEventResList(eventResDto);
        boardDto.setTeamId(teamId);
        return ResponseEntity.status(200).body(boardDto);

    }

    @GetMapping("/{teamId}/count/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 게시글 조회 count (한달치 전체를 확인해서 갯수만뽑아옴)", notes = "team name, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Map<String, List<DayCountDto>>> getTeamsBoardCountListAtDate(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "uploadDate", required = true) @PathVariable("uploadDate") String uploadDate){

        List<DayCountDto> videocntList = videoService.getTeamVideosCountByMonth(uploadDate, teamId);
        List<DayCountDto> imagecntList = imageService.getTeamImagesCountByMonth(uploadDate,teamId);
        List<DayCountDto> articleList = articleService.getTeamArticleCountByMonth(uploadDate, teamId);
        Map<String, List<DayCountDto>> responseEntity = new HashMap<String, List<DayCountDto>>();
        responseEntity.put("videoCntList", videocntList);
        responseEntity.put("imageCntList", imagecntList);
        responseEntity.put("articleCntList", articleList);
        return ResponseEntity.status(200).body(responseEntity);
    }

    @GetMapping("/{teamId}/users")
    @ApiOperation(value = "team에 가입된 유저 목록", notes = "teamId를 통해 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<UserDto>> userListInTeam(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){

        List<UserDto> userDtoList = teamService.userListInTeam2(teamId);

        return ResponseEntity.status(200).body(userDtoList);
    }

    @GetMapping("/{teamId}/teamEvents/{reqDate}")
    @ApiOperation(value = "요청 달에 관련된 Team Event들 ", notes = "teamId를 통해 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<EventResDto>> teamEventListInMonth(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId,
                                                                  @ApiParam(value = "reqDate", required = true) @PathVariable("reqDate") String reqDate){

        List<EventResDto> eventResDtoList = eventService.getTeamEventListInMonth(reqDate, teamId);

        return ResponseEntity.status(200).body(eventResDtoList);

    }

    @PostMapping("/{teamId}/confState/true")
    @ApiOperation(value = "회의 상태 true로 변경", notes = "teamId로 접근")
    public ResponseEntity<? extends BaseResponseBody> changeConfStateTrue(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){
        teamService.changeConfStateToTrue(teamId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @PostMapping("/{teamId}/confState/false")
    @ApiOperation(value = "회의 상태 false로 변경", notes = "teamId로 접근")
    public ResponseEntity<? extends BaseResponseBody> changeConfStateFalse(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){
        teamService.changeConfStateToFalse(teamId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }
}
