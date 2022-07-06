package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponTemplateResponse;
import com.woowacourse.kkogkkog.application.dto.CouponTemplatesResponse;
import com.woowacourse.kkogkkog.domain.CouponTemplate;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.repository.CouponTemplateRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("쿠폰 템플릿 관련")
public class CouponTemplateAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CouponTemplateRepository couponTemplateRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        couponTemplateRepository.save(new CouponTemplate(1L, 1L, "한턱쏘는", "#352446", CouponType.COFFEE));
        couponTemplateRepository.save(new CouponTemplate(2L, 1L, "주는", "#352446", CouponType.MEAL));
    }

    @Test
    void 전쳬_쿠폰_템플릿을_조회_할_수_있다() {
        CouponTemplatesResponse actual = 쿠폰_템플릿_전체_조회에_성공한다();
        CouponTemplatesResponse expected = new CouponTemplatesResponse(List.of(
                new CouponTemplateResponse(1L, "한턱쏘는", "#352446", CouponType.COFFEE.name()),
                new CouponTemplateResponse(2L, "주는", "#352446", CouponType.MEAL.name())
        ));

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public CouponTemplatesResponse 쿠폰_템플릿_전체_조회에_성공한다() {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when()
                .get("/api/templates")
                .then().log().all()
                .extract();

        CouponTemplatesResponse couponTemplateResponse = extract.as(CouponTemplatesResponse.class);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());

        return couponTemplateResponse;
    }
}
