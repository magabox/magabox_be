package com.clone.magabox.reservation.dto.response;

import com.clone.magabox.starttime.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationResponseDto {
    private Long reservationId;
    private String movieTitle;
    private LocalTime startingTime;
    private int numTickets;

    public ReservationResponseDto(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.movieTitle = reservation.getStartTime().getMovie().getTitle();
        this.startingTime = reservation.getStartTime().getStartingTime();
        this.numTickets = reservation.getNumTickets();;
    }
}
