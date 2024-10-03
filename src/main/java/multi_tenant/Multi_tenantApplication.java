package multi_tenant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Multi_tenantApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Multi_tenantApplication.class, args);
	}
		
		@Bean
	    public CommandLineRunner run(PasswordEncoder passwordEncoder) {
	        return args -> {
	            String rawPassword = "123";
	            String encodedPassword = passwordEncoder.encode(rawPassword);
	            System.out.println("Senha criptografada: " + encodedPassword);
	        };
	    }

}
