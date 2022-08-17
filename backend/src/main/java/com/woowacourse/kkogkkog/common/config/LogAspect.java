package com.woowacourse.kkogkkog.common.config;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private static final String TRACING_ID = "trace_id";

    @Pointcut("execution(* com.woowacourse.kkogkkog.presentation.*.*(..) )")
    public void controllerAdvice() {
    }

    @Before("controllerAdvice()")
    public void requestLogging() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        MDC.put(TRACING_ID, UUID.randomUUID().toString());
        log.info("Request : {} {}, requestParams = ({}),", request.getMethod(),
            request.getRequestURL(), paramMapToString(request.getParameterMap()));
    }

    @AfterReturning(pointcut = "controllerAdvice()", returning = "returnValue")
    public void responseLogging(JoinPoint joinPoint, Object returnValue) {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) returnValue;
        log.info("Response {} : {}", responseEntity.getStatusCode(), joinPoint.getSignature().getName());
        MDC.clear();
    }

    @AfterThrowing(pointcut = "controllerAdvice()", throwing = "exception")
    public void exceptionLogging(Exception exception) {
        log.info("Exception has been thrown : ", exception);
    }

    private String paramMapToString(Map<String, String[]> paraStringMap) {
        return paraStringMap.entrySet().stream()
            .map(entry -> String.format("%s : %s",
                entry.getKey(), Arrays.toString(entry.getValue())))
            .collect(Collectors.joining(", "));
    }
}
