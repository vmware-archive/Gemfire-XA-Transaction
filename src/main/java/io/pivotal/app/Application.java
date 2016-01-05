package io.pivotal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ImportResource("/spring/context/spring-context.xml")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "io.pivotal.repositories", entityManagerFactoryRef = "tradeEntityManager", transactionManagerRef = "transactionManager")
public class Application {

	@SuppressWarnings("unused")
	public static void main(String args[]) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}

}
