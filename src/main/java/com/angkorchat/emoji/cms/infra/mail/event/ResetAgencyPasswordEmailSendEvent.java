package com.angkorchat.emoji.cms.infra.mail.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetAgencyPasswordEmailSendEvent {
    private final String email;
    private final String pw;
}
