package com.kkoch.admin.api.controller.notice.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class NoticeResponse {

    private String title;
    private String content;
    private String createdDate;

    @Builder
    public NoticeResponse(String title, String content, LocalDateTime createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
