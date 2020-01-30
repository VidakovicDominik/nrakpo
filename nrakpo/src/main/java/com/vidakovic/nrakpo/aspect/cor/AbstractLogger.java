package com.vidakovic.nrakpo.aspect.cor;


import java.util.List;

public abstract class AbstractLogger {

    protected AbstractLogger nextLogger;

    public void setNextFilter(AbstractLogger logger){
        this.nextLogger =logger;
    }

    public String getLog(String log, List<Object> params){
        log=appendLog(log, params);

        if(nextLogger !=null){
            log=nextLogger.getLog(log, params);
        }
        return log;
    }

    protected abstract String appendLog(String log, List<Object> params);
}

