package com.licenta.shmafaerserver.service.accountconfirmation;

import com.licenta.shmafaerserver.dto.request.ConfirmAccountDTO;
import com.licenta.shmafaerserver.dto.response.ConfirmAccountResponseDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidConfirmationToken;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.ConfirmationToken;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.ConfirmationTokenRepository;
import com.licenta.shmafaerserver.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountConfirmationService {
    private static final String CLIENT_BASE_URL = "http://localhost:4200";
    private static final String CONFIRM_ACCOUNT_PATH = "/confirmAccount";

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository userRepository;

    private final EmailService emailService;

    public void sendConfirmToken(AppUser user)
    {
        ConfirmationToken confirmationToken = ConfirmationTokenUtils.build(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        confirmationTokenRepository.save(confirmationToken);

        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration on SHMAFAER");
        mailMessage.setText(CLIENT_BASE_URL + CONFIRM_ACCOUNT_PATH + "?token=" + confirmationToken.getToken());

        emailService.sendMail(mailMessage);

    }

    public ConfirmAccountResponseDTO confirmAccount(ConfirmAccountDTO confirmAccountDTO) throws InvalidConfirmationToken
    {
        ConfirmAccountResponseDTO responseDTO = new ConfirmAccountResponseDTO();
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(confirmAccountDTO.getToken());

        if((confirmationToken.isPresent()) && (confirmationToken.get().getExpiryDate().isAfter(LocalDateTime.now())))
        {
            enableAccount(confirmationToken.get().getUser());

            responseDTO.setStatus("OK");
            responseDTO.setMessage("Account Confirmed successfully");

            return responseDTO;

        }

        throw new InvalidConfirmationToken("Token has expired or is invalid");
    }

    private void enableAccount(AppUser user)
    {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
