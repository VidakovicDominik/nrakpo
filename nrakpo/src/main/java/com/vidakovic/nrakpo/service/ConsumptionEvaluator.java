package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <b>Title: ConsumptionEvaluator  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2019
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
 * PA1 23-Aug-19
 * @since 23-Aug-19 13:45:42
 */

@Service
public class ConsumptionEvaluator {
    private static final long FREE=5;
    private static final long PRO=10;

    public boolean evaluate(User user, Long count){
        if (user.getUserPackage().equals(UserPackage.FREE)){
            if(count>FREE){
                return false;
            }
            else
                return true;
        }
        else if(user.getUserPackage().equals(UserPackage.PRO)){
            if(count>PRO){
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }

}

