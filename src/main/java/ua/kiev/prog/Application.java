package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo2(final UserRoleService userRoleService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userRoleService.addUserRole(new UserRole(UserRoleEnum.ADMIN));
                userRoleService.addUserRole(new UserRole(UserRoleEnum.USER));
            }
        };
    }

    @Bean
    public CommandLineRunner demo(final UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.addUser(new CustomUser("admin", "40bd001563085fc35165329ea1ff5c5ecbdbbeef", new UserRole(UserRoleEnum.ADMIN))); // password: 123
                userService.addUser(new CustomUser("user", "40bd001563085fc35165329ea1ff5c5ecbdbbeef", new UserRole(UserRoleEnum.USER)));
            }
        };
    }
}