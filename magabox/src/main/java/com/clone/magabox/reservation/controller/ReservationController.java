package com.clone.magabox.reservation.controller;

import com.clone.magabox.reservation.dto.request.ReservationRequestDto;
import com.clone.magabox.util.ResponseDto;
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

    @PostMapping("/reservations")
    public ResponseDto<?> createReservation( @RequestBody ReservationRequestDto reservationRequestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.createReservation(reservationRequestDto, memberDetails);
    }

    @GetMapping("/reservations")
    public ResponseDto<?> getMemberReservation(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.getMemberReservation(memberDetails);
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseDto<?> getReservation(@PathVariable Long reservationId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.getReservation(reservationId, memberDetails);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseDto<?> deleteReservation(@PathVariable Long reservationId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return reservationService.deleteReservation(reservationId, memberDetails);
    }

}
