package com.clone.magabox.reservation.entity;

import com.clone.magabox.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private LocalTime startingTime;

    private int capacity;

    public void addViewers(int num) {
        this.capacity -= num;
    }
}
