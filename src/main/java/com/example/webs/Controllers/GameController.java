package com.example.webs.Controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.webs.WebsApplication;
import com.example.webs.Card_Related.BlackJack;
import com.example.webs.Card_Related.BlackJackUpdater;
import com.example.webs.Card_Related.Poker;
import com.example.webs.Card_Related.PokerBot;
import com.example.webs.Card_Related.PokerUpdater;
import com.example.webs.Other_Games.SlotMachine;
import com.example.webs.Other_Games.SlotUpdater;
import com.example.webs.Services.HighscoreService;
import com.example.webs.Services.UserService;
import com.example.webs.Services.WeatherService;
import com.example.webs.User_Related.Highscore;
import com.example.webs.Weather_Related.WeatherData;
import com.example.webs.Weather_Related.WeatherPrediction;

@RestController
@RequestMapping
public class GameController {
    public Poker poker;
    public Random rand = new Random();
    public ArrayList<PokerBot> poker_bots = new ArrayList<>();
    public ArrayList<String> decisions = new ArrayList<>();
    public BlackJack blackjack = new BlackJack();
    public int folds = 0;
    public int blackjackamt = -1;
    public String blackjackact;
    public String slotresult = "";
    public String slot1 = "🀄";
    public String slot2 = "🔔";
    public String slot3 = "🍒";
    int userfold = 0;
    int prev_raise = -1;
    public ArrayList<Integer> payed = new ArrayList<>();
    private final UserService userService;
    private final HighscoreService highscoreService;
    private final WeatherService weatherService;
    @Autowired
    public GameController(UserService userservice,HighscoreService highscoreService,WeatherService weatherService)
    {
        this.userService = userservice;
        this.highscoreService = highscoreService;
        this.weatherService = weatherService;
    }
    

    @GetMapping("/poker")
    public ModelAndView MainScreen()
    {
        poker_bots.clear();
        folds = 0;
        decisions.clear();
        userfold = 0;
        prev_raise = -1;
        payed.clear();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("poker");
        return modelAndView;
    }

    @PostMapping("/poker/game")
    public void gameScreen(@RequestBody Poker a,Model model)
    {
        poker = a;
        poker.turn = 0;
        poker.generateCards();
        poker.Pot=0;
        for(int i=0;i<poker.Num_Players;i++)
        {
            int num = rand.nextInt(6);
            if(num<2)
            {
                poker_bots.add(new PokerBot(poker, i, "low"));
            }
            else if(num==5)
            {
                poker_bots.add(new PokerBot(poker, i, "high"));
            }
            else
            {
                poker_bots.add(new PokerBot(poker, i, "medium"));
            }
            payed.add(poker.pay_per_person);
        }
    }

    @GetMapping("/poker/game")
    public ModelAndView gameScreen2(Model model)
    {
        if (userfold == 1)
        {
            
        WebsApplication.mainUser.virtual_points-= payed.get(0);
            ModelAndView lossmodelAndView = new ModelAndView();
            lossmodelAndView.setViewName("lose_screen");
            model.addAttribute("amount", poker.pay_per_person);
            model.addAttribute("poker", poker);
            userService.saveUser(WebsApplication.mainUser);
            return lossmodelAndView;
        }
        if(folds == poker.Num_Players-1)
        {
            
        WebsApplication.mainUser.virtual_points-= poker.pay_per_person;
            ModelAndView winmodelAndView = new ModelAndView();
            winmodelAndView.setViewName("win_screen");
            model.addAttribute("amount", poker.Pot);
            model.addAttribute("poker", poker);
            WebsApplication.mainUser.virtual_points += poker.Pot;
            highscoreService.saveHS(new Highscore(WebsApplication.mainUser.username,"Poker",poker.Pot - payed.get(0)));
            userService.saveUser(WebsApplication.mainUser);
            return winmodelAndView;
        }
        if(poker.turn >= 5)
        {
            
        WebsApplication.mainUser.virtual_points-= poker.pay_per_person;
            int max = 0;
            ArrayList<Integer> winners = new ArrayList<>();
            for(int i=0;i<poker.Num_Players;i++)
            {
                if(poker.point(i)>max && poker.folded.get(i)==0)
                {
                    winners.clear();
                    winners.add(i);
                    max = poker.point(i);
                }
                else if(poker.point(i)==max  && poker.folded.get(i)==0)
                {
                    winners.add(i);
                }
            }
            if(winners.get(0)==0)
            {
                ModelAndView win2modelAndView = new ModelAndView();
                win2modelAndView.setViewName("win_screen");
                model.addAttribute("winners", winners);
                model.addAttribute("amount", poker.Pot/winners.size());
                model.addAttribute("poker", poker);
                WebsApplication.mainUser.virtual_points += poker.Pot/winners.size();
                highscoreService.saveHS(new Highscore(WebsApplication.mainUser.username,"Poker",poker.Pot/winners.size() - payed.get(0)));
                userService.saveUser(WebsApplication.mainUser);
                return win2modelAndView;

            }
            else{
                ModelAndView losemodelAndView = new ModelAndView();
            losemodelAndView.setViewName("lose_screen");
            model.addAttribute("amount", poker.pay_per_person);
            model.addAttribute("poker", poker);
            userService.saveUser(WebsApplication.mainUser);
            return losemodelAndView;
            }
        }
        else
        {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("poker_game");
        model.addAttribute("Table0", poker.mask().get(0));
        model.addAttribute("Table1", poker.mask().get(1));
        model.addAttribute("Table2", poker.mask().get(2));
        model.addAttribute("Table3", poker.mask().get(3));
        model.addAttribute("Table4", poker.mask().get(4));
        model.addAttribute("MyCard0", poker.Players.get(0).get(0).getImgPath());
        model.addAttribute("MyCard1", poker.Players.get(0).get(1).getImgPath());
        model.addAttribute("pay_per_person", poker.pay_per_person);
        model.addAttribute("pot", poker.Pot);
        model.addAttribute("oth_decisions", decisions.toString());
        model.addAttribute("mymoney", WebsApplication.mainUser.virtual_points);
        model.addAttribute("all_paid", payed.toString());
        if(poker.turn>=4)
        {model.addAttribute("Points", poker.priority(0));}
        return modelAndView;
        }
    }

    @GetMapping("/highscore")
    public ModelAndView highscore(Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("highscore");
        model.addAttribute("highscores",highscoreService.fetchtopten());
        return modelAndView;
    }

    @PostMapping("/poker/updater")
    public void updater(@RequestBody PokerUpdater updater)
    {
        decisions.clear();
        if(updater.action.equals("fold"))
        {
            userfold = 1;
            
        WebsApplication.mainUser.virtual_points-= poker.pay_per_person;
        }
        else
        {
        if(updater.action.equals("raise"))
        {
            poker.pay_per_person = poker.pay_per_person+updater.amount;
            payed.set(0,poker.pay_per_person);
        }
        else if(updater.action.equals("check"))
        {
            payed.set(0,poker.pay_per_person);
        }
        int raise=0;
        for(int i=1;i<poker.Num_Players;i++)
        {
            
            if(poker.folded.get(i)==0)
            {
                String dec = poker_bots.get(i).makeDecision();
                if(dec.equals("raise") && prev_raise!=i)
                {
                    prev_raise = i;
                    poker.pay_per_person += rand.nextInt(100);
                    raise = 1;
                    payed.set(i,poker.pay_per_person);
                    decisions.add(dec);
                }
                else if(dec.equals("fold"))
                {
                    poker.folded.set(i, 1);
                    folds += 1;
                    decisions.add(dec);
                }
                else
                {
                    payed.set(i,poker.pay_per_person);
                    decisions.add("check");
                }
            }
        }
        poker.Pot = 0;
        for(int i=0;i<poker.Num_Players;i++)
        {
            poker.Pot+=payed.get(i);
        }
        if(raise == 0)
        {
            poker.turn+=1;
        }
    }
    }

    @GetMapping("/blackjack")
    public ModelAndView blackjack(Model model)
    {
        if(blackjackamt<0)
        {
        blackjack = new BlackJack();
        blackjack.dealCards();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("blackjack");
        model.addAttribute("blackjack", blackjack);
        return modelAndView;
        }
        else if(blackjackact.equals("hit") && blackjack.playerPoints()<=21)
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("blackjack2");
            model.addAttribute("blackjack", blackjack);
            return modelAndView;
        }
        else
        {
            
            
            if(blackjack.playerPoints()>21)
            {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("blackjackout");
                if(blackjack.dealerPoints()>21)
                {
                    blackjack.dealerHand.removeLast();
                }
                model.addAttribute("blackjack", blackjack);
                model.addAttribute("msg", "You lose! Player Busts");
                model.addAttribute("act", "lose");
                model.addAttribute("amt", blackjackamt);
                WebsApplication.mainUser.virtual_points-=blackjackamt;
                userService.saveUser(WebsApplication.mainUser);
                blackjackamt = -1;
                return modelAndView;
            }
            
            else if(blackjack.dealerPoints()>21)
            {
                while(blackjack.dealerPoints()<blackjack.playerPoints())
            {
                blackjack.dealerHand.add(blackjack.deck.Draw());
            }
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("blackjackout");
                model.addAttribute("blackjack", blackjack);
                model.addAttribute("msg", "You win! Dealer Busts");
                model.addAttribute("act", "gain");
                model.addAttribute("amt", blackjackamt);
                WebsApplication.mainUser.virtual_points+=blackjackamt;
                highscoreService.saveHS(new Highscore(WebsApplication.mainUser.username,"BlackJack",blackjackamt));
                userService.saveUser(WebsApplication.mainUser);
                blackjackamt = -1;
                return modelAndView;
            }
            else if(blackjack.dealerPoints()>blackjack.playerPoints())
            {
                while(blackjack.dealerPoints()<blackjack.playerPoints())
            {
                blackjack.dealerHand.add(blackjack.deck.Draw());
            }
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("blackjackout");
                model.addAttribute("blackjack", blackjack);
                model.addAttribute("msg", "You lose! Dealer scores more");
                model.addAttribute("act", "lose");
                model.addAttribute("amt", blackjackamt);
                WebsApplication.mainUser.virtual_points-=blackjackamt;
                userService.saveUser(WebsApplication.mainUser);
                blackjackamt = -1;
                return modelAndView;
            }
            else
            {
                while(blackjack.dealerPoints()<blackjack.playerPoints())
            {
                blackjack.dealerHand.add(blackjack.deck.Draw());
            }
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("blackjackout");
                model.addAttribute("blackjack", blackjack);
                model.addAttribute("msg", "You win! Player scores more");
                model.addAttribute("act", "gain");
                model.addAttribute("amt", blackjackamt);
                WebsApplication.mainUser.virtual_points+=blackjackamt;
                highscoreService.saveHS(new Highscore(WebsApplication.mainUser.username,"BlackJack",blackjackamt));
                userService.saveUser(WebsApplication.mainUser);
                blackjackamt = -1;
                return modelAndView;
            }
        }
    }

    @PostMapping("/blackjack/update")
    public void blackjackupdater(@RequestBody BlackJackUpdater updater)
    {
        System.out.println(updater.amount);
        if(blackjackamt<0)
        {
        blackjackamt = updater.amount;}
        if(updater.action.equals("hit"))
        {
            blackjack.playerHand.add(blackjack.deck.Draw());
            blackjackact = "hit";
        }
        else
        {
            blackjackact = "stand";
        }
    }

    @GetMapping("/slotmachine")
    public ModelAndView slotmachine(Model model)
    {
        SlotMachine slotMachine = new SlotMachine(new ArrayList<>(Arrays.asList(slot1,slot2,slot3)));
        slotMachine.getValue(0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("slotmachine");
        model.addAttribute("result", slotresult);
        model.addAttribute("mymoney", WebsApplication.mainUser.virtual_points);
        model.addAttribute("slot1", slot1);
        model.addAttribute("slot2", slot2);
        model.addAttribute("slot3", slot3);
        return modelAndView;

    }
    @PostMapping("/slotmachine/update")
    public void slotupdate(@RequestBody SlotUpdater slotUpdater)
    {
        WebsApplication.mainUser.virtual_points += slotUpdater.amount;
        userService.saveUser(WebsApplication.mainUser);
        if(slotUpdater.amount<0)
        {
            slotresult = "You lose " +String.valueOf(-1*slotUpdater.amount)+" virtual points";
        }
        else
        {
            slotresult = "You win " +String.valueOf(slotUpdater.amount)+" virtual points";
            highscoreService.saveHS(new Highscore(WebsApplication.mainUser.username,"Slot Machine",slotUpdater.amount));

        }
        slot1 = slotUpdater.slot1;
        slot2 = slotUpdater.slot2;
        slot3 = slotUpdater.slot3;
    }

    @GetMapping("/weather")
    public ModelAndView weather(Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("weather2");
        return modelAndView;
    }

    @PostMapping("weather/save")
    public void save_preds(@RequestBody WeatherPrediction weatherPrediction)
    {
        weatherPrediction.username = WebsApplication.mainUser.username;
        Date today = new Date();
        int ONE_DAY = 24*1000*60*60;
        Date tomorrow = new Date(today.getTime()+ONE_DAY);
        weatherPrediction.date = tomorrow;
        weatherService.addPrediction(weatherPrediction);
        WebsApplication.mainUser.virtual_points-=200;
        userService.saveUser(WebsApplication.mainUser);
    }

    @GetMapping("weather/outcomes")
    public ModelAndView winloss(Model model)
    {
        ArrayList<WeatherPrediction> preds = weatherService.getPredictionsByUsername(WebsApplication.mainUser.username);
        Date today = new Date();
        int tod = Integer.parseInt(new SimpleDateFormat("dd").format(today));
        WeatherData weatherData = new WeatherData();
        weatherData.callWeatherAPI("Bangalore");
        int ispred=0;
        WeatherPrediction tod_pred = new WeatherPrediction();
        for(int i=0;i<preds.size();i++)
        {
            if(Integer.parseInt(new SimpleDateFormat("dd").format(preds.get(i).date))==tod)
            {
                ispred=1;
                tod_pred = preds.get(i);
            }
        }
        if(ispred==1)
        {
            int windwin=1;
            int tempwin=1;
            float tempdiff = Math.abs(weatherData.temp-tod_pred.Temperature);
            float winddiff = Math.abs(weatherData.wind-tod_pred.WindSpeed);
            ArrayList<WeatherPrediction> oth_preds = weatherService.getallbytoday();
            for(int i=0;i<oth_preds.size();i++)
            {
                float temptemp = Math.abs(weatherData.temp-oth_preds.get(i).Temperature);
                float tempwind = Math.abs(weatherData.wind-oth_preds.get(i).WindSpeed);
                if(temptemp<tempdiff)
                {
                    tempwin = 0;
                }
                if(tempwind<winddiff)
                {
                    windwin = 0;
                }
            }
            String msg1 = new String();
            String msg2 = new String();
            if(windwin==0)
            {
                msg1 = "Your prediction for wind speed was inaccurate";
            }
            else
            {
                msg1 = "Your prediction for wind speed was the best prediction. You win "+100*oth_preds.size()+" Virtual Points!!";
                WebsApplication.mainUser.virtual_points+=100*oth_preds.size();
                userService.saveUser(WebsApplication.mainUser);
            }
            if(tempwin==0)
            {
                msg2 = "Your prediction for temperature was inaccurate";
            }
            else
            {
                msg2 = "Your prediction for temperature was the best prediction. You win "+100*oth_preds.size()+" Virtual Points!!";
                WebsApplication.mainUser.virtual_points+=100*oth_preds.size();
                userService.saveUser(WebsApplication.mainUser);
            }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("weatherouts");
        model.addAttribute("msg1", msg1);
        model.addAttribute("msg2", msg2);
        model.addAttribute("mine", tod_pred);
        model.addAttribute("act_preds", weatherData);
        model.addAttribute("oth_preds", oth_preds);
        return modelAndView;}
        else{
            ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nopreds");
        return modelAndView;
        }
    }
}
