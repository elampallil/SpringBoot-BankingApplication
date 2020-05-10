package binson.banking.binsonbank;

import ch.qos.logback.core.CoreConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BinsonbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinsonbankApplication.class, args);
		System.out.println("welcome");
	}

}
