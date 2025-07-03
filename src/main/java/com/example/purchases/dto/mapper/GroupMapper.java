package com.example.purchases.dto.mapper;

import com.example.purchases.dto.GroupDTO;
import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GroupMapper {

    public GroupDTO toDTO(Group group) {
        return new GroupDTO(group.getId(), group.getName(),
                group.getUsers().stream()
                        .map(User::getUsername)
                        .collect(Collectors.toSet()));
    }
}
