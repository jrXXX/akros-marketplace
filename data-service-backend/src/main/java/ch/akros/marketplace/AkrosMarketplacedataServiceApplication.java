package ch.akros.marketplace;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AkrosMarketplacedataServiceApplication {
	private static final String POSTGRES_DB_HOST_ENV = "POSTGRES_DB_HOST";

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AkrosMarketplacedataServiceApplication.class);

		if (System.getenv(POSTGRES_DB_HOST_ENV) != null && System.getenv(POSTGRES_DB_HOST_ENV).length() > 0) {
			// using container orchestration
			Properties properties = new Properties();
			properties.put("spring.datasource.url",
					String.format("jdbc:postgresql://%s:5432/am", System.getenv(POSTGRES_DB_HOST_ENV)));
			application.setDefaultProperties(properties);
		} else {
			// localhost for local development
			Properties properties = new Properties();
			properties.put("spring.datasource.url", "jdbc:postgresql://localhost:5432/am");
			application.setDefaultProperties(properties);
		}

		application.run(args);
	}

}
