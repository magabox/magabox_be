package com.clone.magabox.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private Long timeId;
    private int numTickets;
}
