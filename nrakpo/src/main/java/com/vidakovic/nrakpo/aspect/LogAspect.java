package com.vidakovic.nrakpo.aspect;

import com.vidakovic.nrakpo.aspect.cor.LoggingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    LoggingService loggingService;

    public LogAspect(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Before("@annotation(com.vidakovic.nrakpo.aspect.Log)")
    public void writeLog(final JoinPoint joinPoint) {
        loggingService.logMethodInformation(joinPoint);
    }

}

