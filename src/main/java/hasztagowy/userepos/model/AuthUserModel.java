package hasztagowy.userepos.model;

import hasztagowy.userepos.entity.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthUserModel {
    private String userName;
    private String password;
    private UserRole role;

    public AuthUserModel(String userName, String password, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public AuthUserModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.role = UserRole.USER;
    }
}
