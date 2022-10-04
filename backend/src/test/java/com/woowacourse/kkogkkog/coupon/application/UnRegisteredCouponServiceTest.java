package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.무기명_COFFEE_쿠폰_발급_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("UnRegisteredCouponService의")
public class UnRegisteredCouponServiceTest {

    @Autowired
    private UnregisteredCouponService unregisteredCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Nested
    @DisplayName("save 메서드는")
    class Save {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("무기명 쿠폰들을 최대 5개 생성하고, 생성된 쿠폰들을 반환한다.")
        void success_saveAll() {
            UnregisteredCouponSaveRequest request = 무기명_COFFEE_쿠폰_발급_요청(sender.getId(), 5);

            List<UnregisteredCouponResponse> actual = unregisteredCouponService.save(request);

            assertThat(actual).hasSize(5);
        }

        @Test
        @DisplayName("수량이 0개 이하 또는 5개 초과이면, 예외를 던진다.")
        void fail_quantity_excess() {
            UnregisteredCouponSaveRequest request = 무기명_COFFEE_쿠폰_발급_요청(sender.getId(), 6);

            assertThatThrownBy(() -> unregisteredCouponService.save(request))
                .isInstanceOf(UnregisteredCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("find 메서드는")
    class Find {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("무기명 쿠폰 아이디를 받으면, 상세 정보를 반환한다.")
        void success() {
            List<UnregisteredCouponResponse> response = unregisteredCouponService.save(
                무기명_COFFEE_쿠폰_발급_요청(sender.getId(), 1));
            Long unregisteredCouponId = response.get(0).getId();

            var actual = unregisteredCouponService.find(unregisteredCouponId);

            Long id = actual.getSender().getId();
            assertAll(
                () -> assertThat(id).isEqualTo(sender.getId()),
                () -> assertThat(actual.getDeleted()).isFalse());
        }
    }
}
