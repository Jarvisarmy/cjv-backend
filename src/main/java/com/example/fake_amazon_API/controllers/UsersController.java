package com.example.fake_amazon_API.controllers;

import com.example.fake_amazon_API.CustomizedResponse;
import com.example.fake_amazon_API.models.UserModel;
import com.example.fake_amazon_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin(origins="*")
@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        var response = new CustomizedResponse("A list of all the users",userService.getUsers());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getAUser(@PathVariable("id") String id) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("The user with id: " + id,
                    Collections.singletonList(userService.getAUser(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(value="/users",consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity CreateUsers(@RequestBody UserModel user) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("User created successfully", Collections.singletonList(userService.addUser(user)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(),null);
            return new ResponseEntity(customizedResponse,HttpStatus.BAD_REQUEST);
        }
    }

}
