package com.dropwizard.core;

/**
 * Created by SauravKumar on 30-05-2016.
 */
public class User
{
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;
    private String userId;

    public User(String name, String userId)
    {
        this.name = name;
        this.userId = userId;
    }

    public User()
    {
        this.name = name;
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public String getUserId()
    {
        return userId;
    }

}
