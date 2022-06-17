package com.example.demo.service.impl;

import com.example.demo.entity.ConfirmationToken;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email : %s is not found";
    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("saving new User {} to the database.", user.getLastName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new Role {} to the database.", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findUserByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        log.info("saving new Role {} to User {} .",
                roleName,
                user.getFirstName() + " , " + user.getLastName()
                );
    }

    @Override
    public User getUser(String email) {
        log.info("fetching user by email {} ", email);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if(userExists) {
            throw  new IllegalStateException("email already taken.");
        }

        String encodedPassowrd = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassowrd);

        userRepository.save(user);

        //generate uuid token
        String token = UUID.randomUUID().toString();
        //generate confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConformationToken(
                confirmationToken);

        return token;
    }

    public void enableUser(String email) {
        userRepository.enableAppUser(email);
    }
}
