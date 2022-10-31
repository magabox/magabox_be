package com.clone.magabox.comment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.comment.service.CommentService;
import com.clone.magabox.dto.request.CommentRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{movieId}")
    public ResponseDto<?> create(@PathVariable("movieId") Long movieId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.create(movieId,commentRequestDto, memberDetails);
    }

    @PutMapping("/{commentId}")
    public ResponseDto<?> update(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.update(commentId,commentRequestDto, memberDetails.getMember());
    }

    @DeleteMapping("/{commentId}")
    public ResponseDto<?> delete(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.delete(commentId, memberDetails.getMember());
    }

}
