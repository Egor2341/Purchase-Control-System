package com.example.purchases.controllers;

import com.example.purchases.entities.Group;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.responses.Response;
import com.example.purchases.security.AuthUserDetails;
import com.example.purchases.servicies.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups/")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addGroup(@RequestBody @Valid Group group,
                                             @AuthenticationPrincipal AuthUserDetails userDetails) {
        try {
            service.saveGroup(group, userDetails.getUsername());
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
