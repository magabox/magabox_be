package com.clone.magabox.config.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private int rating;
    private String content;
}
