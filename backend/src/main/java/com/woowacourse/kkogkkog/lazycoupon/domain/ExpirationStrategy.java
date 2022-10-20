package com.woowacourse.kkogkkog.lazycoupon.domain;

import java.time.LocalDateTime;

@FunctionalInterface
public interface ExpirationStrategy {

    boolean isNotExpired(LocalDateTime createTime);
}
