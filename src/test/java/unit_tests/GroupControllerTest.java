package unit_tests;

import com.example.purchases.controllers.GroupController;
import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @Mock
    GroupService service;

    @InjectMocks
    GroupController controller;

    @Test
    void addGroup_DataIsValid_ReturnsValidResponseEntity() {
        // given
        String name = "Group 1";
        Group group = new Group();
        group.setName(name);
        String username = "user";
        String password = "pass1234";
        String email = "user@example.com";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);


        // when
        ResponseEntity<Response> responseEntity = this.controller.addGroup(group, new AuthUserDetails(user));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.service).saveGroup(group, username);
    }
}
