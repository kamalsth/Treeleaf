package com.treeleaf.restapi.services;

import com.treeleaf.restapi.config.CustomUserDetails;
import com.treeleaf.restapi.config.JwtTokenResponse;
import com.treeleaf.restapi.config.JwtTokenUtil;
import com.treeleaf.restapi.config.PasswordEncoder;
import com.treeleaf.restapi.entities.Role;
import com.treeleaf.restapi.entities.User;
import com.treeleaf.restapi.exceptions.CustomMessage;
import com.treeleaf.restapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user.get().getUsername(), user.get().getPassword(), user.get().getRoles());
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user.get();

    }

    public ResponseEntity<?> registerUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(hashPassword(user.getPassword()));
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


    public ResponseEntity<?> loginUser(User user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        if (userDetails == null) {
            return ResponseEntity.badRequest().body("User not found with username: " + user.getUsername());
        }
        if (!new PasswordEncoder().matches(user.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.badRequest().body(new CustomMessage("Password is not correct"));
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(token);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    public String hashPassword(String password) {
        return new PasswordEncoder().encodePassword(password);
    }

    public ResponseEntity<?> getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        User user=getUserByUsername(username);
        if(user!=null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new CustomMessage("User not found"));
    }
}
