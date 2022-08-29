package com.example.navigator_backend.web;


import com.example.navigator_backend.domain.Answer;
import com.example.navigator_backend.domain.Navigator;
import com.example.navigator_backend.repository.NavigatorRepository;
import com.example.navigator_backend.web.dto.AnswerDTO;
import com.example.navigator_backend.web.dto.NavigatorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/navigators")
@CrossOrigin
public class NavigatorController {
    private Logger log = LoggerFactory.getLogger(NavigatorController.class);
    private NavigatorRepository navigatorRepository;
    
    public NavigatorController(NavigatorRepository navigatorRepository) {
        this.navigatorRepository = navigatorRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<NavigatorDTO> createNavigator(@RequestBody NavigatorDTO dto) {
        try {
            Navigator navigator = new Navigator("", dto.questionText(), new ArrayList<>());
            navigator = navigatorRepository.saveNavigator(navigator);
            NavigatorDTO navigatorDto = new NavigatorDTO(navigator.id(), dto.questionText());
            return ResponseEntity.ok().body(navigatorDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(params = "token")
    public ResponseEntity<List<NavigatorDTO>> findNavigators(@RequestParam String token,
                                                             @RequestParam(required = false) String filter) {
        List<NavigatorDTO> dtos = new ArrayList<>();
        try {
            // check if token is an UUID
            UUID.fromString(token);
            if (filter.equals("last")) {
                Navigator navigator = navigatorRepository.getActNavigator();
                if (navigator != null) {
                    NavigatorDTO navigatorDto = new NavigatorDTO(navigator.id(), navigator.question());
                    dtos.add(navigatorDto);
                    return ResponseEntity.ok().body(dtos);
                }
                return ResponseEntity.notFound().build();
            } else {
                List<Navigator> polls = navigatorRepository.getAllNavigators();
                for (Navigator poll : polls) {
                    NavigatorDTO navigatorDto = new NavigatorDTO(poll.id(), poll.question());
                    dtos.add(navigatorDto);
                }
                return ResponseEntity.ok().body(dtos);
            }
        } catch (Exception e) {
            log.error("Token given is wrong");
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin
    @PostMapping("/{id}/answers")   // HTTP Support
    public ResponseEntity<Void> answer(@PathVariable String id, @RequestBody AnswerDTO dto) {
        Navigator actPoll = navigatorRepository.getActNavigator();
        if ((actPoll == null) || (!actPoll.id().equals(id))) {
            log.info("Poll given is wrong: id='{}'", dto.token());
            return ResponseEntity.badRequest().build();
        }
        List<Answer> answers = actPoll.answers();
        for (Answer answer : answers) {
            if (answer.token().equals(dto.token())) {
                log.info("Answer already given: '{}'", dto.token());
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }
        }
        Answer answer = new Answer(dto.token(), dto.answer());
        actPoll.answers().add(answer);
        log.debug("Answer set for {}'", dto.token());
        return ResponseEntity.ok().build();
    }


}