package com.ciandt.summit.bootcamp2022;

import com.ciandt.summit.bootcamp2022.repository.ArtistRepository;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class SummitBootcampApplication {

	public static void main(String[] args) {
		log.info("Starting MyMusic API");
		SpringApplication.run(SummitBootcampApplication.class, args);
		log.info("MyMusic API is ready to receive requests");
	}
}
