package com.x04e.projman.pmserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PmserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmserverApplication.class, args);
	}

	@GetMapping("/")
	public String root(){
		return "hello, world!";
	}
}
