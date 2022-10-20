package com.woowacourse.kkogkkog.support.application;

import com.woowacourse.kkogkkog.acceptance.support.TestConfig;
import com.woowacourse.kkogkkog.support.common.DataClearExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ExtendWith(DataClearExtension.class)
@Import(TestConfig.class)
public @interface ApplicationTest {

}
