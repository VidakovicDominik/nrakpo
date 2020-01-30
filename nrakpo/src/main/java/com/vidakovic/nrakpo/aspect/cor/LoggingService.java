package com.vidakovic.nrakpo.aspect.cor;

import com.vidakovic.nrakpo.aspect.Log;
import com.vidakovic.nrakpo.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    public void logMethodInformation(JoinPoint joinPoint){
        String message= ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class).message();
        String methodName= joinPoint.getSignature().getName();
        String methodParameters= getMethodParameterLogs(joinPoint.getArgs());

        String log=new DateUtil().getSimpleDateAndTime(new Date().getTime())+": "+message+ ", "+ methodName+", "+methodParameters;
        LOGGER.info(log);
    }

    private String getMethodParameterLogs(Object[] params){
        AbstractLogger authLogger=new AuthenticationLogging();
        AbstractLogger apiModelLogger=new ApiModelLogging();

        authLogger.setNextFilter(apiModelLogger);

        return authLogger.getLog("Method parameters->", Arrays.asList(params));
    }
}

