package com.angkorchat.emoji.cms.domain.angkor.admin.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.ModifyAdminInfo;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.RegisterAdmin;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminQueryMapper {
    List<AdminList> adminList(@Param("pageable") Pageable pageable,
                              @Param("searchKeyword") String searchKeyword);

    List<String> adminUserIdList();

    long adminListCount(String searchKeyword);

    AdminDetail adminDetail(Integer id);

    Integer checkAdminExist(@Param("email") String email,
                            @Param("id") Integer id);

    void resetAdminPassword(@Param("id") Integer id,
                            @Param("pw") String pw);

    void registerAdmin(RegisterAdmin req);

    void modifyAdminInfo(ModifyAdminInfo req);

    void modifyAdminGroup(@Param("id") Integer id,
                          @Param("groupId") Integer groupId);

    void increaseAdminGroupNumber(Integer groupId);

    void decreaseAdminGroupNumber(Integer id);

    int deleteAdmin(@Param("ids") List<Integer> ids);

    List<AdminGroupList> groupList(@Param("pageable") Pageable pageable,
                                   @Param("searchKeyword") String searchKeyword);

    long groupListCount(@Param("searchKeyword") String searchKeyword);

    AdminGroupDetail groupDetail(@Param("id") Integer id);

    List<AdminGroupCode> groupCodeList();

    int checkDuplicateGroupName(@Param("id") Integer id,
                                @Param("name") String name);

    void registerGroup(String name);

    void modifyGroup(@Param("id") Integer id,
                     @Param("name") String name);

    void deleteGroup(@Param("ids") List<Integer> ids);
}
