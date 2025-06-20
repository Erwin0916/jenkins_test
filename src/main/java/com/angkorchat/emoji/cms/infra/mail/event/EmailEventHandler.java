package com.angkorchat.emoji.cms.infra.mail.event;

import com.angkorchat.emoji.cms.infra.mail.service.EmailService;
import com.angkorchat.emoji.cms.infra.mail.util.EmailMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventHandler {
    private final EmailService emailService;
    private final EmailMessageUtil emailMessageUtil;

    @Async
    @EventListener(ResetPasswordEmailSendEvent.class)
    public void handleResetPasswordEmailSendEvent(ResetPasswordEmailSendEvent event) {
        String subject = "[Angkor Emoji] Temporary password";

        emailService.sendEmail(
                event.getEmail(),
                subject,
                emailMessageUtil.getResetPasswordMessage(event.getAdminId(), event.getPw()),
                true
        );
    }

    @Async
    @EventListener(SignInAdminEmailSendEvent.class)
    public void handleSignInAdminSendEvent(SignInAdminEmailSendEvent event) {
        String subject = "[Angkor Emoji] Sign In Angkor Emoji CMS";

        emailService.sendEmail(
                event.getEmail(),
                subject,
                emailMessageUtil.getSignInAdminMessage(event.getAdminId(), event.getPw()),
                true
        );
    }

    @Async
    @EventListener(SignInStudioUserEmailSendEvent.class)
    public void handleSignInStudioUserEmailSendEvent(SignInStudioUserEmailSendEvent event) {
        String subject = "[Emoji Studio] Sign In Angkor Emoji Studio";

        emailService.sendEmail(
                event.getEmail(),
                subject,
                emailMessageUtil.getSignInStudioUserEmailSendEvent(event.getPw()),
                true
        );
    }
}
