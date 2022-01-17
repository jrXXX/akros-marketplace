
package ch.akros.marketplace.dataservice;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class AkrosMarketplacedataServiceApplication {
  private static final String POSTGRES_DB_HOST_ENV = "POSTGRES_DB_HOST";

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AkrosMarketplacedataServiceApplication.class);

    application.run(args);
  }

  @Bean
  public DataSource getPostgresDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");

    if (System.getenv(POSTGRES_DB_HOST_ENV) != null && System.getenv(POSTGRES_DB_HOST_ENV).length() > 0) {
      // using container orchestration
      dataSource.setUrl(String.format("jdbc:postgresql://%s:5432/am", System.getenv(POSTGRES_DB_HOST_ENV)));
    }
    else {
      // localhost for local development
      dataSource.setUrl("jdbc:postgresql://localhost:5432/am");
    }

    dataSource.setUsername("am");
    dataSource.setPassword("am");
    return dataSource;
  }
}
