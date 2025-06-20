package com.angkorchat.emoji.cms.infra.mail.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetPasswordEmailSendEvent {
    private final String email;
    private final String adminId;
    private final String pw;
}
