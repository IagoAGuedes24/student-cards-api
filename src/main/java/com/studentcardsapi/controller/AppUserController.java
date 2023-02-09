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

import static com.studentcardsapi.utils.ConstantEndpoints.USERNAME_CONFIRMATION_ENDPOINT;

@RestController
@RequestMapping(path = "/api/usuario")
@AllArgsConstructor
public class AppUserController {

    AppUserService appUserService;

    ModelMapper modelMapper;

    TokenManagerService tokenDecoder;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        AppUser appUser = this.appUserService.registerUser(userRegistrationDTO);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping(USERNAME_CONFIRMATION_ENDPOINT + "/{activationToken}")
    public ResponseEntity<?> confirmUsername(@PathVariable("activationToken") String activationToken) {
        AppUser appUser = this.appUserService.confirmUsername(activationToken);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/gerar-novo-token")
    public ResponseEntity<?> regenerateActivationToken(HttpServletRequest request) {
        AppUser appUser = this.tokenDecoder.decodeAppUserToken(request);
        this.appUserService.generateActivationToken(appUser);
        this.appUserService.saveAppUser(appUser);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<?> getUserById(@PathVariable("appUserId") Long appUserId) {
        return new ResponseEntity<>(modelMapper.map(appUserService.getUser(appUserId), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.tokenDecoder.refreshToken(request, response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request){
        AppUser user = this.tokenDecoder.decodeAppUserToken(request);
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

}
