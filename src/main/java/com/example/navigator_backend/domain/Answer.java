package com.example.navigator_backend.domain;

public record Answer(
        String token,
        // tracks vote, what can be "yes" or "no"
        String Answer) {
}