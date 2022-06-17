package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwPalestraApplication implements CommandLineRunner {
	
//    @Autowired private CredentialsRepository credR;
//	@Autowired protected PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SiwPalestraApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		User u = new User();
//		u.setNome("Fabio");
//		u.setCognome("Letizia");
//		
//		Credentials c = new Credentials();
//		c.setUser(u);
//		c.setPassword(this.passwordEncoder.encode("admin"));
//		c.setUsername("admin");
//		c.setRole("ADMIN");
//		
//		credR.save(c);	
	}

}
