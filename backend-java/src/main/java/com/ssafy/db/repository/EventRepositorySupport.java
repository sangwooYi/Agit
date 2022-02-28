package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.QEvent;
import com.ssafy.db.entity.QTeam;
import com.ssafy.db.entity.QUserTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QEvent qEvent = QEvent.event;
    QTeam qTeam = QTeam.team;
    QUserTeam qUserTeam = QUserTeam.userTeam;

    public Optional<List<Event>> findEventsAllByUserID(Long id){
        List<Event> eventList = jpaQueryFactory.select(qEvent)
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();

        return Optional.ofNullable(eventList);

    }

    // 특정 user에 관련된 전체 event 리스트  // 중복 제거
    public Optional<List<EventResDto>> findEventResAllByUserID(Long id){
        List<EventResDto> eventResDtoList = new ArrayList<>();
        List<Tuple> result = jpaQueryFactory.select(qEvent.eventTitle, qEvent.eventContent, qEvent.teamName, qEvent.startDate, qEvent.endDate, qUserTeam.team.id)
                .distinct()
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();
        for (Tuple tuple : result){
            EventResDto eventResDto = new EventResDto();
            eventResDto.setEventTitle(tuple.get(qEvent.eventTitle));
            eventResDto.setEventContent(tuple.get(qEvent.eventContent));
            eventResDto.setTeamName(tuple.get(qEvent.teamName));
            eventResDto.setTeamId(tuple.get(qUserTeam.team.id));
            LocalDate startDate = tuple.get(qEvent.startDate);
            LocalDate endDate = tuple.get(qEvent.endDate);
            eventResDto.setStartDate(startDate);
            eventResDto.setEndDate(endDate);
            // 남은 날짜 계산
            LocalDate now = LocalDate.now();
            Long dDay = now.until(endDate, ChronoUnit.DAYS);
            eventResDto.setDDay(dDay);

            eventResDtoList.add(eventResDto);
        }
        return Optional.ofNullable(eventResDtoList);
    }

    // 특정 user에 관련된 event중 특정 달에 해당하는 리스트  // 중복 제거
    public Optional<List<EventResDto>> findEventResListByUserIdInMonth(Long id, LocalDate date){
        List<EventResDto> eventResDtoList = new ArrayList<>();
        List<Tuple> result = jpaQueryFactory.select(qEvent.eventTitle, qEvent.eventContent, qEvent.teamName, qEvent.startDate, qEvent.endDate, qUserTeam.team.id)
                .distinct()
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();
        for (Tuple tuple : result){
            LocalDate startDate = tuple.get(qEvent.startDate);
            LocalDate endDate = tuple.get(qEvent.endDate);
            // 특정 달에만 해당하는 쿼리만 추가해서 반환 // 시작 날짜 or 끝나는 날짜 중 하나라도 걸리면
            if ((date.getYear() == endDate.getYear() && endDate.getMonthValue() == date.getMonthValue())
                    || (date.getYear() == startDate.getYear() && startDate.getMonthValue() == date.getMonthValue())){
                EventResDto eventResDto = new EventResDto();
                eventResDto.setEventTitle(tuple.get(qEvent.eventTitle));
                eventResDto.setEventContent(tuple.get(qEvent.eventContent));
                eventResDto.setTeamName(tuple.get(qEvent.teamName));
                eventResDto.setTeamId(tuple.get(qUserTeam.team.id));
                eventResDto.setStartDate(startDate);
                eventResDto.setEndDate(endDate);
                // 남은 날짜 계산
                Long dDay = date.until(endDate, ChronoUnit.DAYS);
                eventResDto.setDDay(dDay);

                eventResDtoList.add(eventResDto);
            }
        }
        return Optional.ofNullable(eventResDtoList);
    }
    // 특정 달에 관련된 Team Event
    public Optional<List<EventResDto>> findEventListByTeamInMonth(String eventDate, Long teamId){
        StringTemplate startDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qEvent.startDate, ConstantImpl.create("%Y-%m"));
        StringTemplate endDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qEvent.endDate, ConstantImpl.create("%Y-%m"));
        List<Event> eventList = jpaQueryFactory.select(qEvent)
                .from(qEvent)
                .where(startDateFormat.eq(eventDate).or(endDateFormat.eq(eventDate)), qEvent.team.id.eq(teamId))
                .fetch();

        List<EventResDto> eventResDtoList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Event event : eventList){
            EventResDto eventResDto = new EventResDto();
            eventResDto.setEventTitle(event.getEventTitle());
            eventResDto.setEventContent(event.getEventContent());
            eventResDto.setTeamName(event.getTeamName());
            eventResDto.setTeamId(teamId);
            eventResDto.setStartDate(event.getStartDate());
            eventResDto.setEndDate(event.getEndDate());
            eventResDto.setDDay(now.until(event.getEndDate(), ChronoUnit.DAYS));
            eventResDtoList.add(eventResDto);
        }
        return Optional.ofNullable(eventResDtoList);
    }


    // 특정 팀에 특정 일자에 관련된 Event
    public Optional<List<EventResDto>> findEventListByTeamInDate(String eventDate, Long teamId){
        StringTemplate startDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qEvent.startDate, ConstantImpl.create("%Y-%m-%d"));
        StringTemplate endDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qEvent.endDate, ConstantImpl.create("%Y-%m-%d"));
        List<Event> eventList = jpaQueryFactory.select(qEvent)
                .from(qEvent)
                .where(startDateFormat.eq(eventDate).or(endDateFormat.eq(eventDate)), qEvent.team.id.eq(teamId))
                .fetch();
        List<EventResDto> eventResDtoList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Event event : eventList){
            EventResDto eventResDto = new EventResDto();
            eventResDto.setEventTitle(event.getEventTitle());
            eventResDto.setEventContent(event.getEventContent());
            eventResDto.setTeamName(event.getTeamName());
            eventResDto.setStartDate(event.getStartDate());
            eventResDto.setTeamId(teamId);
            eventResDto.setEndDate(event.getEndDate());
            eventResDto.setDDay(now.until(event.getEndDate(), ChronoUnit.DAYS));
            eventResDtoList.add(eventResDto);
        }

        return Optional.ofNullable(eventResDtoList);
    }


}