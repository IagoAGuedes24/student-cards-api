package com.studentcardsapi.service.interfaces;

import com.studentcardsapi.model.user.AppUser;

public interface AppUserService {

    public AppUser getUser(String username);
    AppUser saveAppUser(AppUser appUser);

}
