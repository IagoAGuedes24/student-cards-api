package com.studentcardsapi.service.implementations;


import com.studentcardsapi.DTO.UserRegistrationDTO;
import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.user.AppUser;
import com.studentcardsapi.repository.AppUserRepository;
import com.studentcardsapi.service.interfaces.AppUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImpl  implements AppUserService {

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    private AppUserRepository<AppUser> appUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    public AppUser saveAppUser(AppUser appUser) {
        this.appUserRepository.save(appUser);
        return appUser;
    }

    public AppUser getUser(String username) {
        return this.appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AppUser getUser(Long id) {
        return this.appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AppUser registerUser(UserRegistrationDTO userRegistrationDTO) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}
