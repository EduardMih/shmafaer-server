package com.licenta.shmafaerserver.service.accountconfirmation;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.ConfirmationToken;

import java.time.LocalDateTime;

import java.util.UUID;

public class ConfirmationTokenUtils {

    public static final int EXPIRY_RANGE_HOURS = 24;

    public static ConfirmationToken build(AppUser user)
    {
        ConfirmationToken token = new ConfirmationToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(EXPIRY_RANGE_HOURS));

        return token;

    }

    public static void updateToken(ConfirmationToken token)
    {
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(EXPIRY_RANGE_HOURS));
    }
}
