package com.example.webs.Weather_Related;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class WeatherPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    public String username;
    @Column
    public float Temperature;
    @Column
    public float WindSpeed;
    @Column
    public Date date;

    public WeatherPrediction()
    {}

}
