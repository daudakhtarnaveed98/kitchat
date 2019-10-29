package com.creativosoft.kitchat.registry.controllers;

import com.creativosoft.kitchat.registry.models.User;
import com.creativosoft.kitchat.registry.repositories.UserRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class RegistrationAndLoginController {
    // Attributes.
    private final UserRepository userRepository;

    // Constructors.
    @Contract(pure = true)
    @Autowired
    public RegistrationAndLoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Rest endpoint to register a user.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUser(@NotNull @RequestBody User user) {
        if (userRepository.findById(user.getUserEmailAddress()).isPresent()) {
            return new ResponseEntity<>("User with this email address already exists.", HttpStatus.CONFLICT);
        }
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
    }

    // Rest endpoint to login a user.
    @RequestMapping(value = "/login/{userEmailAddress}", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@NotNull @RequestBody User user, @PathVariable String userEmailAddress) {
        String userEmailToValidate = user.getUserEmailAddress();
        String userPasswordHashString = user.getUserPassword();
        Optional<User> userRecordFromDatabaseOptional = userRepository.findById(userEmailAddress);

        if (userRecordFromDatabaseOptional.isPresent()) {
            User userRecordFromDatabase = userRecordFromDatabaseOptional.get();

            if (userRecordFromDatabase.getUserEmailAddress().equals(userEmailToValidate)
                    && userRecordFromDatabase.getUserPassword().equals(userPasswordHashString)) {
                return new ResponseEntity<>("Logged in successfully.", HttpStatus.OK);
            }

            else {
                return new ResponseEntity<>("Incorrect email address or password.", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        }

        else {
            return new ResponseEntity<>("User not found in database.", HttpStatus.NOT_FOUND);
        }
    }

    // Rest endpoint to update token of a user.
    @RequestMapping(value = "/update-token/{userEmailAddress}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateToken(@RequestBody String token, @PathVariable String userEmailAddress) {
        Optional<User> userById = userRepository.findById(userEmailAddress);

        if (userById.isPresent()) {
            User user = userById.get();
            user.setDeviceToken(new JSONObject(token).getString("deviceToken"));
            userRepository.save(user);
            return new ResponseEntity<>("User's token updated successfully.", HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>("User not found in database.", HttpStatus.NOT_FOUND);
        }
    }

    // Rest endpoint to get all users.
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ArrayList<String> listUsers() {
        return userRepository.getAllUsers();
    }
}