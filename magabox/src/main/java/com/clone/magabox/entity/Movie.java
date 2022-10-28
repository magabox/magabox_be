package com.clone.magabox.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String desc;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int runtime;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<StartTime> startTimeList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

//    public Movie(MovieRequestDto movieRequestDto) {
//
//    }
}
