package com.clone.magabox.movie.entity;

import com.clone.magabox.comment.entity.Comment;
import com.clone.magabox.Heart.entity.Heart;
import com.clone.magabox.member.entity.Member;
import com.clone.magabox.reservation.entity.StartTime;
import org.hibernate.annotations.Formula;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    private String imageUrl;

    @Column(nullable = false)
    private int runtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<StartTime> startTimeList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Heart> heartList = new ArrayList<>();

    @Formula("(select count(1) from heart h where h.movie_id = id)")
    private int totalHeartCount;

    @Formula("(select ROUND(AVG(c.rating),1) from comment c where c.movie_id = id)")
    private Float totalRating;
}
