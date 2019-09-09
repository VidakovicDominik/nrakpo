package com.vidakovic.nrakpo.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRO")
public class ProUser extends User {
}
