package com.studentcardsapi.utils;

import com.studentcardsapi.enums.AppUserRole;
import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.studentcardsapi.utils.constants.BusinessRulesConstants.USERS_PATH;
import static com.studentcardsapi.utils.messages.ErrorMessages.NULL_APP_USER_CLASS;

@NoArgsConstructor
@Getter
public class UserIdentifier {

    private Class appUserClass;

    public UserIdentifier(AppUserRole appUserRole) throws ClassNotFoundException {
        this.appUserClass = this.setUserClass(appUserRole);
    }

    public UserIdentifier(Class appUserClass) {
        this.appUserClass = appUserClass;
    }

    private Class setUserClass(AppUserRole appUserRole) throws ClassNotFoundException {
        String userPath = USERS_PATH + ".";
        String userRole = appUserRole.toString();
        String formatedUserRole =
                userRole.substring(0, 1).toUpperCase() +
                userRole.substring(1).toLowerCase();

        Class<?> userClass = Class.forName(userPath + formatedUserRole);
        this.appUserClass = userClass;
        return this.appUserClass;
    }

    public AppUser instantiate() throws InstantiationException, IllegalAccessException {
        if (this.appUserClass == null) {
            throw new ApiRequestException(NULL_APP_USER_CLASS);
        }

        return (AppUser) this.appUserClass.newInstance();
    }

    public  String getUserClassEnumString() {
        return this.appUserClass.toString()
                .replace("class " + USERS_PATH + ".", "")
                .toUpperCase();
    }
}
