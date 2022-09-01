package com.ciandt.summit.bootcamp2022;

import com.ciandt.summit.bootcamp2022.repository.ArtistRepository;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={
		"com.ciandt.summit.bootcamp2022.model",
		"com.ciandt.summit.bootcamp2022.controller",
		"com.ciandt.summit.bootcamp2022.dto",
		"com.ciandt.summit.bootcamp2022.exceptions",
		"com.ciandt.summit.bootcamp2022.repository",
		"com.ciandt.summit.bootcamp2022.service",
		"com.ciandt.summit.bootcamp2022.config.swagger"
})
@EnableJpaRepositories(basePackageClasses={
		MusicRepository.class,
		ArtistRepository.class
})
@EnableFeignClients
public class SummitBootcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummitBootcampApplication.class, args);
	}

}
