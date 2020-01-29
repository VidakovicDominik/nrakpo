package com.vidakovic.nrakpo.aspect;

import com.vidakovic.nrakpo.controller.apimodel.LoggableApiModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <b>Title: MeasureTimeAspect  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2020
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 16-Jan-20
 * @since 16-Jan-20 09:44:42
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(com.vidakovic.nrakpo.aspect.Log)")
    public void writeLog(final JoinPoint joinPoint) {
        Authentication auth = null;
        LoggableApiModel apiModel=null;
        for (Object param :
                joinPoint.getArgs()) {
            if (param instanceof Authentication) {
                auth = (Authentication) param;
            }
            if (param instanceof LoggableApiModel) {
                apiModel = (LoggableApiModel) param;
            }
        }
        String message = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class).message();
        String methodName = joinPoint.getSignature().getName();
        String user = auth == null ? "" : "User: " + auth.getName() + ", ";
        String log=apiModel==null?"":apiModel.logText();
        LOG.info(user + methodName + log+ " : " + message);
    }



}

