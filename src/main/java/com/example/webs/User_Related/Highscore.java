package com.example.webs.User_Related;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Highscore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    public String username;
    @Column
    public int score;
    @Column
    public String game;

    public Highscore(String username,String game, int points)
    {
        this.username = username;
        this.game = game;
        this.score = points;
    }
    public Highscore()
    {}
}
