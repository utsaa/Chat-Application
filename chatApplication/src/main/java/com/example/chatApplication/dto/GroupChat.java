package com.example.chatApplication.dto;

import org.apache.catalina.LifecycleState;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groupChat")
public class GroupChat implements Serializable {
    public String getId() {
        return Id;
    }

    public GroupChat(){
        groupMessages=new GroupMessages();
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "Id='" + Id + '\'' +
                ", users=" + users +
                ", groupMessages=" + groupMessages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupChat groupChat)) return false;
        return Objects.equals(getId(), groupChat.getId()) && Objects.equals(getUsers(), groupChat.getUsers()) && Objects.equals(getGroupMessages(), groupChat.getGroupMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsers(), getGroupMessages());
    }

    public void setId(String id) {
        Id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = new HashSet<>(users);
    }

    public GroupMessages getGroupMessages() {
        return groupMessages;
    }

    public void setGroupMessages(GroupMessages groupMessages) {
        this.groupMessages = groupMessages;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String Id;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    private Set<User> users;

    @Embedded
    private GroupMessages groupMessages;

}
