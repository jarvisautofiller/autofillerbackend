package ipp.ipp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {
    // Setters
    // Getters
    private int status;
    private String message;

    public Message(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
