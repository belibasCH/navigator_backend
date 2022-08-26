package com.example.navigator_backend.web;

import com.example.navigator_backend.repository.NavigatorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    NavigatorRepository navigatorRepository;

    // using constructor injection instead of @Autowired
    public AdminController(NavigatorRepository navigatorRepository) {
        this.navigatorRepository = navigatorRepository;
    }

    @PutMapping(value = "/navigators", params = "method")
    public ResponseEntity<Void> admin(@RequestParam String method) throws IOException {
        if ((method != null) && (method.equals("reset"))) {
            navigatorRepository.reset();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
