package com.vidakovic.nrakpo.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GITHUB")
public class GitHubUser extends User {
    public GitHubUser() {
        super();
    }
}
