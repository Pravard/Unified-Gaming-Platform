package com.example.webs.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webs.User_Related.*;

@Repository
public interface UserRepository extends JpaRepository<User, String>{


    
}
