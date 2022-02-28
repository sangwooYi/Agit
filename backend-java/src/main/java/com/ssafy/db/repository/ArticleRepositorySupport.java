package com.ssafy.db.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.QDayCountDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.QArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QArticle qArticle = QArticle.article;

    public Optional<Article> findArticleById(Long id){
        Article article = jpaQueryFactory.select(qArticle).from(qArticle)
                .where(qArticle.id.eq(id)).fetchOne();
        if(article == null) return Optional.empty();
        return Optional.ofNullable(article);
    }

    // Req에서 날짜 형태를 어떻게 받을지는 논의 필요
    // 특정 팀이 특정 달에 작성한 글
    public Optional<Long> findTeamsArticleCountByMonth(String cDate, Long teamId){
        // LocalDate 객체를 yyyy-mm 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qArticle.uploadDate, ConstantImpl.create("%Y-%m"));
        Long articleCount = jpaQueryFactory.select(qArticle).from(qArticle)
                .where(dateFormat.eq(cDate), qArticle.team.id.eq(teamId)).fetchCount();
        if(articleCount == 0) return Optional.empty();
        return Optional.ofNullable(articleCount);
    }


    // 특정 팀이 특정 날짜에 작성한 글
    public Optional<List<Article>> findTeamsArticleListByDate(String cDate, Long teamId){
        // LocalDate 객체를 yyyy-mm-dd 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qArticle.uploadDate, ConstantImpl.create("%Y-%m-%d"));
        List<Article> articles = jpaQueryFactory.select(qArticle).from(qArticle)
                .where(dateFormat.eq(cDate), qArticle.team.id.eq(teamId)).fetch();
        //if(articles.size() == 0) return Optional.empty();
        return Optional.ofNullable(articles);
    }

    // 특정 유저가 특정 날짜에 작성한 글
    public Optional<List<Article>> findUsersArticleListByDate(String cDate, String userId){
        // LocalDate 객체를 yyyy-mm-dd 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qArticle.uploadDate, ConstantImpl.create("%Y-%m-%d"));
        List<Article> articles = jpaQueryFactory.select(qArticle).from(qArticle)
                .where(dateFormat.eq(cDate), qArticle.writer.eq(userId)).fetch();
        if(articles.size() == 0) return Optional.empty();
        return Optional.ofNullable(articles);
    }

    public Optional<List<DayCountDto>>  findTeamArticleCountByMont(String uploadDate, Long teamId) {
        //LocalDate 객체를 yyyy-mm 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qArticle.uploadDate, ConstantImpl.create("%Y-%m"));
        List<DayCountDto> dayCountDtoList = jpaQueryFactory.select(
                        new QDayCountDto(qArticle.count().as("count"),qArticle.uploadDate)
                )
                .from(qArticle)
                .where(dateFormat.eq(uploadDate),qArticle.team.id.eq(teamId))
                .groupBy(qArticle.uploadDate)
                .fetch();

        return Optional.ofNullable(dayCountDtoList);
    }
}
