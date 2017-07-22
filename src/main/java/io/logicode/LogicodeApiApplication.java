package io.logicode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.logicode.mapper")
public class LogicodeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogicodeApiApplication.class, args);
	}
}
