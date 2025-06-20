package com.angkorchat.emoji.cms.domain.angkor.admin.service;

import com.angkorchat.emoji.cms.domain.angkor.admin.dao.mapper.AdminQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.ModifyAdminInfo;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.RegisterAdmin;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.ResetPassword;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.*;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.AdminEmailDuplicateException;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupMemberExistException;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupNameDuplicateException;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupNotFoundException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import com.angkorchat.emoji.cms.global.util.Encrypt;
import com.angkorchat.emoji.cms.infra.mail.event.ResetPasswordEmailSendEvent;
import com.angkorchat.emoji.cms.infra.mail.event.SignInAdminEmailSendEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final AdminQueryMapper adminQueryMapper;
    private final ApplicationEventPublisher eventPublisher;

    public Page<AdminList> adminList(Pageable pageable, String searchKeyword) {
        return new PageImpl<>(adminQueryMapper.adminList(pageable, searchKeyword),
                pageable,
                adminQueryMapper.adminListCount(searchKeyword));
    }

    public AdminDetail adminDetail(Integer id) {
        return adminQueryMapper.adminDetail(id);
    }

    @Transactional
    public void registerAdmin(RegisterAdmin req) {
        try {
            int cnt = adminQueryMapper.checkAdminExist(req.getEmail(), null);

            if(cnt > 0) {
                throw new AdminEmailDuplicateException();
            }
            //관리자 그룹 체크 필요

            req.setRegisterId(SecurityUtils.getLoginUserNo());

            // 임시 비밀번호 생성
            String pw = CommonUtils.getRandomPassword(16);

            // 관리자 계정 생성
            req.setPassword(Encrypt.encryptSHA256(pw));
            adminQueryMapper.registerAdmin(req);

            // 관리자 계정 생성 시 그룹 멤버 수 증가
            adminQueryMapper.increaseAdminGroupNumber(req.getGroupId());

            // 관리자 계정 메일로 계정 생성 완료 메일 발송
            if (!ObjectUtils.isEmpty(req.getEmail())) {
                eventPublisher.publishEvent(new SignInAdminEmailSendEvent(req.getEmail(), req.getAdminId(), pw));
            }
        } catch (Exception ex) {
            log.error("\n## PRINT STACK TRACE: ", ex);
            log.error("\n## EXCEPTION MESSAGE: {}", ex.getMessage());

            throw new RuntimeException();
        }
    }

    public void modifyAdminInfo(Integer id, ModifyAdminInfo req) {
        int cnt = adminQueryMapper.checkAdminExist(req.getEmail(), id);

        if(cnt > 0) {
            throw new AdminEmailDuplicateException();
        }

        req.setId(id);
        // req.setRegisterId(SecurityUtils.getLoginUserAdminId()); // updateDt 이력 필요 시

        adminQueryMapper.modifyAdminInfo(req);
    }

    @Transactional
    public void modifyAdminGroup(Integer id, Integer groupId) {
        // 관리자 계정 수정 시 이전 그룹 멤버 수 감소
        adminQueryMapper.decreaseAdminGroupNumber(id);

        // 관리자 계정 소속 그룹 변경
        adminQueryMapper.modifyAdminGroup(id, groupId);

        // 변경 그룹 멤버 수 증가
        adminQueryMapper.increaseAdminGroupNumber(groupId);
    }

    @Transactional
    public void deleteAdmin(List<Integer> ids) {
        // 관리자 계정 삭제
        int cnt = adminQueryMapper.deleteAdmin(ids);

        if(cnt > 0) {
            for (Integer id : ids) {
                // 관리자 계정 삭제 전 소속 그룹 멤버 수 감소
                adminQueryMapper.decreaseAdminGroupNumber(id);
            }
        }
    }

    public void resetAdminPassword(Integer id, ResetPassword req) {
        try {
            // 임시 비밀번호 생성
            String pw = CommonUtils.getRandomPassword(16);

            // 임시 비밀번호 저장
            adminQueryMapper.resetAdminPassword(id, Encrypt.encryptSHA256(pw));

            // 임시 비밀번호 메일 발송
            eventPublisher.publishEvent(new ResetPasswordEmailSendEvent(req.getEmail(), req.getAdminId(), pw));
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public Page<AdminGroupList> adminGroupList(Pageable pageable, String searchKeyword) {
        return new PageImpl<>(adminQueryMapper.groupList(pageable, searchKeyword),
                pageable,
                adminQueryMapper.groupListCount(searchKeyword));
    }

    public AdminGroupDetail adminGroupDetail(Integer id) {
        return adminQueryMapper.groupDetail(id);
    }

    public List<AdminGroupCode> adminGroupCodeList() {
        return adminQueryMapper.groupCodeList();
    }

    public void registerGroup(String name) {
        int cnt = adminQueryMapper.checkDuplicateGroupName(null, name);
        if(cnt > 0) {
            throw new GroupNameDuplicateException();
        }

        adminQueryMapper.registerGroup(name);
    }

    public void modifyGroup(Integer id, String name) {
        int cnt = adminQueryMapper.checkDuplicateGroupName(id, name);
        if(cnt > 0) {
            throw new GroupNameDuplicateException();
        }

        adminQueryMapper.modifyGroup(id, name);
    }

    public void deleteGroup(List<Integer> ids) {
        for (Integer id : ids) {
            AdminGroupDetail groupDetail = adminQueryMapper.groupDetail(id);
            if(groupDetail == null) {
                throw new GroupNotFoundException();
            }
            if(groupDetail.getMembers() > 0) {
                throw new GroupMemberExistException();
            }
        }

        adminQueryMapper.deleteGroup(ids);
    }
}
