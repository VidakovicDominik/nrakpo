package com.vidakovic.nrakpo.aspect;

import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.Duration;

@Aspect
@Component
public class MeasureTimeAspect {

    @Autowired
    PrometheusMeterRegistry meterRegistry;

    @Around("@annotation(com.vidakovic.nrakpo.aspect.MeasureTime)")
    public Object validateActionRequest(final ProceedingJoinPoint joinPoint) throws Throwable {
        String meterName = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(MeasureTime.class).metricName();
        Timer testTimer = meterRegistry.timer(meterName);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object jointPointResult = joinPoint.proceed();
        stopWatch.stop();
        testTimer.record(Duration.ofMillis(stopWatch.getLastTaskTimeMillis()));
        return jointPointResult;
    }


}

