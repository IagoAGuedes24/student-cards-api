package com.studentcardsapi.controller;

import com.studentcardsapi.DTO.UserDTO;
import com.studentcardsapi.DTO.UserRegistrationDTO;
import com.studentcardsapi.model.user.AppUser;
import com.studentcardsapi.service.interfaces.AppUserService;
import com.studentcardsapi.service.interfaces.TokenManagerService;
import lombok.AllArgsConstructor;
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
public class AppUserController {

    AppUserService appUserService;

    ModelMapper modelMapper;

    TokenManagerService tokenDecoder;

    @PostMapping(CADASTER)
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        return new ResponseEntity<>(this.appUserService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping(USERNAME_CONFIRMATION + "/{activationToken}")
    public ResponseEntity<?> confirmUsername(@PathVariable("activationToken") String activationToken) {
        AppUser appUser = this.appUserService.confirmUsername(activationToken);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(GENERATE_NEW_USERNAME_ACTIVATION_TOKEN + "/{username}")
    public ResponseEntity<?> regenerateActivationToken(@PathVariable("username") String username) {
        return new ResponseEntity<>(this.appUserService.generateActivationToken(username), HttpStatus.OK);
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<?> getUserById(@PathVariable("appUserId") Long appUserId) {
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
