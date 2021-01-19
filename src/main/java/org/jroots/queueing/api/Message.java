package org.jroots.queueing.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Message {
    private UUID uuid;
    private String content;

    @JsonProperty
    public UUID getUUID() {
        return uuid;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public void setUUID() {
        this.uuid = uuid;
    }

    @JsonProperty
    public void setContent() {
        this.content = content;
    }
}
