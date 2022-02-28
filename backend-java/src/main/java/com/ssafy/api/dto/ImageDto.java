package com.ssafy.api.dto;

import com.ssafy.db.entity.Image;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("ImageDto")
public class ImageDto {
    Long id;
    String originalFileName;
    String filePath;
    String fileName;
    LocalDateTime createDate;
    LocalDateTime updateDate;
    LocalDate uploadDate;

    public Image toEntity(){
        Image build = Image.builder()
                .originalFileName(originalFileName)
                .filePath(filePath)
                .fileName(fileName)
                .uploadDate(uploadDate)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Image image){
        this.id = image.getId();
        this.originalFileName = image.getOriginalFileName();
        this.filePath = image.getFilePath();
        this.fileName = image.getFileName();
        this.createDate = image.getCreateDate();
        this.updateDate = image.getCreateDate();
        this.uploadDate = image.getUploadDate();
    }
}
