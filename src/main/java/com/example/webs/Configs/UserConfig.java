package com.example.webs.Configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.webs.Repositories.UserRepository;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandlinerunner(UserRepository userrepository){
        return args ->{
        };
    }
}
