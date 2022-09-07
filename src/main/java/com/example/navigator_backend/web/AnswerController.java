package com.example.navigator_backend.web;


import com.example.navigator_backend.domain.Answer;
import com.example.navigator_backend.domain.Navigator;
import com.example.navigator_backend.repository.AnswerRepository;
import com.example.navigator_backend.repository.NavigatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/answers")
@CrossOrigin
public class AnswerController {
    private Logger log = LoggerFactory.getLogger(NavigatorController.class);
    private NavigatorRepository navigatorRepository;
    private AnswerRepository answerRepository;

    public AnswerController(NavigatorRepository navigatorRepository) {
        this.navigatorRepository = navigatorRepository;
    }
    @GetMapping()
    public ResponseEntity<List<Answer>> findAnswers(String filter) {
        List<Answer> answers = new ArrayList<>();
        answers = answerRepository.getAllAnswers();
        return ResponseEntity.ok().body(answers);

    }

    @CrossOrigin
    @PostMapping()   // HTTP Support
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        try {
            Answer newanswer = new Answer("", answer.antwort());
            answer = answerRepository.saveAnswer(answer);
            Answer answerDto = new Answer(answer.id(), answer.antwort());
            return ResponseEntity.ok().body(answerDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}