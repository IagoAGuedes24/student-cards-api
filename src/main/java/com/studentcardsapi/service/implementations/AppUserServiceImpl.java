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
        AppUser appUser = this.modelMapper.map(userRegistrationDTO, AppUser.class);
        setDefaultValues(appUser);

        this.emailService.sendUsernameConfirmation(appUser.getUsername(), appUser.getUsernameConfirmationToken());
        // TODO Confirmando, o usuário fica validado no sistema e a entidade é validada
        // TODO REVISAR UTILIZAÇÃO DE GETS COM BASE EM ENABLED
        // TODO CRIAR TOKEN DE SOLICITAÇÃO DE NOVO LINK

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

        if (cpf.length() != 11) {
            throw new ApiRequestException(INVALID_CPF);
        }

        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(cpf.charAt(i) + "");
            if (i > 0 && cpfArray[i] == cpfArray[i - 1]) {
                throw new ApiRequestException(INVALID_CPF);
            }
        }

        int firstDigit = 0;
        for (int i = 0; i < 9; i++) {
            firstDigit += (10 - i) * cpfArray[i];
        }
        firstDigit = (firstDigit % 11) < 2 ? 0 : 11 - (firstDigit % 11);
        if (firstDigit != cpfArray[9]) {
            throw new ApiRequestException(INVALID_CPF);
        }

        int secondDigit = 0;
        for (int i = 0; i < 10; i++) {
            secondDigit += (11 - i) * cpfArray[i];
        }
        secondDigit = (secondDigit % 11) < 2 ? 0 : 11 - (secondDigit % 11);
        if (secondDigit != cpfArray[10]) {
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
