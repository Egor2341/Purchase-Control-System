package unit_tests;

import com.example.purchases.controllers.UnauthController;
import com.example.purchases.entities.User;
import com.example.purchases.responses.Response;
import com.example.purchases.servicies.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class UnauthControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UnauthController controller;


    @Test
    void registration_DataIsValid_ReturnsValidResponseEntity() {
        // given
        String username = "User";
        String password = "password";
        String email = "user@example.com";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

//         when
        ResponseEntity<Response> responseEntity = this.controller.registration(user);

//         then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(this.userService).saveUser(user);
    }
}
