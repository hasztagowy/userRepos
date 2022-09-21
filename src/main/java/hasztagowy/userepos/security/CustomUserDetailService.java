package hasztagowy.userepos.security;

import hasztagowy.userepos.entity.AuthUser;
import hasztagowy.userepos.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AuthUser authUser = authUserRepository.findFirstByUserName(username);
        if (authUser != null) {
            return User.withUsername(authUser.getUserName()).password(authUser.getPassword()).authorities(authUser.getRole().toString()).build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
