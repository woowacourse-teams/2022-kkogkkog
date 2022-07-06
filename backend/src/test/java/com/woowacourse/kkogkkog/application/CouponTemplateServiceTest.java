package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponTemplateResponse;
import com.woowacourse.kkogkkog.application.dto.CouponTemplatesResponse;
import com.woowacourse.kkogkkog.domain.CouponTemplate;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.repository.CouponTemplateRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CouponTemplateServiceTest {

    @Autowired
    private CouponTemplateService couponTemplateService;

    @Autowired
    private CouponTemplateRepository couponTemplateRepository;

    @BeforeEach
    void setUp() {
        couponTemplateRepository.save(new CouponTemplate(1L, 1L, "한턱쏘는", "#352446", CouponType.COFFEE));
        couponTemplateRepository.save(new CouponTemplate(2L, 1L, "주는", "#352446", CouponType.MEAL));
    }

    @Test
    @DisplayName("쿠폰 템플릿을 전체 조회할 수 있다.")
    void findAll() {
        CouponTemplatesResponse actual = couponTemplateService.findAll();

        CouponTemplatesResponse expect = new CouponTemplatesResponse(List.of(
                new CouponTemplateResponse(1L, "한턱쏘는", "#352446", CouponType.COFFEE.name()),
                new CouponTemplateResponse(2L, "주는", "#352446", CouponType.MEAL.name())
        ));
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expect);
    }
}
