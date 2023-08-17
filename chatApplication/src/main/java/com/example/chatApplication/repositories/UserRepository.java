package com.example.chatApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.chatApplication.dto.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String > {

    @Query("select u from User u where u.name=:name")
    List<User> findUserByName(@Param("name") String name);


}
