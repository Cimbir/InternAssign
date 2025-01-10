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
class AppTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testCallSample() throws Exception {
		mockMvc.perform(post("/transfer/cheapest-route")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"maxWeight": 15,
									"availableTransfers": [
										{
											"weight": 5,
											"cost": 10
										},
										{
											"weight": 10,
											"cost": 20
										},
										{
											"weight": 3,
											"cost": 5
										},
										{
											"weight": 8,
											"cost": 15
										}
									]
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalWeight").value(15))
				.andExpect(jsonPath("$.totalCost").value(30));
	}

	@Test
	void testCallOverweight() throws Exception {
		mockMvc.perform(post("/transfer/cheapest-route")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"maxWeight": 15,
									"availableTransfers": [
										{
											"weight": 100,
											"cost": 10
										}
									]
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalWeight").value(0))
				.andExpect(jsonPath("$.totalCost").value(0));
	}

	@Test
	void testCallError() throws Exception {
		mockMvc.perform(post("/transfer/cheapest-route")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{}
								"""))
				.andExpect(status().isBadRequest());
	}

}
