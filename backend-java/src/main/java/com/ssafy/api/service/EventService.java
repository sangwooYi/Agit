package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    boolean addTeamEvent(EventDto eventDto, Long teamId);
    List<EventResDto> getUserEventList(Long userId);
    List<EventResDto> getUserEventListInMonth(Long userId, LocalDate date);
    List<EventResDto> getTeamEventListInDate(String eventDate, Long teamId);
    List<EventResDto> getTeamEventListInMonth(String eventDate, Long teamId);
}

