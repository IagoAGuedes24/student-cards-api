package com.studentcardsapi.enums;

import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.user.*;

public enum AppUserRole {
    ASSISTANT,
    COORDINATOR,
    PROFESSOR,
    STUDENT;

    public AppUser instantiante(AppUser appUser) {
        switch (appUser.getUserRole()) {
            case ASSISTANT:
                return (Assistant) appUser;
            case COORDINATOR:
                return (Coordinator) appUser;
            case PROFESSOR:
                return (Professor) appUser;
            case STUDENT:
                return (Student) appUser;
            default:
                throw new ApiRequestException("Unrecognized model type");
        }
    }
}
