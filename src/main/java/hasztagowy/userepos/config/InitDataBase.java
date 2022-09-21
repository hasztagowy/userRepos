package hasztagowy.userepos.config;

import hasztagowy.userepos.entity.AuthUser;
import hasztagowy.userepos.entity.UserRole;
import hasztagowy.userepos.repository.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitDataBase {
    @Bean
    CommandLineRunner commandLineRunner(AuthUserRepository authUserRepository) {

        return args -> {
            authUserRepository.saveAll(initUsers());
        };
    }

    private List<AuthUser> initUsers() {
        String name = "John";
        String pasword = "$2y$10$U.TPm1EAfVx85lC/Mfw5QOMmPqWYviG4WOfr1DK3YXAg5.ivJXtuG";
        AuthUser authUser = new AuthUser(name, pasword, UserRole.USER);
        String name1 = "admin";
        String password1 = "$2y$10$IVIbNeOswSOqIQrIfK/pqeQ3/Zwg3nZr7WFiseyDOLcJfU6ksFVrW";
        AuthUser authUser1 = new AuthUser(name1, password1, UserRole.ADMIN);

        return List.of(authUser, authUser1);
    }
}

