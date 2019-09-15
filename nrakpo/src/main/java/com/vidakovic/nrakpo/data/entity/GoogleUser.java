package com.vidakovic.nrakpo.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GOOGLE")
public class GoogleUser extends User {

    public GoogleUser() {
        super();
    }
}
