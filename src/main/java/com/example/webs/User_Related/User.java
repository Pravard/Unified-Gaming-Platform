package com.example.webs.User_Related;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
    @Id
    public String username;
    @Column
    public String password;
    @Column
    public int virtual_points = 10000;
    public User(String username, String password, int virtual_points)
    {
        this.username = username;
        this.password = password;
        this.virtual_points = virtual_points;
    }
    public User()
    {}
    public String getusername()
    {
        return this.username;
    }
    public String toString()
    {
        return username+" "+password+" "+virtual_points;
    }
    public static void main(String[] args)
    {
        System.out.println("Hello");
    }

}
