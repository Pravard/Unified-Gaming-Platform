package com.example.webs.Card_Related;

import java.util.ArrayList;

public class BlackJack {
    public CardDeck deck = new CardDeck(true);
    public ArrayList<PlayingCard> dealerHand = new ArrayList<>();
    public ArrayList<PlayingCard> playerHand = new ArrayList<>();
    public BlackJack()
    {

    }
    public void dealCards()
    {
        dealerHand.clear();
        playerHand.clear();
        for(int i=0;i<2;i++)
        {
            PlayingCard next = deck.Draw();
            dealerHand.add(next);
            next = deck.Draw();
            playerHand.add(next);
        }
    }
    
    public int playerPoints()
    {
        int j = 0;
        int aces = 0;
        for(int i=0;i<playerHand.size();i++)
        {
            if(playerHand.get(i).getValue() == 1)
            {
                aces+=1;
            }
            if(playerHand.get(i).getValue() >=10)
            {
                j+=10;
            }
            else
            {
                j+=playerHand.get(i).getValue();
            }
        }
        if(aces>=1 && j<=11)
        {
            j+=10;
        }
        return j;
    }
    public int dealerPoints()
    {
        int j = 0;
        int aces = 0;
        for(int i=0;i<dealerHand.size();i++)
        {
            if(dealerHand.get(i).getValue() == 1)
            {
                aces+=1;
            }
            if(dealerHand.get(i).getValue() >=10)
            {
                j+=10;
            }
            else
            {
                j+=dealerHand.get(i).getValue();
            }
        }
        if(aces>=1 && j<=11)
        {
            j+=10;
        }
        return j;
    }
    public void dealerHit()
    {
        PlayingCard next = deck.Draw();
        dealerHand.add(next);
    }
    public void playerHit()
    {
        PlayingCard next = deck.Draw();
        playerHand.add(next);
    }
    public ArrayList<String> mask()  //Pure Fabrication
    {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("/back.jpg");
        for(int i=1;i<dealerHand.size();i++)
        {
            ret.add(dealerHand.get(i).getImgPath());
        }
        return ret;
    }
    public static void main(String[] args) {
        BlackJack new_game = new BlackJack();
        new_game.dealCards();
        System.out.println(new_game.dealerHand.toString());
        System.out.println(new_game.playerHand.toString());
        System.out.println(new_game.dealerPoints());
        System.out.println(new_game.playerPoints());
        new_game.dealerHit();
        new_game.playerHit();
        System.out.println(new_game.dealerHand.toString());
        System.out.println(new_game.playerHand.toString());
        System.out.println(new_game.dealerPoints());
        System.out.println(new_game.playerPoints());
    }
}