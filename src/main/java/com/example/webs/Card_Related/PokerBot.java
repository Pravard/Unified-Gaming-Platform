package com.example.webs.Card_Related;

import java.util.Random;

public class PokerBot { //Strategy Pattern
    public Poker poker;
    public int id;
    public String risk;
    public PokerBot(Poker poker,int id, String risk)
    {
        this.poker = poker;
        this.id = id;
        this.risk = risk;
    }
    public String makeDecision()
    {
        Random rand = new Random();
        int risk_taken = rand.nextInt(100);
        if(risk.equals("high"))
        {
            if(risk_taken<10-poker.point(id))
            {
                return "fold";
            }
            else if(risk_taken<100-poker.point(id)*3)
            {
                return "check";
            }
            else
            {
                return "raise";
            } 
        }
        else if(risk.equals("low"))
        {
            if(risk_taken<20-poker.point(id)*2)
            {
                return "fold";
            }
            else if(10*risk_taken<100-poker.point(id)*75)
            {
                return "check";
            }
            else
            {
                return "raise";
            } 
        }
        else
        {
            if(risk_taken<15-poker.point(id))
            {
                return "fold";
            }
            else if(risk_taken<100-poker.point(id)*5)
            {
                return "check";
            }
            else
            {
                return "raise";
            } 
        }
    }

}
