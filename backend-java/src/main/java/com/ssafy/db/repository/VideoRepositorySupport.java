package com.ssafy.db.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.QDayCountDto;
import com.ssafy.db.entity.QVideo;
import com.ssafy.db.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VideoRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QVideo qVideo = QVideo.video;


    public Optional<Video> findVideoById(Long id){
        Video video = jpaQueryFactory.select(qVideo).from(qVideo)
                .where(qVideo.id.eq(id)).fetchOne();
        if(video==null) return Optional.empty();
        return Optional.ofNullable(video);
    }

    //특정 팀이 특정 달에 작성한글
    public Optional<List<DayCountDto>> findTeamVideosCountByMonth(String uploadDate, Long teamId){
        //LocalDate 객체를 yyyy-mm 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qVideo.uploadDate, ConstantImpl.create("%Y-%m"));
        List<DayCountDto> dayCountDtoList = jpaQueryFactory.select(
                        new QDayCountDto(qVideo.count().as("count"),qVideo.uploadDate)
                )
                .from(qVideo)
                .where(dateFormat.eq(uploadDate),qVideo.team.id.eq(teamId))
                // 날짜별로 group 나누어서 count 됨 (날짜 있는애들만)
                .groupBy(qVideo.uploadDate)
                .fetch();

        return Optional.ofNullable(dayCountDtoList);
    }

    public Optional<List<Video>> findTeamsVideoListByDayte(String uploadDate, Long teamId) {
        // LocalDate 객체를 yyyy-mm-dd 형태의 String으로 변경
        StringTemplate dateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qVideo.uploadDate, ConstantImpl.create("%Y-%m-%d"));
        List<Video> videos = jpaQueryFactory.select(qVideo).from(qVideo)
                // , 로 and 조건 가능
                .where(dateFormat.eq(uploadDate), qVideo.team.id.eq(teamId)).fetch();

        return Optional.ofNullable(videos);
    }

}
