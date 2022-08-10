package com.woowacourse.kkogkkog.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.kkogkkog.application.AuthService;
import com.woowacourse.kkogkkog.application.JwtTokenProvider;
import com.woowacourse.kkogkkog.application.MemberService;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.presentation.CouponController;
import com.woowacourse.kkogkkog.presentation.AuthController;
import com.woowacourse.kkogkkog.presentation.MemberController;
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
@WebMvcTest({
    AuthController.class,
    CouponController.class,
    MemberController.class,
})
public abstract class Documentation {

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
