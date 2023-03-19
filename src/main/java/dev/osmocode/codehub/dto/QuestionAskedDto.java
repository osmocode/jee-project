package dev.osmocode.codehub.dto;

import jakarta.validation.constraints.Size;

public class QuestionAskedDto {

    @Size(max = 150)
    private String title;

    @Size(max = 30_000)
    private String body;
    
    public QuestionAskedDto() {
    }

    public QuestionAskedDto(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
