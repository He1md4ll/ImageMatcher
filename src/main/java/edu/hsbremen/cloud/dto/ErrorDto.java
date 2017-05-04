package edu.hsbremen.cloud.dto;

public class ErrorDto {
    private String key;
    private String message;

    public ErrorDto(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}