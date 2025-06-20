package com.angkorchat.emoji.cms.infra.mail.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QnaAnswerEmailSendEvent {
    private final String email;
    private final String id;
    private final String name;
    private final String questionTitle;
    private final String questionContent;
    private final String answerTitle;
    private final String answerContent;
}
