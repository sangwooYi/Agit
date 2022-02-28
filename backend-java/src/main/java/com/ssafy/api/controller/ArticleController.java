package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CArticleNotFoundException;
import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Article;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Api(value = "게시글 API", tags = {"Article"})
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @PostMapping("/{teamId}")
    @ApiOperation(value = "게시글 작성", notes = "<strong>글 제목과 글 내용</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addArticle (@RequestBody @ApiParam(value="로그인 정보", required = true) ArticleDto articleDto,
                                                                  @ApiParam(value = "userId", required = true) @PathVariable("teamId") Long teamId) throws Exception {
        Article article = articleService.addArticle(articleDto, teamId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping()
    @ApiOperation(value = "전체 게시글 조회", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getList(){
        List<ArticleDto> articleDtoList = articleService.getArticleList();

        return ResponseEntity.status(200).body(articleDtoList);
    }

    @GetMapping("/writer/{userId}")
    @ApiOperation(value = "user가 작성한 전체 글 조회", notes = "user id 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getUsersArticleList(@ApiParam(value = "userId", required = true) @PathVariable("userId") String userId){
        List<ArticleDto> articleDtoList = articleService.getUsersArticleList(userId);
        return ResponseEntity.status(200).body(articleDtoList);
    }

    @GetMapping("/writer/{userId}/{date}")
    @ApiOperation(value = "user가 특정 일자에 작성한 전체 글 조회", notes = "userId, date 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getUsersArticleListAtDate(@ApiParam(value = "userId", required = true) @PathVariable("userId") String userId,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String cDate){
        List<ArticleDto> articleDto = articleService.getUsersArticleListAtDate(cDate, userId);
        return ResponseEntity.status(200).body(articleDto);
    }

    @GetMapping("/team/{teamId}")
    @ApiOperation(value = "team에서 작성한 전체 글 조회", notes = "team name 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getTeamsArticleList(@ApiParam(value = "teamName", required = true) @PathVariable("teamId") Long teamId){
        List<ArticleDto> articleDto = articleService.getTeamsArticleList(teamId);
        return ResponseEntity.status(200).body(articleDto);
    }


    @GetMapping("/team/{teamId}/date/{date}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 글 조회", notes = "team name, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getTeamsArticleListAtDate(@ApiParam(value = "teamName", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String cDate){
        List<ArticleDto> articleDto = articleService.getTeamsArticleListAtDate(cDate, teamId);
        return ResponseEntity.status(200).body(articleDto);
    }

    @GetMapping("/team/{teamId}/month/{month}")
    @ApiOperation(value = "team에서 특정 달에 작성한 전체 글 갯수", notes = "team name, date(yyyy-mm) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Long> getTeamsArticleCountAtMonth(@ApiParam(value = "teamName", required = true) @PathVariable("teamId") Long teamId,
                                                            @ApiParam(value = "month", required = true) @PathVariable("month") String cDate){
        Long articleCount = articleService.getTeamsArticleCountAtMonth(cDate, teamId);
        return ResponseEntity.status(200).body(articleCount);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "특정 게시글 조회", notes = "게시글 id 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ArticleDto> getArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        Article article = articleService.getArticleDetailById(id);
        // 수정하려는 글이 없으면 예외 처리
        if(article.getId() == null){
            throw new CArticleNotFoundException();
        }
        ArticleDto articleDto = new ArticleDto(article);
        return ResponseEntity.status(200).body(articleDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글 id 이용하여 조회 후 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends  BaseResponseBody> updateArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                                           @RequestBody @ApiParam(value="로그인 정보", required = true) ArticleDto articleDto){
        if(!articleService.updateArticleDetailById(id, articleDto)){
            // 수정하려는 글이 없으면 예외처리
            throw new CArticleNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "수정 성공"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 id 이용하여 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> deleteArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        if(!articleService.deleteArticleDetailById(id)){
            // 삭제하려는 글이 없으면 예외 처리
            throw new CArticleNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제 성공"));
    }
}
