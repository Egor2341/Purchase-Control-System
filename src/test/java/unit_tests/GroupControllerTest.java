package unit_tests;

import com.example.purchases.controllers.GroupController;
import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
import com.example.purchases.repositories.GroupRepository;
import com.example.purchases.responses.Response;
import com.example.purchases.security.AuthUserDetails;
import com.example.purchases.servicies.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @Mock
    GroupService groupService;

    @InjectMocks
    GroupController controller;

    @Test
    void addGroup_DataIsValid_ReturnsValidResponseEntity() {
        // given
        Group group = new Group();
        group.setName("Group 1");

        String username = "user";
        User user = new User();
        user.setUsername(username);
        user.setPassword("pass1234");
        user.setEmail("user@example.com");


        // when
        ResponseEntity<Response> responseEntity = this.controller.addGroup(group, new AuthUserDetails(user));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.groupService).saveGroup(group, username);
    }

    @Test
    void getAllGroups_ReturnsValidResponseEntity() {
        // given

        Group group1 = new Group(1L, "Group1", new HashSet<>());
        Group group2 = new Group(2L, "Group2", new HashSet<>());

        User userObj = new User(1L, "user", "password", "user@example.com", Set.of(group1, group2),
                new HashSet<>(), new HashSet<>());

        group1.getUsers().add(userObj);
        group2.getUsers().add(userObj);

        var user = new AuthUserDetails(userObj);
        var groups = Set.of(group1, group2);

        doReturn(groups).when(this.groupService).findGroupsByUser(user.getUsername());

        // when
        var responseEntity = this.controller.getAllGroups(user);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
