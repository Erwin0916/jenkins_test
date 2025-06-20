package com.angkorchat.emoji.cms.domain.studio.term.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioUserTermList {
    private Integer termId;
    private String agreeYn;
    private String version;
    private String termType;
    private Integer agreeId;
}
