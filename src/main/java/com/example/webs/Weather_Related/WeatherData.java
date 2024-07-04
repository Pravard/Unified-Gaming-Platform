package com.example.webs.Weather_Related;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class WeatherData {
    public float temp;
    public float wind;
    public WeatherData()
    {}
    public void callWeatherAPI(String place) {
        String apiKey = "f11725e805e3f0202bc5a82c48531903";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + place + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
            JSONObject response = new JSONObject(jsonResponse);
            this.temp = response.getJSONObject("main").getFloat("temp");
            this.temp -= 273.15;
            this.wind = response.getJSONObject("wind").getFloat("speed");
        } catch (Exception e) { //Controlled Variation
            this.temp = 25;
            this. wind = 5;
        
    }
    }
    public static void main(String[] args) {
        WeatherData data = new WeatherData();
        data.callWeatherAPI("Bangalore");
        System.out.println(data.temp);
        System.out.println(data.wind);
        Date currentDate = new Date();
        String currenthour = new SimpleDateFormat("dd").format(currentDate);
        System.out.println(currenthour);    
    }
    
}
