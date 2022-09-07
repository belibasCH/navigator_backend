package com.example.navigator_backend.repository;

import com.example.navigator_backend.domain.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AnswerRepository {
    private Logger log = LoggerFactory.getLogger(NavigatorRepository.class);

    private List<Answer> answers = new ArrayList<Answer>();
    public Answer saveAnswer(Answer p) {
        Answer poll = new Answer(Integer.toString(answers.size()), p.antwort());
        answers.add(poll);
        log.debug("Successfully added poll[{}] to repository", poll.id());
        return poll;
    }
    public List<Answer> getAllAnswers(){return answers;}
}
