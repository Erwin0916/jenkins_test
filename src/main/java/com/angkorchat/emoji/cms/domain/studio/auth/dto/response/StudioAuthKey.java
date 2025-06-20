package com.angkorchat.emoji.cms.domain.studio.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudioAuthKey {
    private String authKey;
    private LocalDateTime expireTm;
}
