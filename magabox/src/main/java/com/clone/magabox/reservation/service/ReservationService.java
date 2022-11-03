package com.clone.magabox.reservation.service;

import com.clone.magabox.reservation.dto.request.ReservationRequestDto;
import com.clone.magabox.reservation.dto.response.ReservationResponseDto;
import com.clone.magabox.reservation.entity.StartTime;
import com.clone.magabox.util.ResponseDto;
import com.clone.magabox.starttime.entity.Reservation;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.starttime.repository.StartTimeRepository;
import com.clone.magabox.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StartTimeRepository startTimeRepository;

    @Transactional
    public ResponseDto<?> createReservation(ReservationRequestDto reservationRequestDto, MemberDetailsImpl memberDetails) {
        StartTime startTime = startTimeRepository.findById(reservationRequestDto.getTimeId()).orElse(null);
        if (Objects.isNull(startTime)) {
            return ResponseDto.fail(404, "Not Found", "해당 상영시간이 없습니다");
        }
        if (startTime.getCapacity() < reservationRequestDto.getNumTickets()) {
            return ResponseDto.fail(400, "Bad Request", "해당 시간대에 자리수가 부족합니다");
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

    @Transactional
    public ResponseDto<?> getReservation(Long reservationId, MemberDetailsImpl memberDetails) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (Objects.isNull(reservation)) {
            return ResponseDto.fail(404, "Not Found", "해당 예약내역이 없습니다");
        }
        if (Objects.isNull(memberDetails.getMember())) {
            return ResponseDto.fail(401, "Unauthorized", "로그인이 안된 상태입니다");
        }
        if (! memberDetails.getMember().getId().equals(reservation.getMember().getId())) {
            return ResponseDto.fail(403, "Forbidden Request", "예약을 한 유저가 아닙니다");
        }
        return ResponseDto.success(new ReservationResponseDto(reservation));
    }

    @Transactional
    public ResponseDto<?> deleteReservation(Long reservationId, MemberDetailsImpl memberDetails) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (Objects.isNull(reservation)) {
            return ResponseDto.fail(404, "Not Found", "해당 예약내역이 없습니다");
        }
        if (Objects.isNull(memberDetails.getMember())) {
            return ResponseDto.fail(401, "Unauthorized", "로그인이 안된 상태입니다");
        }
        if (! memberDetails.getMember().getId().equals(reservation.getMember().getId())) {
            return ResponseDto.fail(403, "Forbidden Request", "예약을 한 유저가 아닙니다");
        }
        reservationRepository.delete(reservation);
        return ResponseDto.success("성공적으로 예약을 삭제하였습니다");
    }
}
