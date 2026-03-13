package com.test.cph.controller;

import com.test.cph.entity.LoginRequest;
import com.test.cph.entity.LoginResponse;
import com.test.cph.serviceinf.UserInf;
import com.test.cph.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInf userServiceInf;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String,String> request){

        String email = request.get("email");

         if(email == null || email.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of(
                    "status","error",
                    "message","Email is required"
            ));
        }
        System.out.println("Email for forgot password "+email);
        try {
            userServiceInf.generateResetToken(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(Map.of(
                "status","success",
                "message","Reset password link sent to email"
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String,String> request){

        String token = request.get("token");
        String password = request.get("password");

        System.out.println("Token received: " + token);
        System.out.println("Password received: " + password);

        userServiceInf.resetPassword(token,password);

        return ResponseEntity.ok(Map.of(
                "message","Password updated successfully"
        ));
    }


}
