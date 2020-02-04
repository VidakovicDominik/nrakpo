package com.vidakovic.nrakpo.aspect;

import com.vidakovic.nrakpo.aspect.timer.TimerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeasureTimeAspect {

    @Autowired
    TimerService timerService;

    @Around("@annotation(com.vidakovic.nrakpo.aspect.MeasureTime)")
    public Object validateActionRequest(final ProceedingJoinPoint joinPoint) throws Throwable {
        String meterName = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(MeasureTime.class).metricName();
        timerService.start();
        Object jointPointResult = joinPoint.proceed();
        timerService.stopAndRecord(meterName);
        return jointPointResult;
    }


}

