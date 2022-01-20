package ch.akros.marketplace;

import java.util.Properties;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AkrosMarketplacedataServiceApplication {
	private static final String POSTGRES_DB_HOST_ENV = "POSTGRES_DB_HOST";
	 //HTTP port
    @Value("${http.port}")
    private int httpPort;
    
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
	
	 @Bean
	    public ServletWebServerFactory servletContainer() {
	        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
	        return tomcat;
	    }

	    private Connector createStandardConnector() {
	        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	        connector.setPort(httpPort);
	        return connector;
	    }

}
