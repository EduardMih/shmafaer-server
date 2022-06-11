package com.licenta.shmafaerserver.service.resetpassword;

import com.licenta.shmafaerserver.dto.request.PasswordResetDTO;
import com.licenta.shmafaerserver.dto.response.PasswordResetResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidPasswordResetToken;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.PasswordResetToken;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.PasswordResetTokenRepository;
import com.licenta.shmafaerserver.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private static final String CLIENT_BASE_URL = "http://localhost:4200";
    private static final String PASSWORD_RESET_PATH = "/resetPassword";

    private final AppUserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private PasswordResetToken createResetPasswordToken(String email) throws UnknownUserEmail
    {
        AppUser user = userRepository.findAppUserByEmail(email)
                .orElseThrow(UnknownUserEmail::new);
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByUser(user);
        PasswordResetToken token;

        if(tokenOptional.isPresent())
        {
            token = tokenOptional.get();
            PasswordResetTokenUtils.resetExpiryDate(token);
        }

        else
        {

            token = PasswordResetTokenUtils.build(user);

        }

        return passwordResetTokenRepository.save(token);

    }

    private void sendPasswordResetToken(PasswordResetToken passwordResetToken)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(passwordResetToken.getUser().getEmail());
        mailMessage.setSubject("Password Reset on SHMAFAER");
        mailMessage.setText(CLIENT_BASE_URL + PASSWORD_RESET_PATH + "?token=" + passwordResetToken.getToken());

        emailService.sendMail(mailMessage);
    }

    public PasswordResetResponseDTO initializeResetPassword(String email) throws UnknownUserEmail
    {
        PasswordResetResponseDTO resetResponseDTO = new PasswordResetResponseDTO();
        PasswordResetToken token = createResetPasswordToken(email);

        sendPasswordResetToken(token);

        resetResponseDTO.setMessage("An email was sent to " + email);

        return resetResponseDTO;

    }

    public PasswordResetResponseDTO resetPassword(PasswordResetDTO passwordResetDTO) throws InvalidPasswordResetToken, UnknownUserEmail
    {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(passwordResetDTO.getToken())
                .orElseThrow(() -> new InvalidPasswordResetToken("Token has expired or is invalid"));

        AppUser user = userRepository.findAppUserByEmail(token.getUser().getEmail())
                .orElseThrow(UnknownUserEmail::new);

        PasswordResetResponseDTO response = new PasswordResetResponseDTO();

        user.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));

        userRepository.save(user);
        passwordResetTokenRepository.delete(token);

        response.setMessage("Password successfully reset");

        return response;


    }
}
