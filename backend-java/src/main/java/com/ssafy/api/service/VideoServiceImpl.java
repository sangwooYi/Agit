package com.ssafy.api.service;


import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.VideoDto;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.TeamRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.VideoRepository;
import com.ssafy.db.repository.VideoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("videoService")
public class VideoServiceImpl implements VideoService{


    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoRepositorySupport videoRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Override
    public Video addVideo(VideoDto videoDto, String userId, Long teamId) {
        Video video = videoDto.toEntity();

        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);

        video.setUser(user.get());
        video.setTeam(team.get());

        return videoRepository.save(video);
    }

    // 특정 팀이 업로드한 모든 비디오 리스트
    @Override
    public List<VideoDto> getVideoListById(Long teamId) {
        List<VideoDto> videoDtoList = new ArrayList<>();
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        List<Video> videos = team.getVideos();
        for(Video video : videos){
            VideoDto videoDto = new VideoDto(video);
            videoDtoList.add(videoDto);
        }
        return videoDtoList;
    }

    // 특정 팀이 특정 일자에 업로드한 모든 비디오 리스트
    @Override
    public List<VideoDto> getVideoListAtDateByTeamId(String uploadDate, Long teamId) {
        List<VideoDto> videoDtoList = new ArrayList<>();
        List<Video> videos = videoRepositorySupport.findTeamsVideoListByDayte(uploadDate, teamId).get();
        for (Video video : videos){
            VideoDto videoDto = new VideoDto(video);
            videoDtoList.add(videoDto);
        }
        return videoDtoList;
    }

    // 특정 팀이 특정 달에 업로드한 모든 비디오 리스트의 건수 (날짜별로 groupby)
    @Override
    public List<DayCountDto> getTeamVideosCountByMonth(String uploadDate, Long teamId) {
        List<DayCountDto> dayCountDtoList = videoRepositorySupport.findTeamVideosCountByMonth(uploadDate, teamId).get();

        return dayCountDtoList;
    }

    // 비디오 삭제
    @Override
    public boolean deleteVideo(Long no, String path) {
        Video video = videoRepositorySupport.findVideoById(no).get();
        if (video == null){
            return false;
        }
        videoRepository.deleteById(no);
        File file = new File(path + File.separator + video.getFilePath() + File.separator + video.getFileName());
        System.out.println(file);
        file.delete();
        return true;
    }

    @Override
    public Optional<Video> findById(Long no) {
        return videoRepository.findById(no);
    }
}

