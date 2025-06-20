package com.angkorchat.emoji.cms.domain.angkor.banner.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ModifyBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.RegisterBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ReorderBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.UpdateBannerStatus;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerDetail;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerQueryMapper {
    List<BannerList> bannerList(@Param("status") String status,
                                @Param("searchKeyword") String searchKeyword);

    long bannerListCount(@Param("status") String status,
                         @Param("searchKeyword") String searchKeyword);

    BannerDetail bannerDetail(Integer id);

    int checkBannerNameDuplicate(@Param("id") Integer id,
                                 @Param("name") String name);

    void registerBanner(RegisterBanner req);

    void uploadBannerImageUrl(@Param("id") Integer id,
                              @Param("imageUrl") String imageUrl);

    void reorderBanner(ReorderBanner req);

    void modifyBanner(ModifyBanner req);

    void updateBannerStatus(UpdateBannerStatus req);
}
