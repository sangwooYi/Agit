package com.ssafy.api.dto;


import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("ArticleDto")
public class ArticleDto {
    Long id;
    String title;
    String content;
    String writer;
    String nickName;
    String teamName;
    // yyyy-mm-dd 형식 스트링으로 받아옴
    LocalDate uploadDate;
    LocalDateTime createdTime;
    LocalDateTime updatedDate;


    public Article toEntity(){
        Article build = Article.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .nickName(nickName)
                .teamName(teamName)
                .uploadDate(uploadDate)
                .build();
        return build;
    }

    @Builder
    public ArticleDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writer = article.getWriter();
        this.nickName = article.getNickName();
        this.teamName = article.getTeamName();
        this.createdTime = article.getCreatedDate();
        this.updatedDate = article.getUpdatedDate();
        this.uploadDate = article.getUploadDate();
    }
}
