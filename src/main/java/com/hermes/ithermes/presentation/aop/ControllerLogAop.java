package com.hermes.ithermes.presentation.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ControllerLogAop {
    @Around("within(com.hermes.ithermes.presentation.controller.*)")
    public Object checkLog(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName() + "()";
            log.info("className : {} | methodName : {} server | transaction Processing Time : {} ms", className, methodName, stopWatch.getLastTaskTimeMillis());
        }
    }
}