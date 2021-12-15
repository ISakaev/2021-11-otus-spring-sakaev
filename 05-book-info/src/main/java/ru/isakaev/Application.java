package ru.isakaev;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.isakaev.dao.BookDao;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Application.class);
		Console.main(args);
	}

}
