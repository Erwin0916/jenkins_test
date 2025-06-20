package com.angkorchat.emoji.cms.domain.angkor.code.service;

import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.CodeList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.LastMinor;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.MajorList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.SimpleCode;
import com.angkorchat.emoji.cms.domain.angkor.code.exception.CodeNameDuplicateException;
import com.angkorchat.emoji.cms.domain.angkor.code.exception.MajorCodeNotFoundException;
import com.angkorchat.emoji.cms.domain.angkor.code.dao.mapper.CodeQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.AddCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateMajorName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeQueryMapper codeQueryMapper;

    public Page<MajorList> majorList(Pageable pageable, String searchKeyword) {
        return new PageImpl<>(codeQueryMapper.majorList(pageable, searchKeyword),
                pageable,
                codeQueryMapper.majorListCount(searchKeyword));
    }

    public Page<CodeList> codeList(Pageable pageable, String searchKeyword, String major) {
        return new PageImpl<>(codeQueryMapper.codeList(pageable, searchKeyword, major),
                pageable,
                codeQueryMapper.codeListCount(searchKeyword, major));
    }

    public List<SimpleCode> simpleCodeList(String major) {
        return codeQueryMapper.simpleCodeList(major);
    }

    public void addMajor(AddCode req) {
//        int cnt = codeQueryMapper.checkDuplicateCodeName(null, req.getMajorName());

        String major = codeQueryMapper.findLastMajor();

        req.setMajor(major);
        req.setMinor("001");
        req.setCode(major + "001");

        codeQueryMapper.addCode(req);
    }

    public void addCode(AddCode req) {
        LastMinor minor = codeQueryMapper.findLastMinor(req.getMajor());

        if (minor.getMajorName() == null) {
            throw new MajorCodeNotFoundException();
        }
        req.setMinor(minor.getMinor());
        req.setMajorName(minor.getMajorName());
        req.setCode(req.getMajor() + minor.getMinor());

        codeQueryMapper.addCode(req);
    }

    public void updateCode(UpdateCode req) {
        codeQueryMapper.updateCode(req);
    }

    public void updateMajorName(UpdateMajorName req) {
        int cnt = codeQueryMapper.checkDuplicateCodeName(req.getMajor(), req.getCodeValue());

        if(cnt > 0) {
            throw new CodeNameDuplicateException();
        }

        codeQueryMapper.updateMajorName(req);
    }

    public void deleteCode(String code) {
        codeQueryMapper.deleteCode(code);
    }
}
