package org.jroots.queueing.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class Message {

    private String id;
    private String content;
    private String identifier;

    public Message() {
        id = UUID.randomUUID().toString();
    }

    @JsonProperty
    public String getUUID() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty
    public void setUUID(String id) {
        this.id = id;
    }

    @JsonProperty
    public void setContent(String content) {
        this.content = content;
    }

    public String serializeToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String string = "{}";
        try {
            string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
