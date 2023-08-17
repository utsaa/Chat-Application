package com.example.chatApplication.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Message(){

    }
    public Message(User user, String content){
        this.user = user;
        this.content = content;

        this.localDateTime = LocalDateTime.now();
    }
    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
 private  User user;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getUser(), message.getUser()) && Objects.equals(getContent(), message.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getContent());
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }


    private String content;

    private LocalDateTime localDateTime;


}
