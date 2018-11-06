package willydekeyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan
@SpringBootApplication
@EnableAsync
public class SpringDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);	
	}
}
