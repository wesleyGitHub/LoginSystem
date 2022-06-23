package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoginSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User("John", "Chen", "john.chen@gmail.com", "1234"));
			userService.saveUser(new User("Andy", "Liu", "andy.liu@gmail.com", "1234"));
			userService.saveUser(new User("Peter", "lin", "peter.lin@gmail.com", "1234"));
			userService.saveUser(new User("Judy", "Xe", "judy.xe@gmail.com", "1234"));


			userService.addRoleToUser("john.chen@gmail.com", "ROLE_USER");
			userService.addRoleToUser("john.chen@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("john.chen@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("andy.liu@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("peter.lin@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("judy.xe@gmail.com", "ROLE_USER");
			userService.addRoleToUser("judy.xe@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("judy.xe@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("judy.xe@gmail.com", "ROLE_SUPER_ADMIN");
		};
	}
}
