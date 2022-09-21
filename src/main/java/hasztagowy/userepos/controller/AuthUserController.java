package hasztagowy.userepos.controller;

import hasztagowy.userepos.exceptions.ExceptionModel;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.model.AuthUserModel;
import hasztagowy.userepos.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthUserController {

    AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> addNewUser(@RequestBody AuthUserModel authUserModel) {
        try {
            return authUserService.saveUser(authUserModel);
        } catch (UserExistException e) {
            ExceptionModel ex= new ExceptionModel(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }
    }
}
