package com.woowacourse.kkogkkog.auth.presentation;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer".toLowerCase();

    public static String extractBearerToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (isBearerScheme(header)) {
                return extractSingleBearerToken(header);
            }
        }
        return null;
    }

    private static boolean isBearerScheme(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE);
    }

    private static String extractSingleBearerToken(String header) {
        String headerValue = header.substring(BEARER_TYPE.length()).trim();
        int commaIndex = headerValue.indexOf(',');
        if (commaIndex > 0) {
            headerValue = headerValue.substring(0, commaIndex);
        }
        return headerValue;
    }
}
