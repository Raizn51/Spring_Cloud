package com.gourav_raikwar.quizservice.service;

import com.gourav_raikwar.quizservice.dao.QuizDao;
import com.gourav_raikwar.quizservice.feign.QuizInterface;
import com.gourav_raikwar.quizservice.model.QuestionWrapper;
import com.gourav_raikwar.quizservice.model.Quiz;
import com.gourav_raikwar.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
          Quiz quiz = quizDao.findById(id).get();
          List<Integer> questionIds = quiz.getQuestionIds();
          ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
          return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
