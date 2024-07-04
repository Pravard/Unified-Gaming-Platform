package com.example.webs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.webs.User_Related.User;

@SpringBootApplication
public class WebsApplication {
	public static User mainUser;
	public static void main(String[] args) {
		SpringApplication.run(WebsApplication.class, args);
		
	}
	
}
