package com.studentcardsapi.service.interfaces;

import com.auth0.jwt.algorithms.Algorithm;
import com.studentcardsapi.model.user.AppUser;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TokenManagerService {

    String createAppUserToken(HttpServletRequest request, Authentication authentication);

    AppUser decodeAppUserToken(HttpServletRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void tokenDecodeError(Exception e, HttpServletResponse response) throws IOException;

    AppUser getCurrentUser(String token, Algorithm algorithm);

}