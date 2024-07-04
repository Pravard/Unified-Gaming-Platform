package com.example.webs.Card_Related;
import java.util.*;
public class Poker {
    public CardDeck s_deck = new CardDeck(true);
    public ArrayList<PlayingCard> Hand = new ArrayList<>();
    public ArrayList<ArrayList<PlayingCard>> Players = new ArrayList<>();
    public int Num_Players;
    public int Pot;
    public int pay_per_person;
    public int turn;
    public ArrayList<Integer> folded = new ArrayList<>();
    public Poker(int Num_Players)
    {
        ArrayList<PlayingCard> front = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            PlayingCard top = s_deck.Draw();
            front.add(top);
        }
        ArrayList<ArrayList<PlayingCard>> All_Players = new ArrayList<>();
        for(int i=0;i<Num_Players;i++)
        {
            ArrayList<PlayingCard> pres_Player = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                PlayingCard top = s_deck.Draw();
                pres_Player.add(top);
            }
            All_Players.add(pres_Player);
            folded.add(0);
        }
        Hand = front;
        Players = All_Players;
    }
    public Poker(){
        
    }
    public void generateCards()
    {
        pay_per_person = 20;
        ArrayList<PlayingCard> front = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            PlayingCard top = s_deck.Draw();
            front.add(top);
        }
        ArrayList<ArrayList<PlayingCard>> All_Players = new ArrayList<>();
        for(int i=0;i<Num_Players;i++)
        {
            ArrayList<PlayingCard> pres_Player = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                PlayingCard top = s_deck.Draw();
                pres_Player.add(top);
            }
            All_Players.add(pres_Player);
            folded.add(0);
        }
        this.Hand = front;
        this.Players = All_Players;
    }
    public ArrayList<String> mask() // Pure Fabrication
    {
        ArrayList<String> ret = new ArrayList<>();
        if(turn <= 1)
        {
            for(int i=0;i<5;i++)
            {
                ret.add("/back.jpg");
            }
        }
        else if(turn == 2)
        {
            for(int i=0;i<3;i++)
            {
                ret.add(Hand.get(i).getImgPath());
            }
            for(int i=0;i<2;i++)
            {
                ret.add("/back.jpg");
            }
        }
        else if(turn == 3)
        {
            for(int i=0;i<4;i++)
            {
                ret.add(Hand.get(i).getImgPath());
            }
            ret.add("/back.jpg");
        }
        else
        {
            for(int i=0;i<5;i++)
            {
                ret.add(Hand.get(i).getImgPath());
            }
        }
        return ret;
    }
    public ArrayList<Integer> points()
    {
        ArrayList<Integer> points = new ArrayList<>();
        return points;
    }
    public int point(int k)
    {
        ArrayList<PlayingCard> Player = Players.get(k); 
        List<PlayingCard> total_hand = new ArrayList<PlayingCard>();
        for(PlayingCard i:Hand)
        {
            total_hand.add(i);
        }
        for(PlayingCard i:Player)
        {
            total_hand.add(i);
        }
        Collections.sort(total_hand,new Comparator<PlayingCard>()
        {
            public int compare(PlayingCard p1,PlayingCard p2)
            {
                return Integer.compare(p1.getValue(), p2.getValue());
            }
        });
        int nspades = 0;
        int nclubs = 0;
        int ndiamonds = 0;
        int nhearts = 0;
        int cont = 0;
        int maxcont = 0;
        int conth = 0;
        int maxconth = 0;
        int mkind = 0;
        int nkind = 0;
        int ckind = 1;
        int prev = -1;
        ArrayList<Integer> chearts = new ArrayList<>();
        ArrayList<Integer> cclubs = new ArrayList<>();
        ArrayList<Integer> cdiamonds = new ArrayList<>();
        ArrayList<Integer> cspades = new ArrayList<>();
        for(PlayingCard i:total_hand)
        {
            if(i.getSuit().equals(new String("Spades")))
            {
                nspades++;
                cspades.add(i.getValue());
            }
            if(i.getSuit().equals(new String("Clubs")))
            {
                nclubs++;
                cclubs.add(i.getValue());
            }
            if(i.getSuit().equals(new String("Hearts")))
            {
                nhearts++;
                chearts.add(i.getValue());
            }
            if(i.getSuit().equals(new String("Diamonds")))
            {
                ndiamonds++;
                cdiamonds.add(i.getValue());
            }
            if(prev+1 == i.getValue())
            {
                cont++;
                conth = i.getValue();
            }
            else if(prev!=i.getValue())
            {
            if(cont>maxcont)
            {
                maxcont = cont;
                maxconth = conth;
                cont = 0;
            }
            else
            {
                cont = 0;
            }}
            if(prev == i.getValue())
            {
                ckind++;
            }
            else if(ckind>mkind)
            {
                nkind = mkind;
                mkind = ckind;
                ckind = 1;
            }
            else if(ckind>nkind)
            {
                nkind = ckind;
                ckind = 1;
            }
            else
            {
                ckind = 1;
            }
            prev = i.getValue();
        }
        if(cont>maxcont)
        {
            maxcont = cont;
        }
        if(ckind>mkind)
        {
            nkind = mkind;
            mkind = ckind;
        }
        else if(ckind>nkind)
        {
            nkind = ckind;
        }
        ckind = 1;
        cont = 0;
        if(maxcont==3 && total_hand.get(0).getValue()==1)
        {
            if(maxconth == 13)
            {
                if(nhearts > 5)
                {
                    if(chearts.get(0) == 1 && chearts.get(chearts.size()-1) == 13 && chearts.get(chearts.size()-2) == 12 && chearts.get(chearts.size()-3) == 11 && chearts.get(chearts.size()-4) == 10)
                    {
                        return 10;
                    }
                }
                if(nclubs > 5)
                {
                    if(cclubs.get(0) == 1 && cclubs.get(cclubs.size()-1) == 13 && cclubs.get(cclubs.size()-2) == 12 && cclubs.get(cclubs.size()-3) == 11 && cclubs.get(cclubs.size()-4) == 10)
                    {
                        return 10;
                    }
                }
                if(nspades > 5)
                {
                    if(cspades.get(0) == 1 && cspades.get(cspades.size()-1) == 13 && cspades.get(cspades.size()-2) == 12 && cspades.get(cspades.size()-3) == 11 && cspades.get(cspades.size()-4) == 10)
                    {
                        return 10;
                    }
                }
                if(ndiamonds > 5)
                {
                    if(cdiamonds.get(0) == 1 && cdiamonds.get(cdiamonds.size()-1) == 13 && cdiamonds.get(cdiamonds.size()-2) == 12 && cdiamonds.get(cdiamonds.size()-3) == 11 && cdiamonds.get(cdiamonds.size()-4) == 10)
                    {
                        return 10;
                    }
                }
            }
        }
        else if(maxcont == 4)
        {
            if(nhearts > 5)
            {
                for(int i = 0;i<chearts.size();i++)
                {
                    if(chearts.get(i) == maxconth && i>4)
                    {
                        if(chearts.get(i-1) == maxconth-1 && chearts.get(i-2) == maxconth-2 && chearts.get(i-3) == maxconth-3 && chearts.get(i-4) == maxconth-4)
                        {
                            return 9;
                        }
                    }
                }
            }
            if(nclubs > 5)
            {
                for(int i = 0;i<cclubs.size();i++)
                {
                    if(cclubs.get(i) == maxconth && i>4)
                    {
                        if(cclubs.get(i-1) == maxconth-1 && cclubs.get(i-2) == maxconth-2 && cclubs.get(i-3) == maxconth-3 && cclubs.get(i-4) == maxconth-4)
                        {
                            return 9;
                        }
                    }
                }
            }
            if(nspades > 5)
            {
                for(int i = 0;i<cspades.size();i++)
                {
                    if(cspades.get(i) == maxconth && i>4)
                    {
                        if(cspades.get(i-1) == maxconth-1 && cspades.get(i-2) == maxconth-2 && cspades.get(i-3) == maxconth-3 && cspades.get(i-4) == maxconth-4)
                        {
                            return 9;
                        }
                    }
                }
            }
            if(ndiamonds > 5)
            {
                for(int i = 0;i<cdiamonds.size();i++)
                {
                    if(cdiamonds.get(i) == maxconth && i>4)
                    {
                        if(cdiamonds.get(i-1) == maxconth-1 && cdiamonds.get(i-2) == maxconth-2 && cdiamonds.get(i-3) == maxconth-3 && cdiamonds.get(i-4) == maxconth-4)
                        {
                            return 9;
                        }
                    }
                }
            }
        }
        if(mkind == 4)
        {
            return 8;
        }
        if(mkind == 3 && nkind == 2)
        {
            return 7;
        }
        if(nclubs == 5 || ndiamonds == 5 || nspades == 5 || nhearts == 5)
        {
            return 6;
        }
        if (maxcont == 4)
        {
            return 5;
        }
        if (mkind == 3)
        {
            return 4;
        }
        if (mkind == 2 && nkind == 2)
        {
            return 3;
        }
        if (mkind == 2)
        {
            return 2;
        }
        return 1;  
    }
    public String priority(int a)
    {
        int p = point(a);
        if(p == 10)
        {
            return "Royal Flush";
        }
        else if(p == 9)
        {
            return "Straight Flush";
        }
        else if(p == 8)
        {
            return "4 of a Kind";
        }
        else if(p == 7)
        {
            return "Full House";
        }
        else if(p == 6)
        {
            return "Flush";
        }
        else if(p == 5)
        {
            return "Straight";
        }
        else if(p == 4)
        {
            return "3 of a Kind";
        }
        else if(p == 3)
        {
            return "Two Pair";
        }
        else if(p == 2)
        {
            return "Pair";
        }
        else
        {
            return "High Card";
        }
    }
    public static void main(String[] args) {
        int max = 0;
        int slot = 0;
        for(int i=0;i<20;i++)
        {
            max = 0;
            Poker new_game = new Poker(5);
            for(int j = 0;j<5;j++)
            {
                if(new_game.point(j)>max)
                {
                    slot = j;
                    max = new_game.point(j);
                }
                System.out.println(max);
                System.out.println(new_game.Players.get(slot));
                System.out.println(new_game.Hand);
            }
        }
    }
}
