package com.woowacourse.kkogkkog.reservation.domain.repository;

import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
