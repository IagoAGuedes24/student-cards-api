package com.studentcardsapi.service.interfaces;

import com.auth0.jwt.algorithms.Algorithm;
import com.studentcardsapi.model.user.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface TokenManagerService {

    String createAppUserToken(HttpServletRequest request, Authentication authentication);

    AppUser decodeAppUserToken(HttpServletRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void tokenDecodeError(Exception e, HttpServletResponse response) throws IOException;

    AppUser decodeToken(String token, Algorithm algorithm);

}