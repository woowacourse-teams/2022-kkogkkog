package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponsResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member(1L, "루키"));
        memberRepository.save(new Member(2L, "아서"));
        memberRepository.save(new Member(3L, "정"));
    }

    @Test
    @DisplayName("쿠폰을 생성할 수 있다.")
    void save() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, "red", "한턱내는", "추가 메세지", "커피");
        Long couponId = couponService.save(couponCreateRequest);
        assertThat(couponId).isNotNull();
    }

    @Test
    @DisplayName("쿠폰을 조회할 수 있다.")
    void findById() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, "red", "한턱내는", "추가 메세지", "커피");
        Long couponId = couponService.save(couponCreateRequest);

        CouponResponse couponResponse = couponService.findById(couponId);

        assertThat(couponResponse).usingRecursiveComparison()
                .isEqualTo(new CouponResponse(couponId, "루키", "아서", "red", "한턱내는", "추가 메세지", "커피",
                        CouponStatus.READY.name()));
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 조회할 경우 예외가 발생한다")
    void findById_notFound() {
        assertThatThrownBy(() -> couponService.findById(1L))
                .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    @DisplayName("쿠폰 목록을 전체 조회할 수 있다.")
    void findAll() {
        CouponCreateRequest couponCreateRequest1 = new CouponCreateRequest(1L, 2L, "red", "한턱내는", "추가 메세지", "커피");
        CouponCreateRequest couponCreateRequest2 = new CouponCreateRequest(1L, 3L, "red", "한턱내는", "추가 메세지", "커피");
        Long couponId1 = couponService.save(couponCreateRequest1);
        Long couponId2 = couponService.save(couponCreateRequest2);

        CouponsResponse actual = couponService.findAll();
        CouponsResponse expected = new CouponsResponse(List.of(
                new CouponResponse(couponId1, "루키", "아서", "red", "한턱내는",
                        "추가 메세지", "커피", CouponStatus.READY.name()),
                new CouponResponse(couponId2, "루키", "정", "red", "한턱내는",
                        "추가 메세지", "커피", CouponStatus.READY.name())));
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
