package com.woowacourse.kkogkkog.support.documenation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.kkogkkog.auth.application.AuthService;
import com.woowacourse.kkogkkog.auth.support.JwtTokenProvider;
import com.woowacourse.kkogkkog.coupon2.application.CouponService;
import com.woowacourse.kkogkkog.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest
public abstract class DocumentTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CouponService couponService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected AuthService authService;

//    @MockBean
//    protected ReservationService reservationService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @BeforeEach
    public void setUp(WebApplicationContext ctx,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }
}
