package hasztagowy.userepos.service;

import hasztagowy.userepos.exceptions.EmptyValueException;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.AuthUserModel;
import org.springframework.http.ResponseEntity;

public interface AuthUserService {

    ResponseEntity<?> findUserByName(String name) throws UserNotFoundException;

    ResponseEntity<?> saveUser(AuthUserModel authUser) throws UserExistException, EmptyValueException;

    ResponseEntity<?> changePassword(String password);

    ResponseEntity<?> deleteUser(String name) throws UserNotFoundException;


}
