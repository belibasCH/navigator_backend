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
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/navigators")
@CrossOrigin
public class NavigatorController {
    private Logger log = LoggerFactory.getLogger(NavigatorController.class);
    private NavigatorRepository navigatorRepository;
    private AnswerRepository answerRepository;
    
    public NavigatorController(NavigatorRepository navigatorRepository) {
        this.navigatorRepository = navigatorRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Navigator> createNavigator(@RequestBody Navigator dto) {
        try {
            Navigator navigator = new Navigator("", dto.person(), dto.projekt(), dto.tool());
            navigator = navigatorRepository.saveNavigator(navigator);
            Navigator navigatorDto = new Navigator(navigator.id(),  dto.person(), dto.projekt(), dto.tool());
            return ResponseEntity.ok().body(navigatorDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Navigator>> findNavigators(@RequestParam (defaultValue = "") String filter) {
        List<Navigator> navigators = new ArrayList<>();
        try {
            if (filter.equals("last")) {
                Navigator navigator = navigatorRepository.getActNavigator();
                navigators.add(navigator);
                return ResponseEntity.ok().body(navigators);
            } else {
                List<Navigator> polls = navigatorRepository.getAllNavigators();
                for (Navigator poll : polls) {
                    Navigator navigator = new Navigator(poll.id(),  poll.person(), poll.projekt(), poll.tool());
                    navigators.add(navigator);
                }
                return ResponseEntity.ok().body(navigators);
            }
        } catch (Exception e) {
            log.error("Token given is wrong");
            return ResponseEntity.internalServerError().build();
        }
    }


}