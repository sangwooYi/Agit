package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;

@Service("EventService")
public class EventServiceImpl implements EventService{

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserTeamRepositroySupport userTeamRepositroySupport;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventRepositorySupport eventRepositorySupport;

    @Override
    public boolean addTeamEvent(EventDto eventDto, Long teamId) {
        Event event = eventDto.toEntity();

        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        if (team == null){
            return false;
        }
        event.setTeam(team.get());
        eventRepository.save(event);
        return true;
    }

//    @Override
//    public List<EventDto> getUsersEventList(Long userId) {
//
//        List<Event> eventList = eventRepositorySupport.findEventsAllByUserID(userId).get();
//        List<EventDto> eventDtoList = new ArrayList<>();
//        for(Event event : eventList){
//            EventDto eventDto = new EventDto(event);
//            eventDtoList.add(eventDto);
//        }
//
//        return eventDtoList;
//    }

    @Override
    public List<EventResDto> getUserEventList(Long userId) {
        List<EventResDto> eventResDtoList = eventRepositorySupport.findEventResAllByUserID(userId).get();
        return eventResDtoList;
    }

    @Override
    public List<EventResDto> getUserEventListInMonth(Long userId, LocalDate date) {
        List<EventResDto> eventResDtoList = eventRepositorySupport.findEventResListByUserIdInMonth(userId, date).get();

        return eventResDtoList;
    }




    @Override
    public List<EventResDto> getTeamEventListInDate(String eventDate, Long teamId) {
        List<EventResDto> eventResDtoList = eventRepositorySupport.findEventListByTeamInDate(eventDate, teamId).get();

        return eventResDtoList;
    }

    @Override
    public List<EventResDto> getTeamEventListInMonth(String eventDate, Long teamId) {
        List<EventResDto> eventResDtoList = eventRepositorySupport.findEventListByTeamInMonth(eventDate, teamId).get();

        return eventResDtoList;
    }


}
