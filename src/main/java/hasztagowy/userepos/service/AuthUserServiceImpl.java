package hasztagowy.userepos.service;

import hasztagowy.userepos.entity.AuthUser;
import hasztagowy.userepos.exceptions.EmptyValueException;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.AuthUserModel;
import hasztagowy.userepos.model.AuthUserResponseModel;
import hasztagowy.userepos.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    private final AuthUserRepository authUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


    @Override
    public AuthUserModel findUserByName(String name) throws UserNotFoundException {
        AuthUser authUser = authUserRepository.findFirstByUserName(name);
        AuthUserModel authUserModel = new AuthUserModel(authUser.getUserName(), authUser.getPassword(), authUser.getRole());
        if (authUser != null) {
            return authUserModel;
        } else {
            throw new UserNotFoundException("User are not in database!");
        }
    }

    @Override
    public AuthUserModel saveUser(AuthUserModel authUser) throws UserExistException, EmptyValueException {
        if (authUser.getUserName().isEmpty() || authUser.getUserName().isBlank()) {
            throw new EmptyValueException("user cannot be null or empty");
        } else if (authUser.getPassword().isEmpty() || authUser.getPassword().isBlank()) {
            throw new EmptyValueException("password cannot be null or empty");
        }
        if (authUserRepository.findFirstByUserName(authUser.getUserName()) != null) {
            throw new UserExistException("User with that userName is already in database!");
        } else {
            AuthUser authUser1 = new AuthUser(authUser.getUserName(), bCryptPasswordEncoder.encode(authUser.getPassword()), authUser.getRole());
            authUserRepository.save(authUser1);
            return new AuthUserModel(authUser1.getUserName(), authUser1.getPassword(), authUser1.getRole());
        }
    }

    @Override
    public String changePassword(String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = headers.get("Authorization").get(0);
        byte[] decodedHeader = Base64.getDecoder().decode(auth);
        String[] decodedString = new String(decodedHeader).split(":");
        authUserRepository.changePassword(decodedString[0], password);
        return decodedString[0];
    }

    @Override
    public AuthUserResponseModel deleteUser(String name) throws UserNotFoundException {
        AuthUser authUser = authUserRepository.findFirstByUserName(name);
        if (authUser == null) {
            throw new UserNotFoundException("user not found");
        }
        authUserRepository.deleteUser(name);

        return new AuthUserResponseModel(authUser.getUserName(), authUser.getRole(), "user was deleted");

    }
}
