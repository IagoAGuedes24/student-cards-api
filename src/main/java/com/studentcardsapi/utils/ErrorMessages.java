package com.studentcardsapi.utils;

public class ErrorMessages {

    public static final String GENERIC_ERROR = "Algo deu errado, tente novamente.";
    public static final String USER_NOT_FOUND = "O usuário infrmado não existe no sistema ou ainda não foi validado via e-mail";
    public static final String UNMATCHED_PASSWORDS = "A senha e a confirmação de senha não são iguais";
    public static final String USERNAME_ALREADY_EXISTS = "O e-mail informado já existe no sistema";
    public static final String INVALID_USERNAME = "O e-mail informado não está no formato adequado";
    public static final String INVALID_CPF = "O CPF informado não está no formato adequado";

    public static final String CPF_ALREADY_EXISTS = "O CPF informado já existe no sistema";

    public static final String INVALID_TOKEN = "O link informado está incorreto";

    public static final String EXPIRATED_TOKEN = "O link informado já expirou, solicite novo link de ativação de conta.";

    public static final String USER_ALREADY_ENABLED = "O usuário já está autenticado no sistema";
}
