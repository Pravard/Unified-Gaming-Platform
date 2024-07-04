package com.example.webs.Card_Related;
import java.util.*;
public class CardDeck //Builder
{
    Random r = new Random();
    public ArrayList<PlayingCard> cards;
    public CardDeck(boolean shuffled)
    {
        ArrayList<PlayingCard> pcards = new ArrayList<PlayingCard>();
        
        ArrayList<String> suits = new ArrayList<>(Arrays.asList("Clubs","Diamonds","Hearts","Spades"));
        for(int i=0;i<suits.size();i++)
        {
            for(int j=1;j<14;j++)
            {
                PlayingCard pres_card = new PlayingCard(suits.get(i), j);
                pcards.add(pres_card);
            }
        }
        if(shuffled)
        {
            Random random = new Random();  
            for(int index = 0; index < pcards.size(); index += 1) 
            {  
                Collections.swap(pcards, index, index + random.nextInt(pcards.size() - index));  
            }
        }
        cards = pcards;
    }
    public PlayingCard Draw()
    {
        PlayingCard top_card = cards.get(0);
        cards.remove(0);
        return top_card;
    }
    public static void main(String[] args) {
        CardDeck rot = new CardDeck(false);
        PlayingCard top = rot.Draw();
        System.out.println(top.toString());
        System.err.println(rot.cards.toString());
    }
}