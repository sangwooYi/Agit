package com.ssafy.api.service;

import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.dto.DayCountDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleRepositorySupport articleRepositorySupport;


    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;



    @Override
    @Transactional
    public List<ArticleDto> getArticleList() {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleDto> articleDtoList = new ArrayList<>();

        for(Article article : articleList){
            User user = userRepositorySupport.findUserByUserId(article.getWriter()).get();
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getUsersArticleList(String userId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        User user = userRepositorySupport.findUserByUserId(userId).get();
        List<Article> articles = user.getArticles();
        for(Article article : articles){
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getUsersArticleListAtDate(String cDate, String userId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        List<Article> articles = articleRepositorySupport.findUsersArticleListByDate(cDate, userId).get();
        User user = userRepositorySupport.findUserByUserId(userId).get();
        for(Article article : articles){
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getTeamsArticleList(Long teamId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        List<Article> articles = team.getArticles();
        for(Article article : articles){
            User user = userRepositorySupport.findUserByUserId(article.getWriter()).get();
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getArticleListById(Long teamId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        List<Article> articles = team.getArticles();
        for(Article article : articles){
            User user = userRepositorySupport.findUserByUserId(article.getWriter()).get();
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<DayCountDto> getTeamArticleCountByMonth(String uploadDate, Long teamId) {
        List<DayCountDto> dayCountDtoList = articleRepositorySupport.findTeamArticleCountByMont(uploadDate, teamId).get();
        return dayCountDtoList;
    }

    @Override
    public List<ArticleDto> getTeamsArticleListAtDate(String cDate, Long teamId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        //Optional<List<Article>> articles = Optional.ofNullable(articleRepositorySupport.findTeamsArticleListByDate(cDate, teamId).get());
        List<Article> articles = articleRepositorySupport.findTeamsArticleListByDate(cDate, teamId).get();
        for(Article article : articles){
            User user = userRepositorySupport.findUserByUserId(article.getWriter()).get();
            ArticleDto articleDto = new ArticleDto(article);
            articleDto.setNickName(user.getNickName());
            articleDtoList.add(articleDto);
        }

        return articleDtoList;
    }

    @Override
    public Long getTeamsArticleCountAtMonth(String cDate, Long teamId) {
        Long articleCount = articleRepositorySupport.findTeamsArticleCountByMonth(cDate, teamId).get();
        return articleCount;
    }


    @Override
    public Article addArticle(ArticleDto articleDto, Long teamId) {
        Article article = articleDto.toEntity();
        Optional<User> user = userRepositorySupport.findUserByUserId(article.getWriter());
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        // user ,  team FK 값 입력
        article.setUser(user.get());
        article.setTeam(team.get());
        return articleRepository.save(article);
    }

    @Override
    public Article getArticleDetailById(Long id) {
        Optional<Article> articleOpt = articleRepositorySupport.findArticleById(id);
        Article article = new Article();
        if(articleOpt.isPresent()){
            article = articleOpt.get();
        }
        return article;
    }

    @Override
    public boolean updateArticleDetailById(Long id, ArticleDto articleDto) {
        Article article = getArticleDetailById(id);
        if(article == null){
            return false;
        }
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);
        return true;
    }

    @Override
    public boolean deleteArticleDetailById(Long id) {
        Article article = getArticleDetailById(id);
        if(article == null){
            return false;
        }
        articleRepository.delete(article);
        return true;
    }
}
