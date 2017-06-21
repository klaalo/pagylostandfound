package fi.iki.kari.pagylostandfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@EnableOAuth2Sso
public class PagylostandfoundApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(PagylostandfoundApplication.class, args);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/login**", "/item/*/view", "/mywot04d49d2c948b7a306ffa.html")
					.permitAll()
			.anyRequest()
				.authenticated()
        	;
	}
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
	    return new Java8TimeDialect();
	}
	
}
