package com.clone.magabox.reservation.service;

import com.clone.magabox.dto.request.ReservationRequestDto;
import com.clone.magabox.dto.response.ReservationResponseDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.entity.Reservation;
import com.clone.magabox.entity.StartTime;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.repository.StartTimeRepository;
import com.clone.magabox.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StartTimeRepository startTimeRepository;

    @Transactional
    public ResponseDto<?> createReservation(Long timeId, ReservationRequestDto reservationRequestDto, MemberDetailsImpl memberDetails) {
        StartTime startTime = startTimeRepository.findById(timeId).orElseThrow(
                () -> new NullPointerException("해당 상영시간이 없습니다")
        );
        if (startTime.getCapacity() < reservationRequestDto.getNumTickets()) {
            throw new IllegalArgumentException("해당 시간대에 자리수가 부족합니다");
        }
        Reservation reservation = new Reservation().builder()
                .startTime(startTime)
                .member(memberDetails.getMember())
                .numTickets(reservationRequestDto.getNumTickets())
                .build();

        reservationRepository.save(reservation);
        startTime.addViewers(reservationRequestDto.getNumTickets());

        return ResponseDto.success("성공적으로 예약했습니다");
    }

    @Transactional
    public ResponseDto<?> getMemberReservation(MemberDetailsImpl memberDetails) {
        List<Reservation> reservationList = reservationRepository.findByMember(memberDetails.getMember());

        List<ReservationResponseDto> reservationResponseDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            reservationResponseDtoList.add(new ReservationResponseDto(reservation));
        }

        return ResponseDto.success(reservationResponseDtoList);
    }
}
