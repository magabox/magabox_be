package com.clone.magabox.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String desc;

    private String imageUrl;

    @Column(nullable = false)
    private int runtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<StartTime> startTimeList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Heart> heartList = new ArrayList<>();

    @Formula("(select count(1) from heart h where h.movie_id = id)")
    private int totalHeartCount;
}
