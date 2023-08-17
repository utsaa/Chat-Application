package com.example.chatApplication.model;

import java.util.Objects;

public class ChatMessage {
    private String content;
    private String sender;
    private String toSender;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", toSender='" + toSender + '\'' +
                ", messageType=" + messageType +
                '}';
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage that)) return false;
        return Objects.equals(getContent(), that.getContent()) && Objects.equals(getSender(), that.getSender()) && Objects.equals(getToSender(), that.getToSender()) && getMessageType() == that.getMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getSender(), getToSender(), getMessageType());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getToSender() {
        return toSender;
    }

    public void setToSender(String toSender) {
        this.toSender = toSender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    private MessageType messageType;
}
