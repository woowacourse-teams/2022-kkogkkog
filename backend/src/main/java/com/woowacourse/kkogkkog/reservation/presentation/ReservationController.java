package com.woowacourse.kkogkkog.reservation.presentation;

import com.woowacourse.kkogkkog.presentation.LoginMember;
import com.woowacourse.kkogkkog.reservation.application.ReservationService;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationCreateRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@LoginMember Long loginMemberId,
                                       @RequestBody ReservationCreateRequest request) {
        Long reservationId = reservationService.save(request.toReservationSaveRequest(loginMemberId));

        return ResponseEntity.created(URI.create("/api/reservations/" + reservationId)).build();
    }
}
