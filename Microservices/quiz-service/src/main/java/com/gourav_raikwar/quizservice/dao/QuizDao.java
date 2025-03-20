package com.gourav_raikwar.quizservice.dao;


import com.gourav_raikwar.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
