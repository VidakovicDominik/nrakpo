package com.vidakovic.nrakpo.service.strategy;

import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import org.springframework.stereotype.Service;

@Service
public class PackageUsageService {
    private Context strategyContex=new Context();

    public boolean exeededLimit(User user, Long uploadedPictures){
        if(user.getUserPackage().equals(UserPackage.FREE)){
            strategyContex.setStrategy(new FreeStrategy());
        }
        else if(user.getUserPackage().equals(UserPackage.PRO)){
            strategyContex.setStrategy(new ProStrategy());
        }
        else if(user.getUserPackage().equals(UserPackage.GOLD)){
            strategyContex.setStrategy(new GoldStrategy());
        }
        return strategyContex.executeStrategy(uploadedPictures);
    }
}
