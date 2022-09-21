package hasztagowy.userepos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AuthUser {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private UserRole role;

    public AuthUser(String userName, String password, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
