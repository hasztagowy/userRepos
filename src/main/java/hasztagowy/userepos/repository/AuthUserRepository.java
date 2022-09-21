package hasztagowy.userepos.repository;

import hasztagowy.userepos.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findFirstByUserName(String name);

    @Modifying
    @Query("update AuthUser a set a.password=:password where a.userName=:name")
    void changePassword(@PathVariable String name, @PathVariable String password);
}
