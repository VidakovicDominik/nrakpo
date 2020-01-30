package com.vidakovic.nrakpo.aspect.cor;

import com.vidakovic.nrakpo.controller.apimodel.LoggableApiModel;

import java.util.List;

public class ApiModelLogging extends AbstractLogger {

    @Override
    protected String appendLog(String log, List<Object> params) {
        for (Object o :
                params) {
            if(o instanceof LoggableApiModel){
                return log+ " Received data: "+((LoggableApiModel)o).logText()+", ";
            }
        }
        return log;
    }
}
