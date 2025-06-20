package com.angkorchat.emoji.cms.infra.mail.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class EmailMessageUtil {
    @Value("${emoji.cms.url}")
    private String cmsUrl;
    @Value("${emoji.studio.url}")
    private String studioUrl;

    public String getResetPasswordMessage(String adminId, String pw) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("email/reset-password.html").getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }

            return stringBuilder.toString()
                    .replace("{adminId}", adminId)
                    .replace("{pw}", pw)
                    .replace("{url}", cmsUrl);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getCause());
        }
    }

    public String getSignInAdminMessage(String adminId, String pw) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("email/signIn-admin.html").getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }

            return stringBuilder.toString()
                    .replace("{adminId}", adminId)
                    .replace("{pw}", pw)
                    .replace("{url}", cmsUrl);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getCause());
        }
    }

    public String getSignInStudioUserEmailSendEvent(String pw) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("email/signIn-studio-user.html").getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }

            return stringBuilder.toString()
                    .replace("{pw}", pw)
                    .replace("{url}", studioUrl);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getCause());
        }
    }
}
