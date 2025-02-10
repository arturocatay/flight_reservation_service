package com.vortech.flight.reservation.template;

public class ResponseTemplate {

    protected String message;

    public ResponseTemplate() {
    }

    public ResponseTemplate(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
