package com.example.purchases.servicies;

import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.repositories.GroupRepository;
import com.example.purchases.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public void saveGroup(Group group, String username) {
        Optional<Group> groupFromDB = groupRepository.findByName(group.getName());

        if (groupFromDB.isPresent()) {
            throw new AlreadyExistException("Group with this name already exists");
        }

        User user = userRepository.findByUsername(username).get(); // if user can login, he is in database
        user.getUserGroups().add(group);

        Set<User> users = new HashSet<>();
        users.add(user);
        group.setUsers(users);

        groupRepository.save(group);
    }
}
