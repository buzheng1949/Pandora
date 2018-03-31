package com.gdut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(value = "com.gdut.pandora.mapper")
public class PandoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandoraApplication.class, args);
	}
}
