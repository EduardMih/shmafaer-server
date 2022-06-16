package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.UserConverter;
import com.licenta.shmafaerserver.dto.request.ChangePasswordDTO;
import com.licenta.shmafaerserver.dto.request.UpdateUserInfoDTO;
import com.licenta.shmafaerserver.dto.response.UserDetailsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidOldPassword;
import com.licenta.shmafaerserver.exception.CustomExceptions.UserAlreadyExists;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.security.AuthenticationFacade;
import com.licenta.shmafaerserver.security.service.RefreshTokenService;
import com.licenta.shmafaerserver.service.accountconfirmation.AccountConfirmationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final AppUserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AccountConfirmationService accountConfirmationService;

    @Transactional
    public UserDetailsDTO updateUserInfo(UpdateUserInfoDTO updatedUserInfo) throws UserAlreadyExists
    {
        AppUser currentUser = userRepository.findAppUserByEmail(
                authenticationFacade.getAuthenticatedUser().getEmail())
                .orElseThrow(UnknownError::new);

        Authentication newAuth;

        if((updatedUserInfo.getEmail() != null) &&
                (updatedUserInfo.getEmail().trim().length() > 0) &&
                (!Objects.equals(updatedUserInfo.getEmail(), currentUser.getEmail())))
        {
            if(userRepository.existsByEmail(updatedUserInfo.getEmail()))
            {
                throw new UserAlreadyExists();
            }
            //currentUser.setEmail(updatedUserInfo.getEmail());
            updateEmail(currentUser, updatedUserInfo.getEmail());
        }

        if((updatedUserInfo.getFirstname() != null) &&
                (updatedUserInfo.getFirstname().trim().length() > 0) &&
                (!Objects.equals(updatedUserInfo.getFirstname(), currentUser.getFirstname())))
        {
            currentUser.setFirstname(updatedUserInfo.getFirstname());
        }

        if((updatedUserInfo.getLastname() != null) &&
                (updatedUserInfo.getLastname().trim().length() > 0) &&
                (!Objects.equals(updatedUserInfo.getLastname(), currentUser.getLastname())))
        {
            currentUser.setLastname(updatedUserInfo.getLastname());
        }

        refreshTokenService.deleteToken(currentUser.getEmail());

        return userConverter.convertAppUserToDetailsDTO(currentUser);

    }

    @Transactional
    public UserDetailsDTO changePassword(ChangePasswordDTO changePasswordDTO) throws InvalidOldPassword
    {
        AppUser currentUser = userRepository.findAppUserByEmail(
                        authenticationFacade.getAuthenticatedUser().getEmail())
                .orElseThrow(UnknownError::new);

        if(!oldPasswordMatch(currentUser, changePasswordDTO))
        {
            throw new InvalidOldPassword();
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));

        refreshTokenService.deleteToken(currentUser.getEmail());

        return userConverter.convertAppUserToDetailsDTO(currentUser);

    }

    private boolean oldPasswordMatch(AppUser user, ChangePasswordDTO changePasswordDTO)
    {

        return passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());

    }

    private void updateEmail(AppUser user, String email)
    {
        user.setEmail(email);
        user.setEnabled(false);
        user = userRepository.save(user);

        accountConfirmationService.sendConfirmToken(user);
    }
}
