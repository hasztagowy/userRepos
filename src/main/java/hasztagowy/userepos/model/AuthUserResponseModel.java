package hasztagowy.userepos.model;

import hasztagowy.userepos.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserResponseModel {

    private String userName;
    private UserRole userRole;

    private String message;

    public AuthUserResponseModel(String userName, UserRole userRole, String message) {

        this.userName = userName;
        this.userRole = userRole;
        this.message = message;
    }
}
