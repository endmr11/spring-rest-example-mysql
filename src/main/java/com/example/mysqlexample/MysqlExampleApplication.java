package com.example.mysqlexample;

import com.example.mysqlexample.entity.Todo;
import com.example.mysqlexample.entity.User;
import com.example.mysqlexample.repository.TodoRepository;
import com.example.mysqlexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

@SpringBootApplication
public class MysqlExampleApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(MysqlExampleApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sistem Ayakta...");
	}
}
