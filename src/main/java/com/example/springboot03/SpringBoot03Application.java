package com.example.springboot03;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springboot03.Mapper")
public class SpringBoot03Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot03Application.class, args);
	}

}
