package dev.osmocode.codehub.dto;

import jakarta.validation.constraints.Size;

public class QuestionAnswerDto {

    @Size(max = 30_000)
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
