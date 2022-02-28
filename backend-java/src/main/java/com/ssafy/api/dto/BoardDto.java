package com.ssafy.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("BoardDto")
public class BoardDto {
    Long teamId;
    List<ArticleDto> articleList = new ArrayList<>();
    List<ImageDto> imageList = new ArrayList<>();
    List<VideoDto> videoList = new ArrayList<>();
    List<EventResDto> eventResList = new ArrayList<>();
}
