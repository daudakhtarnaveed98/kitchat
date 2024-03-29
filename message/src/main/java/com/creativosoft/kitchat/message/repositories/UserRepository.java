package com.creativosoft.kitchat.message.repositories;

import com.creativosoft.kitchat.message.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT userEmailAddress FROM User user ")
    ArrayList<String> getAllUsers();
}