package hasztagowy.userepos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class BasicAuthSecurity {

    @Resource
    private CustomUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/users", "*/users/*").hasAuthority("ADMIN")
                .antMatchers("/h2-console/*").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .authenticationProvider(authProvider())
                .headers().frameOptions().disable();
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
