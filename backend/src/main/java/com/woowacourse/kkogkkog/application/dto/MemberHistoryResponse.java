package com.woowacourse.kkogkkog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberHistoryResponse {

    private Long id;
    private String nickname;
    private String imageUrl;
    private Long couponId;
    private String couponType;
    private String couponEvent;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    public MemberHistoryResponse(Long id,
                                 String nickname,
                                 String imageUrl,
                                 Long couponId,
                                 String couponType,
                                 String couponEvent,
                                 LocalDate meetingDate) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
    }

    @Override
    public String toString() {
        return "MemberHistoryResponse{" +
            "id=" + id +
            ", nickname='" + nickname + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", couponId=" + couponId +
            ", couponType='" + couponType + '\'' +
            ", couponEvent='" + couponEvent + '\'' +
            ", meetingDate=" + meetingDate +
            '}';
    }
}
