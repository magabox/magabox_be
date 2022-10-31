package com.clone.magabox.dto.response;

import com.clone.magabox.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String username;
    private int rating;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.rating = comment.getRating();
    }
}
