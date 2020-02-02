package com.vidakovic.nrakpo.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * <b>Title: DateUtil  </b>
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
 * PA1 28-Jan-20
 * @since 28-Jan-20 15:29:42
 */
@Component
public class DateUtil {

    public String getSimpleDate(long date){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(date));
    }

    public String getSimpleDateAndTime(long date){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(date));
    }

    public long getLongDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

