package com.vidakovic.nrakpo.data.entity;

import com.vidakovic.nrakpo.data.entity.enums.ActionType;

import javax.persistence.*;

@Entity
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    private ActionType actionType;

    private String description;

    private Long date;

    public UserAction(User user, ActionType actionType, String description, Long date) {
        this.user = user;
        this.actionType = actionType;
        this.description = description;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}

