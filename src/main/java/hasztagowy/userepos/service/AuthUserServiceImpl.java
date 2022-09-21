package hasztagowy.userepos.service;

import hasztagowy.userepos.entity.AuthUser;
import hasztagowy.userepos.exceptions.UserExistException;
import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.AuthUserModel;
import hasztagowy.userepos.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public ResponseEntity<?> findUserByName(String name) throws UserNotFoundException {
        AuthUser authUser = authUserRepository.findFirstByUserName(name);
        if (authUser != null) {
            return ResponseEntity.ok(authUser);
        } else {
            throw new UserNotFoundException("User are not in database!");
        }
    }

    @Override
    public ResponseEntity<?> saveUser(AuthUserModel authUser) throws UserExistException {
        if (authUserRepository.findFirstByUserName(authUser.getUserName()) != null) {
            throw new UserExistException("User with that userName is already in database!");
        } else {
            AuthUser authUser1 = new AuthUser(authUser.getUserName(), bCryptPasswordEncoder.encode(authUser.getPassword()), authUser.getRole());
            authUserRepository.save(authUser1);
            return ResponseEntity.status(HttpStatus.CREATED).body(authUser);
        }
    }
}
