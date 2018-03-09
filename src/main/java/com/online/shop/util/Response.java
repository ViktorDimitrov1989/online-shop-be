package com.online.shop.util;

public class Response {

    private String message;
    private Object response;

    public Response() {
    }

    public Response(String message, Object response) {
        this.message = message;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
