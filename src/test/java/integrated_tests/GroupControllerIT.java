package integrated_tests;

import com.example.purchases.PurchasesApplication;
import com.example.purchases.servicies.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        var requestBuilder = post("/groups/")
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
        var requestBuilder = post("/groups/")
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
    void getCreatedGroups_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = get("/groups/created")
                .with(httpBasic("user", "pass1234"));

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(status().isOk(),
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
                            "id_group": "1",
                            "username": "user2"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isOk());
    }

    @Test
    void addUser_UserAlreadyInGroup_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/groups/add_user")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id_group": "1",
                            "username": "user"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_UserIsNotAuthorGroup_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/groups/add_user")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id_group": "3",
                            "username": "user3"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser_DataIsValid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = delete("/groups/delete_user")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id_group": "1",
                            "username": "user4"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isOk());
    }
}
