package com.example.webs.Controllers;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.webs.WebsApplication;
import com.example.webs.Services.UserService;
import com.example.webs.User_Related.User;

@RestController
@RequestMapping
public class UserController {
    private final UserService userservice;
    @Autowired
    public UserController(UserService userservice)
    {
        this.userservice = userservice;
    }

    @GetMapping("/user_signup")
    public ModelAndView getUser()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/user_signup")
    public void registerUser(@RequestBody User user)
    {
        userservice.addUser(user);
        WebsApplication.mainUser = user;
    }

    @GetMapping("/user_login")
    public ModelAndView login_page()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/user_login")
    public void loggedUser(@RequestBody User user)
    {
        Optional<User> logged_user = userservice.getsUserbyUsername(user.username);
        if(logged_user.isPresent())
        {
            if(logged_user.get().password.equals(user.password))
            {
                WebsApplication.mainUser  = logged_user.get();
            }
            else{
                throw new IllegalStateException("Incorrect Password!");
            }
        }
        else{
            throw new IllegalStateException("User does not exist!");
        }
    }

}
