package com.usman.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 22-12-2022
 * @since : 1.0.0
 */
@Slf4j
@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class LogExecutionTimeAdvice {

    @Around(("execution(* (@com.usman.springboot.annotation.LogExecutionTime *).*(..))"))
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("API_CLASS Name : {}, Method Name:{}, TOTAL_EXECUTION_TIME_IS :{}  {} ",
            point.getSignature().getDeclaringTypeName(), point.getSignature().getName(),
            (endTime - startTime), " ms");
        return object;
    }

    @Around("@annotation(com.usman.springboot.annotation.LogMethodExecutionTime)")
    public Object methodExecutionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("API_CLASS For USMAN API : {}, Method Name:{}, TOTAL_EXECUTION_TIME_IS :{}  {} ",
            point.getSignature().getDeclaringTypeName(), point.getSignature().getName(),
            (endTime - startTime), " ms");
        return object;
    }
}
