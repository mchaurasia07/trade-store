package com.assignment.tradestore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.repository.TradeRepository;
import com.assignment.tradestore.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ TradestoreApplication.class })
class TradeStoreTests{
	public MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@MockBean
	private TradeRepository tradeRepository;
	@Autowired
	private TradeService tradeService;

	@Test
	public void testPayload_withTradeIdEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":\"\",\"version\":2,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("tradeId length must be between 1 to 255"));

	}

	@Test
	public void testPayload_withTradeIdMissing() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"version\":2,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("tradeId is mandatory"));

	}

	@Test
	public void testPayload_withCounterPartyIdEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":2,\"counterPartyId\":\"\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("counterPartyId length must be between 1 to 255"));

	}

	@Test
	public void testPayload_withCounterPartyIdMissing() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":2,\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("counterPartyId is mandatory"));

	}

	@Test
	public void testPayload_withBookIdEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":2,\"counterPartyId\":\"CP-1\",\"bookId\":\"\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("bookId length must be between 1 to 255"));

	}

	@Test
	public void testPayload_withBookIdMissing() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":2,\"counterPartyId\":\"CP-1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("bookId is mandatory"));

	}

	@Test
	public void testPayload_withVersionHavingNegativeValue() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":-1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("version must be greater than or equal to 1"));

	}

	@Test
	public void testPayload_withVersionLargerThanMaxValue() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":2147483648,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testPayload_withMaturityDateNull() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":null}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("maturityDate is mandatory. supported format is yyyy-MM-dd"));

	}

	@Test
	public void testPayload_withMaturityDateEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Failed"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("maturityDate is mandatory. supported format is yyyy-MM-dd"));
	}

	@Test
	public void testPayload_withMaturityDateInvalidFormat() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"88899-898-9\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testPayload_withValidPayload() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"book-1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));
	}


	@Test
	public void testPayload_withPastMaturityDate() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2021-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("not acceptable"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("maturity date can not past date: 2021-01-01"));
	}


	@Test
	public void testPayload_WithTradeHavingLowerVersion() throws Exception {
		TradeEntity tradeEntity = new TradeEntity();
		tradeEntity.setTradeId("T2");
		tradeEntity.setVersion(10);
		tradeEntity.setBookId("book-2");
		tradeEntity.setCounterPartyId("CP-2");
		tradeEntity.setMaturityDate(LocalDate.now());
		tradeEntity.setExpiredFlag("N");
		tradeEntity.setCreatedDate(LocalDate.now());

		given(tradeRepository.findById(anyString())).willReturn(Optional.of(tradeEntity));
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/trade")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tradeId\":\"T2\",\"version\":1,\"counterPartyId\":\"CP-1\",\"bookId\":\"1\",\"maturityDate\":\"2023-01-01\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("not acceptable"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("version should not be lower than existing version 10: 1"));
	}

	@Test
	public void testExpireTrade() throws Exception {
		given(tradeRepository.expireTrade(anyString(), any())).willReturn(1);
		int count = tradeService.expireTrades();
		assertEquals(count, 1);

	}

	@Test
	public void testGetTradeWithId() throws Exception {
		TradeEntity tradeEntity = new TradeEntity();
		tradeEntity.setTradeId("T3");
		tradeEntity.setVersion(10);
		tradeEntity.setBookId("book-3");
		tradeEntity.setCounterPartyId("CP-3");
		tradeEntity.setMaturityDate(LocalDate.now());
		tradeEntity.setExpiredFlag("N");
		tradeEntity.setCreatedDate(LocalDate.now());

		given(tradeRepository.findById(anyString())).willReturn(Optional.of(tradeEntity));

		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/trade/{id}", "T3")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.tradeId").value("T3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.version").value("10"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.counterPartyId").value("CP-3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId").value("book-3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.expiredFlag").value("N"));
	}
}
