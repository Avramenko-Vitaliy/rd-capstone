package com.todo.api.configurations;

import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@CommonsLog
public class ApplicationLogger {

    @Around("execution(* com.rd.api.controllers.*.*(..))")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        if (className.contains("Mockito")) {
            return retVal;
        }
        stopWatch.stop();

        String args = Arrays.stream(joinPoint.getArgs())
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String logMessage = String.format(
                "%s.%s(%s) execution time: %d ms. returned %s",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                args,
                stopWatch.getTotalTimeMillis(),
                retVal
        );
        log.info(logMessage);
        return retVal;
    }
}
