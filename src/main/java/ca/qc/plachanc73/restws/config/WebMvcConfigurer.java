package ca.qc.plachanc73.restws.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "ca.qc.plachanc73")
@EnableWebMvc
@EnableScheduling
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

	public static void main(String[] args) throws Exception {
		// @See Billet ouvert pour Sonar S2095
		// https://jira.sonarsource.com/browse/SONARJAVA-1687
		// ConfigurableApplicationContext retourné par cette méthode run est
		// Autocloseable mais il ne retient aucune resource
		SpringApplication.run(WebMvcConfigurer.class, args); // NOSONAR
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter(new ObjectMapper());
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}