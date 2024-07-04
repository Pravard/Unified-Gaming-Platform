package com.example.webs.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webs.Repositories.HighscoreRepository;
import com.example.webs.User_Related.Highscore;

@Service
public class HighscoreService { //Facade Pattern
    private final HighscoreRepository highscoreRepository;

    @Autowired
    public HighscoreService(HighscoreRepository highscoreRepository)
    {
        this.highscoreRepository = highscoreRepository;
    }
    public ArrayList<Highscore> fetchtopten()
    {
        List<Highscore> all = highscoreRepository.findAll();
        ArrayList<Highscore> ret = new ArrayList<>();
        int m=10;
        if(all.size()<10)
        {
            m = all.size();
        }
        Collections.sort(all,new Comparator<Highscore>() {
            @Override
            public int compare(Highscore h1,Highscore h2)
            {
                return Integer.compare(h1.score, h2.score);
            }
        
        });
        all = all.reversed();
        for(int i=0;i<m;i++)
        {
            ret.add(all.get(i));
        }
        return ret;
    }
    public void saveHS(Highscore highscore)
    {
        highscoreRepository.save(highscore);
    }

}
