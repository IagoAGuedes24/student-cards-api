package com.studentcardsapi.utils.messages;

public class ErrorMessages {

    public static final String GENERIC_ERROR = "Something went wrong, please try again.";

    public static final String USER_DOES_NOT_EXIST = "This user does not exist yet.";
    public static final String UNMATCHED_PASSWORDS = "The password does not match with the password confirmation";
    public static final String USERNAME_ALREADY_EXISTS = "This e-mail already exists";
    public static final String INVALID_USERNAME = "This e-mail has an invalid format.";
    public static final String INVALID_CPF = "This CPF has an invalid format.";

    public static final String CPF_ALREADY_EXISTS = "This CPF already exists.";
    public static final String INVALID_TOKEN = "This link is invalid.";

    public static final String EXPIRATED_TOKEN = "This link is already expired, please request a new one.";

    public static final String USER_ALREADY_ENABLED = "You are already enabled, please log in.";

    public static final String INVALID_OR_NOT_ENABLED_USERNAME = "This user is not enabled or does not exist.";

    public static final String NULL_APP_USER_CLASS = "Inform the enum for the identifier class before instantiating";

    public static final String MISSING_TOKEN = "the token for this request is missing or it is incomplete";

    public static final String SUBJECT_ALREADY_EXISTS = "this subject already exists at this year";

    public static final String INVALID_SUBJECT_ID = "this subject id does not correspond to a valid subject";
}
