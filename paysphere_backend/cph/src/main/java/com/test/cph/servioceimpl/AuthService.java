package com.test.cph.servioceimpl;

import com.test.cph.dto.AuthResponse;
import com.test.cph.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse refreshToken(String refreshToken) {

        String username = jwtUtil.extractUsername(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtUtil.validateToken(refreshToken, userDetails)) {

            String newAccessToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            return new AuthResponse(newAccessToken, newRefreshToken);
        }

        throw new RuntimeException("Invalid Refresh Token");
    }


}
