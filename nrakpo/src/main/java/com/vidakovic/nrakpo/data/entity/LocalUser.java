package com.vidakovic.nrakpo.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LOCAL")
public class LocalUser extends User {
    public LocalUser() {
        super();
    }
}
