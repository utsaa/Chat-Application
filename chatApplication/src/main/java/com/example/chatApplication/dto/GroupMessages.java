package com.example.chatApplication.dto;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.*;

@Embeddable
public class GroupMessages implements Serializable {

    public GroupMessages(){
        userMessages=new HashSet<>();
    }

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Message.class)
    public Set<Message> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(Set<Message> userMessages) {
        this.userMessages = userMessages;
    }

    @Override
    public String toString() {
        return "GroupMessages{" +
                "userMessages=" + userMessages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMessages that)) return false;
        return Objects.equals(getUserMessages(), that.getUserMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserMessages());
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Message.class)
    private Set<Message> userMessages;

}
