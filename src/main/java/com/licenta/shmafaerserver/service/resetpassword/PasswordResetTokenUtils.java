package com.licenta.shmafaerserver.service.resetpassword;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetTokenUtils {

    private final static int EXPIRY_RANGE_HOURS = 24;

    public static PasswordResetToken build(AppUser user)
    {
        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setUser(user);
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(EXPIRY_RANGE_HOURS));

        return passwordResetToken;

    }

    public static void resetExpiryDate(PasswordResetToken token)
    {
        token.setExpiryDate(LocalDateTime.now().plusHours(EXPIRY_RANGE_HOURS));
    }
}
