package com.ssafy.db.repository;


import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.QDayCountDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Image;
import com.ssafy.db.entity.QImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QImage qImage = QImage.image;

    public Optional<Image> findImageById(Long id){
        Image image = jpaQueryFactory.select(qImage).from(qImage)
                .where(qImage.id.eq(id)).fetchOne();
        if(image==null) return Optional.empty();
        return Optional.ofNullable(image);
    }

    //특정 팀이 특정 달에 작성한글
    public Optional<List<DayCountDto>> findTeamImagesCountByMonth(String uploadDate, Long teamId){
        //LocalDate 객체를 yyyy-mm 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qImage.uploadDate, ConstantImpl.create("%Y-%m"));
        List<DayCountDto> dayCountDtoList = jpaQueryFactory.select(
                new QDayCountDto(qImage.count().as("count"),qImage.uploadDate)
                )
                .from(qImage)
                .where(dateFormat.eq(uploadDate),qImage.team.id.eq(teamId))
                .groupBy(qImage.uploadDate)
                .fetch();

        return Optional.ofNullable(dayCountDtoList);
    }

    public Optional<List<Image>> findTeamsImageListByDayte(String uploadDate, Long teamId) {
        // LocalDate 객체를 yyyy-mm-dd 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qImage.uploadDate, ConstantImpl.create("%Y-%m-%d"));
        List<Image> images = jpaQueryFactory.select(qImage).from(qImage)
                .where(dateFormat.eq(uploadDate), qImage.team.id.eq(teamId)).fetch();

        return Optional.ofNullable(images);
    }
}
