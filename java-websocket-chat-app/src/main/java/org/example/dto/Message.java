package org.example.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String fromUserName;
    private String toUserName;
    private String message;

    public static Message generate(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<Message>(){});
    }
    public Message() {

    }
    public Message(String fromUserName, String toUserName, String message) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.message = message;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getMessage() {
        return message;
    }


}
