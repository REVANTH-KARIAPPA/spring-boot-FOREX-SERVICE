package com.forexservice.controller;

import com.forexservice.Repository.UserRepository;
import com.forexservice.config.TokenProvider;
import com.forexservice.exception.UserNotFoundException;
import com.forexservice.model.*;
import com.forexservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User u=userRepository.findByUsername(loginUser.getUsername());
        Set<Role> roles= u.getRoles();
        Set<Role> roleSet= roles.stream().filter(r->r.getName().equals("USER")).collect(Collectors.toSet());
        if(roleSet.isEmpty())
        {
            throw new UserNotFoundException("Currently Admin doesn't have access");
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,userService.getUserByName(loginUser.getUsername())));
    }
    @RequestMapping(value = "/authenticate/admin", method = RequestMethod.POST)
    public ResponseEntity<?> generateTokenAdmin(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        User u=userRepository.findByUsername(loginUser.getUsername());
        Set<Role> roles= u.getRoles();
        Set<Role> roleSet= roles.stream().filter(r->r.getName().equals("ADMIN")).collect(Collectors.toSet());
        if(roleSet.isEmpty())
        {
            throw new UserNotFoundException("Not Admin");
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,userService.getUserByName(loginUser.getUsername())));
    }


    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User saveUser( @Valid @RequestBody UserDto user ){
        return userService.save(user);
    }



    // my controller
    @GetMapping("/all")
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User users=userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
    }

}
