package com.example.webs.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webs.Repositories.UserRepository;
import com.example.webs.User_Related.User;

@Service
public class UserService { //Facade Pattern

    private final UserRepository userrepository;

    @Autowired
    public UserService(UserRepository userrepository)
    {
        this.userrepository = userrepository;
    }
    public List<User> getUser()
    {
        return userrepository.findAll();
    }

    public Optional<User> getsUserbyUsername(String username)
    {
        Optional<User> logged_user_check = userrepository.findById(username);
        return logged_user_check;
    }
    public void addUser(User user)
    {
        Optional<User> new_user_check = userrepository.findById(user.username);
        if (new_user_check.isPresent())
        {
            throw new IllegalStateException("username in use");
        }
        else{
            userrepository.save(user);
        }
    }
    public void saveUser(User user)
    {
        userrepository.save(user);
    }

}
