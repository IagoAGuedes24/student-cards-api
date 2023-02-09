package com.studentcardsapi.service.implementations;


import com.studentcardsapi.DTO.UserRegistrationDTO;
import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.model.user.AppUser;
import com.studentcardsapi.repository.AppUserRepository;
import com.studentcardsapi.service.interfaces.AppUserService;
import com.studentcardsapi.service.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.UUID;

import static com.studentcardsapi.utils.BusinessRulesConstants.MINUTES_FOR_USERNAME_CONFIRMATION_EXPIRATION;
import static com.studentcardsapi.utils.ErrorMessages.*;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImpl  implements AppUserService {

    private AppUserRepository<AppUser> appUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    private EmailService emailService;

    public AppUser saveAppUser(AppUser appUser) {
        appUser = appUser.getUserRole().instantiante(appUser);
        // TODO OBSERVAR COMO A DATABASE REAGE A ESSE SAVE
        this.appUserRepository.save(appUser);
        return appUser;
    }

    public AppUser getUser(String username) {
        return this.appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public AppUser getUser(String username, boolean enabled) {
        return this.appUserRepository
                .findByUsernameAndEnabled(username, enabled)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AppUser getUser(Long id) {
        return this.appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public AppUser getUser(Long id, boolean enabled) {
        return this.appUserRepository.findByIdAndEnabled(id, enabled)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AppUser registerUser(UserRegistrationDTO userRegistrationDTO) {

        checkUsername(userRegistrationDTO.getUsername());
        checkCpf(userRegistrationDTO.getCpf());
        AppUser appUser = new AppUser();
        appUser = this.modelMapper.map(userRegistrationDTO, AppUser.class);
        setDefaultValues(appUser);

        this.emailService.sendUsernameConfirmation(appUser.getUsername(), appUser.getUsernameConfirmationToken());
        // TODO REVISAR UTILIZAÇÃO DE GETS COM BASE EM ENABLED

        return this.saveAppUser(appUser);
    }

    @Override
    public AppUser confirmUsername(String activationToken) {
        AppUser appUser = this.appUserRepository.findByUsernameConfirmationToken(activationToken)
                .orElseThrow(() -> new ApiRequestException(INVALID_TOKEN));
        if (appUser.getUsernameConfirmationTokenExpiration().before(new Date(System.currentTimeMillis())))
            throw new ApiRequestException(EXPIRATED_TOKEN);
        if (appUser.isEnabled())
            throw new ApiRequestException(USER_ALREADY_ENABLED);

        appUser.setEnabled(true);
        return appUser;
    }


    private void checkCpf(String cpf) {

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            throw new ApiRequestException(INVALID_CPF);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if (!(dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                throw new ApiRequestException(INVALID_CPF);
        } catch (InputMismatchException erro) {
            throw new ApiRequestException(INVALID_CPF);
        }

        if (this.appUserRepository.findByCpf(cpf).isPresent())
            throw new ApiRequestException(CPF_ALREADY_EXISTS);
    }

    private void setDefaultValues(AppUser appUser) {
        appUser.setCreationDate(new Date(System.currentTimeMillis()));
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getCpf()));
        generateActivationToken(appUser);
    }

    public void generateActivationToken(AppUser appUser) {
        if (appUser.isEnabled())
            throw new ApiRequestException(USER_ALREADY_ENABLED);
        appUser.setUsernameConfirmationToken(UUID.randomUUID().toString());
        appUser.setUsernameConfirmationTokenExpiration(new Date(System.currentTimeMillis() + MINUTES_FOR_USERNAME_CONFIRMATION_EXPIRATION * 60 * 1000));
    }

    private void checkUsername(String username) {
        try {
            InternetAddress email = new InternetAddress(username);
            email.validate();
        } catch (Exception e) {
            throw new ApiRequestException(INVALID_USERNAME);
        }

        if (this.appUserRepository.findByUsername(username).isPresent())
            throw new ApiRequestException(USERNAME_ALREADY_EXISTS);
    }

    private void checkPasswordConfirmation(String password, String passwordConfirmation) {
        if (!(password.equals(passwordConfirmation)))
            throw new ApiRequestException(UNMATCHED_PASSWORDS);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}
