package com.example.InternAssign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheapestRoute() throws Exception {
        mockMvc.perform(post("/transfer/cheapest-route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"availableTransfers\":[], \"maxWeight\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.selectedTransfers").exists())
                .andExpect(jsonPath("$.totalWeight").exists())
                .andExpect(jsonPath("$.totalCost").exists());
    }

    @Test
    public void testCheapestRouteNoBody() throws Exception {
        mockMvc.perform(post("/transfer/cheapest-route")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheapestRouteEmptyBody() throws Exception {
        mockMvc.perform(post("/transfer/cheapest-route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheapestRouteInvalidMaxWeight() throws Exception {
        mockMvc.perform(post("/transfer/cheapest-route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"availableTransfers\":[], \"maxWeight\":-1}"))
                .andExpect(status().isBadRequest());
    }

}
