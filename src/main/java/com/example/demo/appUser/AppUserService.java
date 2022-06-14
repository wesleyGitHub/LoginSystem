package com.example.demo.appUser;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email : %s is not found";
    public final AppUserRepository appUserRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if(userExists) {
            throw  new IllegalStateException("email already taken.");
        }

        String encodedPassowrd = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassowrd);

        appUserRepository.save(appUser);

        //generate uuid token
        String token = UUID.randomUUID().toString();
        //generate confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConformationToken(
                confirmationToken);

        //todo:send email.
        return token;
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
