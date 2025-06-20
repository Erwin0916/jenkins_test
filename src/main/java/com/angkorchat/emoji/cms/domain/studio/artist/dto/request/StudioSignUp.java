package com.angkorchat.emoji.cms.domain.studio.artist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class StudioSignUp {
    @Schema(description = "약관 동의정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<StudioTermAgreement> termAgreement;
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private String angkorId;
    @Schema(hidden = true)
    private String phone;
    @Schema(description = "Artist 여권/은행 정보 파일 URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String attachFileUrl;
    @Schema(description = "passportName : 여권 기재 영어이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String passportName;
    @Schema(description = "email", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@email.com")
    private String email;
    @Schema(description = "phoneNumber : 8551012345678", requiredMode = Schema.RequiredMode.REQUIRED, example = "8551012345678")
    private String phoneNumber;
    @Schema(description = "phoneCode : 국가코드 855", requiredMode = Schema.RequiredMode.REQUIRED, example = "855")
    private String phoneCode;
    @Schema(description = "Artist 이름 영어", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String artistName;
    @Schema(description = "Artist 이름 크메르어", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "name")
    private String artistNameKm;
}
