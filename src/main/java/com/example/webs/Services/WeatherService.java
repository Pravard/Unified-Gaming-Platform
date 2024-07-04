package com.example.webs.Services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webs.Repositories.WeatherRepository;
import com.example.webs.Weather_Related.WeatherPrediction;

@Service
public class WeatherService { //Facade pattern
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository)
    {
        this.weatherRepository = weatherRepository;
    }

    public ArrayList<WeatherPrediction> getPredictionsByUsername(String username1)
    {
        List<WeatherPrediction> all = weatherRepository.findAll();
        ArrayList<WeatherPrediction> ret = new ArrayList<>();
        for(int i=0;i<all.size();i++)
        {
            if(all.get(i).username.equals(username1))
            ret.add(all.get(i));
        }
        return ret;
    }

    public void addPrediction(WeatherPrediction prediction)
    {
        List<WeatherPrediction> all = weatherRepository.findAll();
        String ah = new SimpleDateFormat("dd").format(prediction.date);
        for(int i=0;i<all.size();i++)
        {
            String ph = new SimpleDateFormat("dd").format(all.get(i).date);
            if(prediction.username.equals(all.get(i).username) && ah.equals(ph))
            {
                throw new IllegalStateException("Already added prediction!!");
            }
        }
        weatherRepository.save(prediction);
    }

    public ArrayList<WeatherPrediction> getallbytoday()
    {
        ArrayList<WeatherPrediction> ret = new ArrayList<>();
        List<WeatherPrediction> all = weatherRepository.findAll();
        Date today = new Date();
        int tod = Integer.parseInt(new SimpleDateFormat("dd").format(today));
        for(int i=0;i<all.size();i++)
        {
            if(Integer.parseInt(new SimpleDateFormat("dd").format(all.get(i).date))==tod)
            {
                ret.add(all.get(i));
            }
        }
        return ret;
    }
}
