package de.nloewes.roshambr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //TODO
public class RoshambrApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoshambrApplication.class, args);
	}

}
