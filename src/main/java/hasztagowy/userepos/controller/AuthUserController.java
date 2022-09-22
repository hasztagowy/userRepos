package hasztagowy.userepos.controller;

import hasztagowy.userepos.exceptions.EmptyValueException;
import hasztagowy.userepos.exceptions.ExceptionModel;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.AuthUserModel;
import hasztagowy.userepos.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AuthUserController {

    AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestBody AuthUserModel authUserModel) {
        try {
            return authUserService.saveUser(authUserModel);
        } catch (UserExistException | EmptyValueException e ) {
            ExceptionModel ex= new ExceptionModel(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }
    }
    @PutMapping("/users/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody AuthUserModel authUserModel){
        return null;
    }

    @DeleteMapping("/users")

    public ResponseEntity<?> deleteUser(@RequestParam String name) throws UserNotFoundException {
        if(name.isEmpty() || name.isBlank()){
            throw  new UserNotFoundException("user not found");
        }
      return authUserService.deleteUser(name);
    }
}
