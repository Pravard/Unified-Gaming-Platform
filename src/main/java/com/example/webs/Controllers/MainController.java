package com.example.webs.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.webs.WebsApplication;

@RestController
@RequestMapping
public class MainController {
    @GetMapping(path = "/")
    public ModelAndView MainScreen()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }
    @GetMapping("/home")
    public ModelAndView homescreen(Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        model.addAttribute("Username", WebsApplication.mainUser.getusername());
        model.addAttribute("mymoney", WebsApplication.mainUser.virtual_points);
        return modelAndView;
    }

    
}
