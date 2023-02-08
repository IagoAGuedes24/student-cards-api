package com.studentcardsapi.service.implementations;


import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.user.AppUser;
import com.studentcardsapi.repository.AppUserRepository;
import com.studentcardsapi.service.interfaces.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImpl  implements AppUserService {

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    private AppUserRepository<AppUser> appUserRepository;

    public AppUser saveAppUser(AppUser appUser) {
        this.appUserRepository.save(appUser);
        return appUser;
    }

    public AppUser getUser(String username) {
        return this.appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

}
