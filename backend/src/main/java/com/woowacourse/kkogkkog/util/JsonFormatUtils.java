package com.woowacourse.kkogkkog.util;

import java.time.LocalDateTime;

public class JsonFormatUtils {

    public static String toLocalDate(LocalDateTime meetingDate) {
        if (meetingDate == null) {
            return null;
        }
        return String.format("%s-%s-%s", meetingDate.getYear(),
            addLeadingZero(meetingDate.getMonthValue()),
            addLeadingZero(meetingDate.getDayOfMonth()));
    }

    private static String addLeadingZero(int time) {
        if (time >= 10) {
            return "" + time;
        }
        return "0" + time;
    }
}
