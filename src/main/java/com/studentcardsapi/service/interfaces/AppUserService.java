package com.studentcardsapi.service.interfaces;

import com.studentcardsapi.DTO.UserRegistrationDTO;
import com.studentcardsapi.model.user.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    public AppUser getUser(String username);
    AppUser saveAppUser(AppUser appUser);

    AppUser getUser(Long id);

    AppUser registerUser(UserRegistrationDTO userRegistrationDTO);
}
