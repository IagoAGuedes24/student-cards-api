package com.studentcardsapi.utils.constants;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.studentcardsapi.utils.constants.EndpointConstants.*;

public class TokenConstants {

    public static final List<String> NO_TOKEN_ENDPOINTS = Collections.unmodifiableList(new ArrayList<>(Arrays.asList("/api/login",
            API + USER + CADASTER,
            API + USER + REFRESH_TOKEN,
            API + USER + FORGOT_PASSWORD,
            API + USER + SWITCH_PASSWORD + ALL_AFTER,
            API + USER + USERNAME_CONFIRMATION + ALL_AFTER,
            API + USER + GENERATE_NEW_USERNAME_ACTIVATION_TOKEN + ALL_AFTER,
            API + LOGIN
    )));

    public static final String BEARER = "Bearer ";

    public static final String SECRET_WORD_FOR_TOKEN_GENERATION = System.getenv("SECRET_WORD_FOR_TOKEN_GENERATION");

    public static final int MINUTES_FOR_TOKEN_EXPIRATION = 30;

    public static final String PERMIT_ALL_AFTER = "/**";

    public static final List<String> SWAGGER_ENDPOINTS =Collections.unmodifiableList(new ArrayList<>(Arrays.asList("/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**")));
}
