package com.angkorchat.emoji.cms.domain.angkor.code.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.CodeList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.LastMinor;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.MajorList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.SimpleCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.AddCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateMajorName;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeQueryMapper {
    List<MajorList> majorList(@Param("pageable") Pageable pageable,
                              @Param("searchKeyword") String searchKeyword);

    long majorListCount(String searchKeyword);

    List<CodeList> codeList(@Param("pageable") Pageable pageable,
                            @Param("searchKeyword") String searchKeyword,
                            @Param("major") String major);

    long codeListCount(@Param("searchKeyword") String searchKeyword,
                       @Param("major") String major);

    List<SimpleCode> simpleCodeList(String major);

    String findLastMajor();

    LastMinor findLastMinor(@Param("major") String major);

    void addCode(AddCode req);

    int checkDuplicateCodeName(@Param("major") String major,
                               @Param("name") String name);

    void updateCode(UpdateCode req);

    void updateMajorName(UpdateMajorName req);

    void deleteCode(@Param("code") String code);
}
