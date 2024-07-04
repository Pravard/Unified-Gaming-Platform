package com.example.webs.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webs.User_Related.Highscore;

@Repository
public interface HighscoreRepository extends JpaRepository<Highscore,String> {


}
