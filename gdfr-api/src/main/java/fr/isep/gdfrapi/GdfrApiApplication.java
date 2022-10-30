package fr.isep.gdfrapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GdfrApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GdfrApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello GDFR API");
	}
}
