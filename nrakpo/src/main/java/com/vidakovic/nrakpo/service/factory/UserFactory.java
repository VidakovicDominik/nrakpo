package com.vidakovic.nrakpo.service.factory;

import com.vidakovic.nrakpo.data.entity.GitHubUser;
import com.vidakovic.nrakpo.data.entity.GoogleUser;
import com.vidakovic.nrakpo.data.entity.LocalUser;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;

public class UserFactory {

    public User getUser(AccountType type){
        if(type.equals(AccountType.LOCAL)){
            return new LocalUser();
        }
        if(type.equals(AccountType.GITHUB)){
            return new GitHubUser();
        }
        if(type.equals(AccountType.GOOGLE)){
            return new GoogleUser();
        }
        return null;
    }
}
