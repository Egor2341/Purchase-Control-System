package com.example.purchases.controllers;

import com.example.purchases.entities.User;
import com.example.purchases.exceptions.UserAlreadyExistException;
import com.example.purchases.responses.Response;
import com.example.purchases.servicies.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/unauth/")
public class UnauthController {

    private final UserService userService;

    @Autowired
    public UnauthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody @Valid User user) {
        try {
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } catch(UserAlreadyExistException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login ")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
