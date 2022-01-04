package Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import Controller.Controller;

@SpringBootApplication
@ComponentScan(basePackageClasses = Controller.class)
public class CurrencyLayerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyLayerProjectApplication.class, args);
	}

}
