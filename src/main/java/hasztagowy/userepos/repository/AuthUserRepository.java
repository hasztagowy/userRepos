package hasztagowy.userepos.repository;

import hasztagowy.userepos.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findFirstByUserName(String name);

    @Modifying
    @Query("update AuthUser a set a.password=:password where a.userName=:name")
    void changePassword(@Param("name") String name, @Param("password") String password);

    @Modifying
    @Query("delete AuthUser a where a.userName=:name")
    @Transactional
    void deleteUser(@Param("name") String name);

}
