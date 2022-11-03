package com.clone.magabox.reservation.repository;


import com.clone.magabox.member.entity.Member;
import com.clone.magabox.starttime.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMember(Member member);
}
