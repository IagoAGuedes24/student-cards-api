package com.studentcardsapi.controller;

import com.studentcardsapi.DTO.UserDTO;
import com.studentcardsapi.DTO.UserRegistrationDTO;
import com.studentcardsapi.model.user.AppUser;
import com.studentcardsapi.service.interfaces.AppUserService;
import com.studentcardsapi.service.interfaces.TokenManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.studentcardsapi.utils.constants.EndpointConstants.*;

@RestController
@RequestMapping(path = API + USER)
@AllArgsConstructor
@Slf4j
public class AppUserController {

    AppUserService appUserService;

    ModelMapper modelMapper;

    TokenManagerService tokenDecoder;

    @PostMapping(CREATION)
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        log.info("Starting Creation of user " + userRegistrationDTO.getUsername());
        return new ResponseEntity<>(this.appUserService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping(USERNAME_CONFIRMATION + "/{activationToken}")
    public ResponseEntity<?> confirmUsername(@PathVariable String activationToken) {
        log.info("Starting confirmation of activation token " + activationToken);
        AppUser appUser = this.appUserService.confirmUsername(activationToken);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(GENERATE_NEW_USERNAME_ACTIVATION_TOKEN + "/{username}")
    public ResponseEntity<?> regenerateActivationToken(@PathVariable String username) {
        log.info("Starting  username activation token regeneration of user " + username);
        return new ResponseEntity<>(this.appUserService.generateActivationToken(username), HttpStatus.OK);
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<?> getUserById(@PathVariable Long appUserId) {
        return new ResponseEntity<>(modelMapper.map(appUserService.getUser(appUserId), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.tokenDecoder.refreshToken(request, response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request){
        AppUser user = this.tokenDecoder.decodeAppUserToken(request);
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

}
