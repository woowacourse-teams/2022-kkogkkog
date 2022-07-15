package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class CouponService2Test extends ServiceTest {

    private static final Member JEONG = new Member(null, "jeong@gmail.com", "password1234!", "정");
    private static final Member LEO = new Member(null, "leo@gmail.com", "password1234!", "레오");
    private static final Member ROOKIE = new Member(null, "rookie@gmail.com", "password1234!", "루키");
    private static final Member ARTHUR = new Member(null, "arthur@gmail.com", "password1234!", "아서");

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(JEONG);
        memberRepository.save(LEO);
        memberRepository.save(ROOKIE);
        memberRepository.save(ARTHUR);
    }

    @DisplayName("사용자가 보낸 쿠폰들이 조회된다.")
    @Nested
    class FindBySenderTest {

        @DisplayName("조회되는 쿠폰 개수 확인")
        @Test
        void couponCount() {
            couponService.save(toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO)));
            couponService.save(toCouponSaveRequest(JEONG, List.of(ARTHUR, LEO)));
            List<CouponResponse> actual = couponService.findAllBySender(ROOKIE.getId());

            assertThat(actual.size()).isEqualTo(3);
        }

        @DisplayName("조회되는 쿠폰의 보낸 사람 정보 확인")
        @Test
        void senderId() {
            couponService.save(toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO)));
            couponService.save(toCouponSaveRequest(JEONG, List.of(ARTHUR, LEO)));
            Long senderId = ROOKIE.getId();

            List<Long> actual = couponService.findAllBySender(ROOKIE.getId())
                    .stream().map(CouponResponse::getSender)
                    .map(CouponMemberResponse::getId)
                    .collect(Collectors.toList());
            List<Long> expected = List.of(senderId, senderId, senderId);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("사용자가 받은 쿠폰들이 조회된다.")
    @Nested
    class FindByReceiverTest {

        @DisplayName("조회되는 쿠폰 개수 확인")
        @Test
        void couponCount() {
            couponService.save(toCouponSaveRequest(ARTHUR, List.of(JEONG, LEO)));
            couponService.save(toCouponSaveRequest(LEO, List.of(JEONG, ARTHUR)));
            List<CouponResponse> actual = couponService.findAllByReceiver(JEONG.getId());

            assertThat(actual.size()).isEqualTo(2);
        }

        @DisplayName("조회되는 쿠폰의 받은 사람 정보 확인")
        @Test
        void receiverId() {
            couponService.save(toCouponSaveRequest(ARTHUR, List.of(JEONG, LEO)));
            couponService.save(toCouponSaveRequest(LEO, List.of(JEONG, ARTHUR)));
            Long receiverId = JEONG.getId();

            List<Long> actual = couponService.findAllByReceiver(receiverId)
                    .stream().map(CouponResponse::getReceiver)
                    .map(CouponMemberResponse::getId)
                    .collect(Collectors.toList());
            List<Long> expected = List.of(receiverId, receiverId);

            assertThat(actual).isEqualTo(expected);
        }
    }

    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        return new CouponSaveRequest(sender.getId(), receiverIds, "#123456", "한턱내는", "추가 메세지", "COFFEE");
    }
}
