package integrated_tests;

import com.example.purchases.PurchasesApplication;
import com.example.purchases.entities.Group;
import com.example.purchases.entities.User;
import com.example.purchases.servicies.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    GroupService groupService;

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

    @Test
    void addGroup_DataIsInvalid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/groups/add")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": null
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllGroups_ReturnsValidResponseEntity() throws Exception {
        // given

        var requestBuilder = get("/groups/")
                .with(httpBasic("user", "pass1234"));

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": 1,
                                        "name": "group1",
                                        "users": ["user"]
                                    },
                                    {
                                        "id": 2,
                                        "name": "group2",
                                        "users": ["user"]
                                    }
                                ]
                                """)
                );
    }

    @Test
    void addUser_DataIsValid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/groups/add_user")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user2",
                            "group_name": "group"
                        }
                        """);
    }
}
