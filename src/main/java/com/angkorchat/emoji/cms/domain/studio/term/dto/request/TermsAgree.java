package com.angkorchat.emoji.cms.domain.studio.term.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TermsAgree {
    @Schema(description = "angkorId", required = true, example = "akwekb123")
    private String angkorId;
    @Schema(description = "termsAgreeStr", required = true, example = "[\"1:Y\",\"2:Y\"]")
    private List<String> termsAgreeStr;
}
