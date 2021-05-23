package com.annette.cw.controller;

import com.annette.cw.entity.Decision;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;

import java.util.List;

  public class Controller {
    private volatile static Controller instance;
    private User selfUser;
    private User changeableUser;
    private List<User> userList;
    private Organization changeableOrg;
    private List<Organization> organizationList;
    private Decision changeableDecision;
    private List<Decision> decisionList;


    private Controller() {}

    synchronized public User getSelfUser() {
        return selfUser;
    }

    synchronized public void setSelfUser(User selfUser) {
        this.selfUser = selfUser;
    }

    synchronized public User getChangeableUser() {
        return changeableUser;
    }

    synchronized public void setChangeableUser(User changeableUser) {
        this.changeableUser = changeableUser;
    }

    synchronized public List<User> getUserList() {
        return userList;
    }

    synchronized public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    synchronized public Organization getChangeableOrg() {
        return changeableOrg;
    }

    synchronized public void setChangeableOrg(Organization changeableOrg) {
        this.changeableOrg = changeableOrg;
    }

    synchronized public List<Organization> getOrganizationList() {
        return organizationList;
    }

    synchronized public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    synchronized public Decision getChangeableDecision() {
        return changeableDecision;
    }

    synchronized public void setChangeableDecision(Decision changeableDecision) {
        this.changeableDecision = changeableDecision;
    }

    synchronized public List<Decision> getDecisionList() {
        return decisionList;
    }

    synchronized public void setDecisionList(List<Decision> decisionList) {
        this.decisionList = decisionList;
    }

    synchronized public static Controller getInstance() {
        if (instance == null) {
            synchronized (Controller.class) {
                if (instance == null) {
                    instance = new Controller();
                }
            }
        }
        return instance;
    }
    synchronized public void clear(){
        this.userList = null;
        this.changeableDecision = null;
        this.changeableOrg = null;
        this.changeableUser = null;
        this.decisionList = null;
        this.organizationList = null;
        this.selfUser = null;
    }
}
