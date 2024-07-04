package com.example.webs.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webs.Weather_Related.WeatherPrediction;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherPrediction,String> {

}

    

