package integrated_tests;

import com.example.purchases.PurchasesApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@Sql("/sql/group_controller/test_data.sql")
@Transactional
@SpringBootTest(classes = PurchasesApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class GroupControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void addGroup_DataIsValid_ReturnsValidResponseEntity() throws Exception{
        // given
        var requestBuilder = post("/groups/add")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Group1"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpect(status().isCreated());

    }
}
