package com.example.navigator_backend.domain;

import java.util.List;

public record Navigator(
        String id,
        String question,
        List<Answer> answers) {
}