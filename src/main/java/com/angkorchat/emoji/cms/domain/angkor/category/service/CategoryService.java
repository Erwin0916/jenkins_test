package com.angkorchat.emoji.cms.domain.angkor.category.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.InvalidAdminUserException;
import com.angkorchat.emoji.cms.domain.angkor.category.dao.mapper.CategoryQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.request.*;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryDetail;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryEmojiList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryRegisteredEmojiList;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryEmojiLimitException;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryLimitException;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryNotFoundException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.constant.BannerStatus;
import com.angkorchat.emoji.cms.global.error.DuplicateNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryQueryMapper categoryQueryMapper;

    public List<CategoryList> categoryList(String status, Integer categoryType, String searchKeyword) {
        return categoryQueryMapper.categoryList(status, categoryType, searchKeyword);
    }

    public CategoryDetail categoryDetail(Integer id) {
        return categoryQueryMapper.categoryDetail(id);
    }

    @Transactional
    public void registerCategory(RegisterCategory req) {
        int cnt = categoryQueryMapper.checkCategoryNameDuplicate(null, req.getCategoryName(), req.getCategoryType());

        if (cnt > 0) {
            throw new DuplicateNameException();
        }
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        if(req.getCategoryType().equals(1)) {
            List<CategoryList> categoryList = categoryQueryMapper.categoryList(null, req.getCategoryType(), null);

            if(!categoryList.isEmpty()) {
                throw new CategoryLimitException("[MD Pick] Max : 1");
            }
        }

        req.setRegId(adminId);
        categoryQueryMapper.registerCategory(req);
        categoryQueryMapper.registerCategoryInfo(req.getId(), adminId, req.getStartDt(), req.getEndDt());

        // 이거 송출 종료일 끝나면 배치돌려서 상태 N으로 돌리던가 endDate 수정 가능하게해야한다.(필수는 아님.) -> 앱에서 조회할 때 시간으로 거르고 가져온다.
    }

    // 카테고리 수정(정보, 송출 날짜 등)
    public void modifyCategory(ModifyCategory req) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(req.getId());

//        if (CategoryStatus.LIVE.getValue().equals(categoryDetail.getStatus())) {
//            throw new CategoryAlreadyLiveException();
//        }

        int cnt = categoryQueryMapper.checkCategoryNameDuplicate(req.getId(), req.getCategoryName(), categoryDetail.getCategoryType());

        if (cnt > 0) {
            throw new DuplicateNameException();
        }
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        req.setUpdId(adminId);
        categoryQueryMapper.modifyCategory(req);
    }

    // 카테고리 상태 업데이트(송출처리)
    public void updateCategoryStatus(UpdateCategoryStatus req) {
//        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(req.getId());
//
//
//        if (CategoryStatus.LIVE.getValue().equals(categoryDetail.getStatus())) {
//            throw new CategoryAlreadyLiveException();
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        req.setAdminId(adminId);
        categoryQueryMapper.updateCategoryStatus(req);
    }

    // 카테고리 순서 변경
    @Transactional
    public void reorderCategory(List<ReorderCategory> req) {
        // 순서만 바뀌면 ank_emoji_main_category 의 upd 만 업데이트 한다.
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        for (ReorderCategory category : req) {
            category.setAdminId(adminId);
            categoryQueryMapper.reorderCategory(category);
        }
    }

    // 카테고리 삭제
    public void deleteCategory(Integer id) {
//        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(id);
//
//        if (CategoryStatus.LIVE.getValue().equals(categoryDetail.getStatus())) {
//            throw new CategoryAlreadyLiveException();
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        UpdateCategoryStatus req = new UpdateCategoryStatus();
        req.setAdminId(adminId);
        req.setDelYn("Y");
        req.setStatus(BannerStatus.DELETED.getValue());
        req.setId(id);
        categoryQueryMapper.updateCategoryStatus(req);
    }

    // 카테고리에 등록된 이모지 리스트 조회 - MD PICK 타입의 경우 모든 이모지 등록 가능
    public List<CategoryRegisteredEmojiList> categoryEmojiList(Integer id) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(id);

        if(categoryDetail == null) {
            throw new CategoryNotFoundException();
        }

        String tag = categoryDetail.getCategoryName();
        if(categoryDetail.getCategoryType().equals(1)) {
            tag = "";
        }

        return categoryQueryMapper.categoryEmojiList(id, tag);

    }

    // 카테고리에 등록안된 이모지 리스트 조회 - MD PICK 타입의 경우 모든 이모지 등록 가능
    public Page<CategoryEmojiList> categoryUnregisteredEmojiList(Pageable pageable, Integer id) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(id);

        if(categoryDetail == null) {
            throw new CategoryNotFoundException();
        }

        String tag = categoryDetail.getCategoryName();
        if(categoryDetail.getCategoryType().equals(1)) {
            tag = "";
        }

        return new PageImpl<>(categoryQueryMapper.categoryUnregisteredEmojiList(pageable, id, tag),
                pageable,
                categoryQueryMapper.categoryUnregisteredEmojiListCount(id, tag));
    }

    // 카테고리에 이모지 추가
    @Transactional
    public void registerEmojiToCategory(RegisterEmojiToCategory req) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(req.getCategoryId());
        if(categoryDetail == null) {
            throw new CategoryNotFoundException();
        }
        List<CategoryRegisteredEmojiList> categoryEmojiList = categoryQueryMapper.categoryEmojiList(req.getCategoryId(), categoryDetail.getCategoryName());

        if(categoryEmojiList.size() > 3 || (categoryEmojiList.size() + req.getEmojiIds().size()) > 4) {
            throw new CategoryEmojiLimitException("Max : 4");
        }

        req.setCategoryMainId(categoryDetail.getCategoryMainId());

        categoryQueryMapper.registerEmojiToCategory(req);

        // 로그
        categoryQueryMapper.registerEmojiToCategoryLog(req.getCategoryMainId());
    }

    // 카테고리 내 이모지 순서 변경
    @Transactional
    public void reorderCategoryEmoji(Integer id, List<ReorderCategoryEmoji> req) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(id);
        if(categoryDetail == null) {
            throw new CategoryNotFoundException();
        }

        for (ReorderCategoryEmoji emoji : req) {
            emoji.setId(categoryDetail.getCategoryMainId());
            categoryQueryMapper.reorderCategoryEmoji(emoji);
        }

        // 로그
        categoryQueryMapper.registerEmojiToCategoryLog(categoryDetail.getCategoryMainId());
    }

    // 카테고리에서 이모지 삭제
    @Transactional
    public void deleteEmojiFromCategory(Integer id, List<Integer> emojiIds) {
        CategoryDetail categoryDetail = categoryQueryMapper.categoryDetail(id);
        if(categoryDetail == null) {
            throw new CategoryNotFoundException();
        }

        categoryQueryMapper.deleteEmojiFromCategory(categoryDetail.getCategoryMainId(), emojiIds);

        // 로그
        categoryQueryMapper.registerEmojiToCategoryLog(categoryDetail.getCategoryMainId());
    }
}