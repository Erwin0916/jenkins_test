package com.angkorchat.emoji.cms.domain.angkor.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CmsAuthKey {
    private String authKey;
    private LocalDateTime expireTm;
}
