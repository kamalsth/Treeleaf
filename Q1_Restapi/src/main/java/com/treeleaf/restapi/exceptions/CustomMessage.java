package com.treeleaf.restapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


public class CustomMessage {
    public String message;

    public CustomMessage(String message) {
        this.message = message;
    }

}
