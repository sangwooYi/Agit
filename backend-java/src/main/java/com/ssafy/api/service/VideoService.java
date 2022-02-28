package com.ssafy.api.service;


import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.VideoDto;
import com.ssafy.db.entity.Video;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    Video addVideo(VideoDto videoDto, String userId, Long teamId);

    List<VideoDto> getVideoListById(Long teamId);
    //특정 team이 작성한 Video List
    List<VideoDto> getVideoListAtDateByTeamId(String uploadDate, Long teamId);
    //특정 team이 특정 날짜에 작성한 게시글 List
    List<DayCountDto> getTeamVideosCountByMonth(String uploadDate, Long teamId);

    boolean deleteVideo(Long no, String path);

    Optional<Video> findById(Long no);
}
