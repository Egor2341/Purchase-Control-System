package com.example.purchases.servicies;

import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
import com.example.purchases.exceptions.AlreadyExistException;
import com.example.purchases.exceptions.DoesNotExistException;
import com.example.purchases.exceptions.ForbiddenException;
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
        user.getCreatedGroups().add(group);

        Set<User> users = new HashSet<>();
        users.add(user);
        group.setUsers(users);
        group.setAuthor(user);

        groupRepository.save(group);
    }

    public Set<Group> findGroupsByUser(String username) {
        return userRepository.findByUsername(username).get().getUserGroups();
    }

    public Set<Group> findCreatedGroups(String username) {
        return userRepository.findByUsername(username).get().getCreatedGroups();
    }

    public void addUser(String authorName, String username, Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new DoesNotExistException("Group with this ID does not exist");
        }
        User author = userRepository.findByUsername(authorName).get();
        if (!author.getCreatedGroups().contains(group.get())) {
            throw new ForbiddenException("User is not author of this group");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new DoesNotExistException("User with this ID does not exist");
        }
        if (user.get().getUserGroups().contains(group.get())) {
            throw new AlreadyExistException("User is already in this group");
        }
        user.get().getUserGroups().add(group.get());
    }
}
