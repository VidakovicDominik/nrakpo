package com.vidakovic.nrakpo.service.singleton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static Logger instance=new Logger();

    private Logger(){}

    public static Logger getInstance(){
        return instance;
    }

    public void log(String username, String action){
        System.out.println("User: "+username+", Action: "+ action+", Time: "+
                new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
    }
}
