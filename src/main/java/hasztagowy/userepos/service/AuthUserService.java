package hasztagowy.userepos.service;

import hasztagowy.userepos.exceptions.EmptyValueException;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.AuthUserModel;
import hasztagowy.userepos.model.AuthUserResponseModel;

public interface AuthUserService {

    AuthUserModel findUserByName(String name) throws UserNotFoundException;

    AuthUserModel saveUser(AuthUserModel authUser) throws UserExistException, EmptyValueException;

    String changePassword(String password);

    AuthUserResponseModel deleteUser(String name) throws UserNotFoundException;


}
