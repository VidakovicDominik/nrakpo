package com.vidakovic.nrakpo.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GOLD")
public class GoldUser extends User {
}
