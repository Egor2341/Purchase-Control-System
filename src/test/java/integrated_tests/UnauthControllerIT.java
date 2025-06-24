package integrated_tests;

import com.example.purchases.PurchasesApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/unauth_controller/test_data.sql")
@Transactional
@SpringBootTest(classes = PurchasesApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class UnauthControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void registration_DataIsValid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/unauth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "User",
                            "password": "pass1234",
                            "email": "user@example.com"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated()
                );
    }
}
