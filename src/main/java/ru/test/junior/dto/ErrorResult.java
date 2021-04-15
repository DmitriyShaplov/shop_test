package ru.test.junior.dto;

import lombok.Getter;

@Getter
public class ErrorResult {
    private final String type = "error";
    private final String message;

    public ErrorResult(String message) {
        this.message = message;
    }
}
