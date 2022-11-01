package com.clone.magabox.comment.service;

import com.clone.magabox.comment.repository.CommentRepository;
import com.clone.magabox.member.repository.MemberRepository;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.movie.repository.MovieRepository;
import com.clone.magabox.comment.dto.request.CommentRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.comment.entity.Comment;
import com.clone.magabox.member.entity.Member;
import com.clone.magabox.movie.entity.Movie;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;

    /**
     * 댓글 생성
     */
    public ResponseDto<?> create(Long movieId, CommentRequestDto commentRequestDto, MemberDetailsImpl memberDetails) {
        Movie movie = movieRepository.findById(movieId).orElse(null);

        Member member = memberDetails.getMember();
        Member findUsername = memberRepository.findById(member.getId()).orElse(null);
        if(Objects.isNull(findUsername)) {
            return ResponseDto.fail(HttpStatus.SC_MULTI_STATUS,"잘못된 요청 입니다.","로그인 후 이용가능");
        }

        if(Objects.isNull(movie)) {
            return ResponseDto.fail(400,"잘못된 요청 입니다.","해당 게시물이 없어요");
        } else {
            Comment comment = Comment.builder()
                    .content(commentRequestDto.getContent())
                    .rating(commentRequestDto.getRating())
                    .movie(movie)
                    .member(member)
                    .build();
            commentRepository.save(comment);

            return ResponseDto.success("댓글 생성 완료!");
        }
    }

    /**
     *  댓글 수정
     */
    public ResponseDto<?> update(Long commentId, CommentRequestDto commentRequestDto, Member member) {
        Comment findComment = commentRepository.findById(commentId).orElse(null);

        if(Objects.isNull(findComment)) {
            return ResponseDto.fail(400,"해당 댓글을 찾을 수 없어요.","해당 댓글을 찾을 수 없어요.");
        }

        if(!findComment.getMember().getId().equals(member.getId())) {
            return ResponseDto.fail(400,"권한이 없어요.","권한이 없어요.");
        }

        Comment comment = Comment.builder()
                .id(findComment.getId())
                .movie(findComment.getMovie())
                .member(findComment.getMember())
                .rating(commentRequestDto.getRating())
                .content(commentRequestDto.getContent())
                .build();

        commentRepository.save(comment);

        return ResponseDto.success("수정성공");
    }

    public ResponseDto<?> delete(Long commentId, Member member) {
        Comment findComment = commentRepository.findById(commentId).orElse(null);

        if(Objects.isNull(findComment)) {
            return ResponseDto.fail(400,"해당 댓글을 찾을 수 없어요.","해당 댓글을 찾을 수 없어요.");
        }

        if(!findComment.getMember().getId().equals(member.getId())) {
            return ResponseDto.fail(400,"권한이 없어요.","권한이 없어요.");
        }

        commentRepository.delete(findComment);

        return ResponseDto.success("댓글 삭제 성공");
    }
}
