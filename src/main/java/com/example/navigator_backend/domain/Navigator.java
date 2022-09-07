package com.example.navigator_backend.domain;

public record Navigator(
        String id,
        Person person,
        Projekt projekt,
        Tool tool) {
}