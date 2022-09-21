package hasztagowy.userepos.repository;

import hasztagowy.userepos.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findFirstByUserName(String name);
}
