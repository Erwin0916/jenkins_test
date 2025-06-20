package com.angkorchat.emoji.cms.domain.angkor.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistDetail extends ArtistListDto {
    @Schema(description = "artist km name", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String nameKm;
    @Schema(description = "artist km name", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String angkorId;
    @Schema(description = "연락처(국가코드 미포함)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "01011115678")
    private String phone;
    @Schema(description = "연락처(국가코드 포함)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "821011115678")
    private String phoneNumber;
    @Schema(description = "국가코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "82")
    private String phoneCode;
    @Schema(description = "기본주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Korea, seoul")
    private String baseAddress;
    @Schema(description = "상세주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Clover Apt. 806-1501")
    private String detailAddress;
    @Schema(description = "계좌번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "123456789")
    private String accountNumber;
    @Schema(description = "계좌 소유자", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "owner name")
    private String accountOwner;
    @Schema(description = "은행명", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "abc bank")
    private String bankName;
    @Schema(description = "수수료율(%)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5")
    private String commission;
    @Schema(description = "상태 reason", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "reason")
    private String statusReason;
    @Schema(description = "등록 어드민 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer regId;
    @Schema(description = "수정 날짜", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-05-12 12:00:30")
    private String updDt;
}
