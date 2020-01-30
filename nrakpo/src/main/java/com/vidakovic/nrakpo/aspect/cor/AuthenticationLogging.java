package com.vidakovic.nrakpo.aspect.cor;

import org.springframework.security.core.Authentication;

import java.util.List;

public class AuthenticationLogging extends AbstractLogger{

    @Override
    protected String appendLog(String log, List<Object> params) {
        for (Object o :
                params) {
            if(o instanceof Authentication){
                return log+ " Username: "+((Authentication)o).getName()+", ";
            }
        }
        return log;
    }
}

