package com.nagarro.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.entity.CountryInfoEntity;
import com.nagarro.entity.GenderEntity;
import com.nagarro.entity.NationalityEntity;
import com.nagarro.entity.ResultEntity;
import com.nagarro.entity.UserData;
import com.nagarro.entity.ValidatedUserData;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Service
public class ApiService {

	@Autowired
	private UserDataValidationService userDataValidationService;

	public List<ValidatedUserData> getName(int size) throws InterruptedException, ExecutionException {

		String url = "https://randomuser.me/api/";

		List<ValidatedUserData> establishedData = new ArrayList<>();

		for (int count = 0; count < size; count++) {

			WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(
					HttpClient.newConnection().responseTimeout(Duration.ofMillis(2000)).doOnConnected(conn -> {
						conn.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS));
						conn.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
					}))).codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).baseUrl(url)
					.build();

			ExecutorService executorService = Executors.newFixedThreadPool(2);

			UserData newUser = webClient.get().retrieve().bodyToMono(UserData.class).block();

			List<ResultEntity> users = newUser.getResults();

			String firstName = users.stream().findFirst().map(user -> user.getName().getFirst()).orElse("");

			CompletableFuture<List<CountryInfoEntity>> nationalityFuture = CompletableFuture
					.supplyAsync(() -> getNationality(firstName), executorService);
			CompletableFuture<String> genderFuture = CompletableFuture.supplyAsync(() -> getGender(firstName),
					executorService);

			executorService.shutdown();

			List<CountryInfoEntity> nationality = nationalityFuture.get();

			String gender = genderFuture.get();

			establishedData.add(userDataValidationService.filterData(users, nationality, gender));
		}
		return establishedData;

	}

	public List<CountryInfoEntity> getNationality(String name) {

		String url = "https://api.nationalize.io/?name=" + name;

		WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(
				HttpClient.newConnection().responseTimeout(Duration.ofMillis(2000)).doOnConnected(conn -> {
					conn.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS));
					conn.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
				}))).codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).baseUrl(url)
				.build();
		NationalityEntity nationality = webClient.get().retrieve().bodyToMono(NationalityEntity.class).block();
		return nationality != null ? nationality.getCountry() : Collections.emptyList();
	}

	public String getGender(String name) {

		String url = "https://api.genderize.io/?name=" + name;
		WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(
				HttpClient.newConnection().responseTimeout(Duration.ofMillis(2000)).doOnConnected(conn -> {
					conn.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS));
					conn.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
				}))).codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).baseUrl(url)
				.build();
		GenderEntity gender = webClient.get().retrieve().bodyToMono(GenderEntity.class).block();
		return gender != null ? gender.getGender() : "";
	}

}