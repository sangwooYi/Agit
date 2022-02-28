package com.ssafy.api.service;

import com.ssafy.api.dto.DayCountDto;
import com.ssafy.api.dto.ImageDto;
import com.ssafy.db.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    Image addImage(ImageDto imageDto,String userId, Long teamId);
    //이미지추가
    List<ImageDto> getImageListById(Long teamId);
    //특정 team이 작성한 image List
    List<ImageDto> getImageListAtDateByTeamId(String uploadDate, Long teamId);
    //특정 team이 특정 날짜에 작성한 게시글 List
    List<DayCountDto> getTeamImagesCountByMonth(String uploadDate, Long teamId);

    boolean deleteNotice(Long no, String path);

    Optional<Image> findById(Long no);
}
