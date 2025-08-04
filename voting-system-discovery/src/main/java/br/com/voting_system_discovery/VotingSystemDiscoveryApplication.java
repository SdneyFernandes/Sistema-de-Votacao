package br.com.voting_system_discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableEurekaServer
public class VotingSystemDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingSystemDiscoveryApplication.class, args);
	}

}
