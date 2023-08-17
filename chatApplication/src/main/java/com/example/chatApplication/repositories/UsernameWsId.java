package com.example.chatApplication.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UsernameWsId {
    public Map<String, String> getRepo() {
        return repo;
    }

    public Set<String> getOfflineUsers() {
        return offlineUsers;
    }

    private final Map<String, String> repo;

    private final Set<String> offlineUsers;
    public UsernameWsId(){

        repo=new HashMap<>();
        offlineUsers=new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsernameWsId that)) return false;
        return Objects.equals(getRepo(), that.getRepo()) && Objects.equals(getOfflineUsers(), that.getOfflineUsers());
    }

    @Override
    public String toString() {
        return "UsernameWsId{" +
                "repo=" + repo +
                ", offlineUsers=" + offlineUsers +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRepo(), getOfflineUsers());
    }

}
