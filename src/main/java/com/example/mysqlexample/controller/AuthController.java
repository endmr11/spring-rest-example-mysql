package com.example.mysqlexample.controller;


import com.example.mysqlexample.dao.AddUserRequest;
import com.example.mysqlexample.dao.AuthRequest;
import com.example.mysqlexample.entity.User;
import com.example.mysqlexample.model.BaseResponse;
import com.example.mysqlexample.service.JwtService;
import com.example.mysqlexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public ResponseEntity<BaseResponse<String>> welcome() {
        final var response = new BaseResponse<String>();
        response.setStatusCode(200);
        response.setModel("Welcome to my life!");
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/register")
    public User addNewUser(@RequestBody AddUserRequest userRequest) {
        final var user =  User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody AuthRequest authRequest) {
        final var response = new BaseResponse<String>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if(authentication.isAuthenticated()){
                final String username = authRequest.getUsername();
                final String token = jwtService.generateToken(username);
                response.setStatusCode(200);
                response.setModel(token);
                return ResponseEntity.status(200).body(response);
            }
            response.setMessage("invalid user request");
            response.setStatusCode(401);
            response.setModel(null);
            return ResponseEntity.status(401).body(response);

        }catch (Exception error){
            response.setMessage(error.getLocalizedMessage());
            response.setStatusCode(422);
            response.setModel(null);
            return ResponseEntity.status(422).body(response);
        }
    }
}
