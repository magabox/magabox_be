package com.clone.magabox.reservation.controller;

import com.clone.magabox.dto.request.ReservationRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/reservations/{timeId}")
    public ResponseDto<?> createReservation(@PathVariable Long timeId, @RequestBody ReservationRequestDto reservationRequestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.createReservation(timeId, reservationRequestDto, memberDetails);
    }

    @GetMapping("/reservations")
    public ResponseDto<?> getMemberReservation(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.getMemberReservation(memberDetails);
    }
}
