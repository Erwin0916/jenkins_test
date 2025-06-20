package com.angkorchat.emoji.cms.domain.studio.term.dao.mapper;

import com.angkorchat.emoji.cms.domain.studio.term.dto.StudioUserTermList;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.StudioTermList;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.TermsAgreeInfo;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.TermsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudioTermQueryMapper {
    List<StudioTermList> studioTermList(@Param("angkorId") String angkorId, @Param("language") String language);
    TermsInfo getTermsInfo(@Param("termsId") Integer termsId);
    TermsAgreeInfo getTermsAgreeInfo(@Param("angkorId") String angkorId, @Param("termsId") Integer termsId);
    void insertTermsAgree(@Param("angkorId") String angkorId, @Param("termsId") Integer termsId, @Param("version") String version, @Param("agreeYn") String agreeYn);
    void updateTermsAgree(@Param("angkorId") String angkorId, @Param("termsId") Integer termsId, @Param("version") String version, @Param("agreeYn") String agreeYn);
    void insertTermsAgreeLog(@Param("id") Integer id);

    List<StudioUserTermList> getStudioTermList(@Param("ids") List<Integer> ids);
}
