package com.ssafy.api.service;


import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.dto.DayCountDto;
import com.ssafy.db.entity.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticleList();
    // 특정 user가 작성한 게시글 list
    List<ArticleDto> getUsersArticleList(String userId);
    // 특정 user가 특정 날짜에 작성한 게시글 list
    List<ArticleDto> getUsersArticleListAtDate(String cDate, String userId);
    // 특정 team이 작성한 게시글 list
    List<ArticleDto> getTeamsArticleList(Long teamId);
    // 특정 team이 특정 날짜에 작성한 게시글 list
    List<ArticleDto> getTeamsArticleListAtDate(String cDate, Long teamId);
    // 특정 team이 특정 달에 작성한 게시글의 갯수
    Long getTeamsArticleCountAtMonth(String cDate, Long teamId);

    Article addArticle(ArticleDto articleDto, Long teamId);
    Article getArticleDetailById(Long id);
    boolean updateArticleDetailById(Long id, ArticleDto articleDto);
    boolean deleteArticleDetailById(Long id);

    // 특정 team이 작성한 게시글 list
    List<ArticleDto> getArticleListById(Long teamId);

    List<DayCountDto> getTeamArticleCountByMonth(String uploadDate, Long teamId);
}
