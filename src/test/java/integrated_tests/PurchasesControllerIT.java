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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@Sql("/sql/purchase_controller/test_data.sql")
@Transactional
@SpringBootTest(classes = PurchasesApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class PurchasesControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void addPurchase_DataIsValid_ReturnsValidResponseEntity() throws Exception {
        //given
        var requestBuilder = post("/purchases/")
                .with(httpBasic("user", "pass1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "date": "2025-07-10T18:21:00",
                                "total": 1560.22
                            }
                        """);
        //when
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }
}
