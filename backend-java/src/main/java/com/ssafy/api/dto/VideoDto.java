package com.ssafy.api.dto;

import com.ssafy.db.entity.Video;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("VideoDto")
public class VideoDto {
    Long id;
    String originalFileName;
    String filePath;
    String fileName;
    LocalDateTime createDate;
    LocalDateTime updateDate;
    LocalDate uploadDate;

    public Video toEntity(){
        Video build = Video.builder()
                .originalFileName(originalFileName)
                .filePath(filePath)
                .fileName(fileName)
                .uploadDate(uploadDate)
                .build();
        return build;
    }

    @Builder
    public VideoDto(Video video){
        this.id = video.getId();
        this.originalFileName = video.getOriginalFileName();
        this.filePath = video.getFilePath();
        this.fileName = video.getFileName();
        this.createDate = video.getCreateDate();
        this.updateDate = video.getCreateDate();
        this.uploadDate = video.getUploadDate();
    }

}
