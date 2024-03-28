package com.nagarro.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import com.nagarro.entity.PageInfoEntity;
import com.nagarro.entity.UserAndPageInfo;
import com.nagarro.entity.ValidatedUserData;
import com.nagarro.repository.ValidatedUserDataRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiServiceTest {

	@InjectMocks
	UserDataValidationService userDataValidationService;

	@Mock
	ValidatedUserDataRepository userData;

	@Autowired
	private MockMvc mockMvc;

	@Test
	// GET request for sortType = Age and sortOrder = Odd and Even
	void testSortedUsersByAge() throws Exception {
		ValidatedUserData user1 = new ValidatedUserData(1, "IsaacWashington", 46, "male", "1977-05-21T06:45:44.385Z", "AU", "TO_BE_VERIFIED",
				LocalDateTime.now(), LocalDateTime.now());
		PageInfoEntity user1Page = new PageInfoEntity(10, true, true);

		ValidatedUserData user2 = new ValidatedUserData(2, "ReginaldAlexander", 77, "male", "1946-07-28T01:41:36.027Z", "US", "VERIFIED",
				LocalDateTime.now(), LocalDateTime.now());
		PageInfoEntity user2Page = new PageInfoEntity(5, true, true);

		List<UserAndPageInfo> sampleUsersByAgeOdd = Arrays.asList(new UserAndPageInfo(user1, user1Page),
				new UserAndPageInfo(user2, user2Page));

		when(userDataValidationService.getValidatedUserData("age", "odd", "2", "0")).thenReturn(sampleUsersByAgeOdd);

		// Perform GET request for sortType = Age and sortOrder = Odd
		mockMvc.perform(get("http://localhost:3030/users?sortType=Age&sortOrder=odd&limit=2&offset=0"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userData.user_id").value(2));

		List<UserAndPageInfo> sampleUsersByAgeEven = Arrays.asList(new UserAndPageInfo(user1, user1Page),
				new UserAndPageInfo(user2, user2Page));

		when(userDataValidationService.getValidatedUserData("age", "even", "2", "0")).thenReturn(sampleUsersByAgeEven);

		// Perform GET request for sortType = Age and sortOrder = Even
		mockMvc.perform(get("http://localhost:3030/users?sortType=Age&sortOrder=even&limit=2&offset=0"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userData.user_id").value(1));
	}

	@Test
	// GET request for sortType = Name and sortOrder = Odd and Even
	void testSortedUsersByName() throws Exception {
		ValidatedUserData user1 = new ValidatedUserData(25, "RasmusMadsen", 45, "male", "1978-09-27T06:35:13.007Z",
				"DK", "VERIFIED", LocalDateTime.now(), LocalDateTime.now());
		PageInfoEntity user1Page = new PageInfoEntity(40, true, true);

		ValidatedUserData user2 = new ValidatedUserData(26, "AngusAnderson", 64, "male", "1959-10-23T05:44:34.979Z",
				"NZ", "VERIFIED", LocalDateTime.now(), LocalDateTime.now());
		PageInfoEntity user2Page = new PageInfoEntity(40, true, true);

		List<UserAndPageInfo> sampleUsersByNameOdd = Arrays.asList(new UserAndPageInfo(user1, user1Page),
				new UserAndPageInfo(user2, user2Page));

		when(userDataValidationService.getValidatedUserData("Name", "odd", "2", "24")).thenReturn(sampleUsersByNameOdd);

		// Perform GET request for sortType = Name and sortOrder = Odd
		mockMvc.perform(get("http://localhost:3030/users?sortType=Name&sortOrder=odd&limit=2&offset=24"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userData.user_id").value(26));

		List<UserAndPageInfo> sampleUsersByNameEven = Arrays.asList(new UserAndPageInfo(user1, user1Page),
				new UserAndPageInfo(user2, user2Page));

		when(userDataValidationService.getValidatedUserData("Name", "even", "2", "24")).thenReturn(sampleUsersByNameEven);

		// Perform GET request for sortType = Name and sortOrder = Even
		mockMvc.perform(get("http://localhost:3030/users?sortType=Name&sortOrder=even&limit=2&offset=24"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userData.user_id").value(25));
	}

	@Test
	// Invalid Sort Type Test Case
	public void invalidSortType() throws Exception {
		mockMvc.perform(get("http://localhost:3030/users?sortType=Naveen&sortOrder=ODD&limit=3&offset=17"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	// Invalid Sort Order Test Case
	public void invalidSortOrder() throws Exception {
		mockMvc.perform(get("http://localhost:3030/users?sortType=Name&sortOrder=Top&limit=3&offset=17"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	// Limit Out Of Range Test Case
	public void limitOutOfRange() throws Exception {
		mockMvc.perform(get("http://localhost:3030/users?sortType=Name&sortOrder=Even&limit=6&offset=17"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	// Offset Out Of Range Test Case
	public void offsetInvalidValue() throws Exception {
		mockMvc.perform(get("http://localhost:3030/users?sortType=Name&sortOrder=Even&limit=4&offset=-2"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	// Post Method Test Case
	public void testSize() throws Exception {
		String requestBody = "{\"size\": 4}";
		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:3030/users")
				.contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	// Post Method Invalid Size Test Case
	public void invalidSize() throws Exception {
		String requestBody = "{\"size\": 6}";
		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:3030/users")
				.contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
}