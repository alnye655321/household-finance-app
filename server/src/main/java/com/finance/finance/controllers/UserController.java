package com.finance.finance.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.User;
import com.finance.finance.repositories.UserRepository;
import com.finance.finance.services.MyUserDetailsService;
import com.finance.finance.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin
    @GetMapping("/users")
    public List<User> getAllUsers() { //@RequestHeader("Authorization") String token
//        System.out.println(token);
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @CrossOrigin
    @PostMapping("/auth")
    public ResponseEntity<User> checkAuth(@Valid @RequestBody User user) {
        User foundUser = null;
        List<User> possibleUsers = userRepository.findAll();
        System.out.println("test");

        for (User possibleUser : possibleUsers) {
            if (possibleUser.getName().equals(user.getName())
                    && possibleUser.getHashedPassword().equals(user.getHashedPassword())) {
                foundUser = possibleUser;
                break;
            }

        }

        return ResponseEntity.ok().body(foundUser);

    }

    /**
     * Client is sending a partial User object with a username / password in it <br>
     * Authenticate the credentials request and return a new valid user entity from database if successful
     * @param user  User database entity - partial object, userName, hashedPassword and email included
     * @return  a new full User entity object
     */
    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<?> checkAuthenticate(@Valid @RequestBody User user) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getHashedPassword()));
        }
        catch (BadCredentialsException e) {
            System.out.print(e);
            return ResponseEntity.notFound().build(); //bad username / password combo, return not found
        }

        //Success - we are now authenticated!
        //Create the user from the found database details
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getName());
        final String jwt = jwtUtil.generateToken(userDetails);

        User myValidUser = userRepository.findByName(user.getName());
        myValidUser.setToken(jwt);

        return ResponseEntity.ok().body(myValidUser);

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                                   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setName(userDetails.getName());
        user.setHashedPassword(userDetails.getHashedPassword());
        user.setEmail(userDetails.getEmail());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}