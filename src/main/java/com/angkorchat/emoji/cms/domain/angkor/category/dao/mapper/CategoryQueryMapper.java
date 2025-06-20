package com.angkorchat.emoji.cms.domain.angkor.category.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ModifyBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.RegisterBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ReorderBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.UpdateBannerStatus;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerDetail;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.request.*;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryDetail;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryEmojiList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryRegisteredEmojiList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryQueryMapper {
    List<CategoryList> categoryList(@Param("status") String status,
                                    @Param("categoryType") Integer categoryType,
                                    @Param("searchKeyword") String searchKeyword);

//    long categoryListCount(@Param("status") String status,
//                                    @Param("categoryType") Integer categoryType,
//                                    @Param("searchKeyword") String searchKeyword);
//
    CategoryDetail categoryDetail(Integer id);

    int checkCategoryNameDuplicate(@Param("id") Integer id,
                                   @Param("name") String name,
                                   @Param("categoryType") Integer categoryType);

    void registerCategory(RegisterCategory req);

    void registerCategoryInfo(@Param("id") Integer id,
                              @Param("adminId") Integer adminId,
                              @Param("startDt") String startDt,
                              @Param("endDt") String endDt);

    void modifyCategory(ModifyCategory req);

    void reorderCategory(ReorderCategory req);

    void updateCategoryStatus(UpdateCategoryStatus req);

    List<CategoryRegisteredEmojiList> categoryEmojiList(@Param("id") Integer id,
                                                        @Param("tag") String tag);

    List<CategoryEmojiList> categoryUnregisteredEmojiList(@Param("pageable") Pageable pageable,
                                                          @Param("id") Integer id,
                                                          @Param("tag") String tag);

    long categoryUnregisteredEmojiListCount(@Param("id") Integer id,
                                            @Param("tag") String tag);


    void registerEmojiToCategory(RegisterEmojiToCategory req);

    void registerEmojiToCategoryLog(Integer id);

    void reorderCategoryEmoji(ReorderCategoryEmoji req);

    void deleteEmojiFromCategory(@Param("id") Integer id,
                                 @Param("emojiIds") List<Integer> emojiIds);
}
