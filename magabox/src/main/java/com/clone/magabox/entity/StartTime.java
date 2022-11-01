package com.clone.magabox.entity;

import com.clone.magabox.movie.entity.Movie;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalTime;
import lombok.Getter;

@Entity
@Getter
@NoArgsConstructor
public class StartTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private LocalTime localTime;

//    public StartTime(StartTimeRequestDto startTimeRequestDto) {
//
//    }
}
