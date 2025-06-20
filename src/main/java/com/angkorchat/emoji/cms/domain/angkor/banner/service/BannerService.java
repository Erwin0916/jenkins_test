package com.angkorchat.emoji.cms.domain.angkor.banner.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.InvalidAdminUserException;
import com.angkorchat.emoji.cms.domain.angkor.banner.dao.mapper.BannerQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ModifyBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.RegisterBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ReorderBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.UpdateBannerStatus;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerDetail;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerList;

import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.constant.BannerStatus;
import com.angkorchat.emoji.cms.global.error.DuplicateNameException;
import com.angkorchat.emoji.cms.infra.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerQueryMapper bannerQueryMapper;
    private final S3Service s3Service;

    public List<BannerList> bannerList(String status, String searchKeyword) {
        return bannerQueryMapper.bannerList(status, searchKeyword);
    }

    public BannerDetail bannerDetail(Integer id) {
        return bannerQueryMapper.bannerDetail(id);
    }

    @Transactional
    public void registerBanner(RegisterBanner req) {
        int cnt = bannerQueryMapper.checkBannerNameDuplicate(null, req.getName());

        if (cnt > 0) {
            throw new DuplicateNameException();
        }
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        req.setRegId(adminId);
        req.setStatus(BannerStatus.REGISTERED.getValue());
        bannerQueryMapper.registerBanner(req);

        String url = s3Service.save(req.getImageFile(), "emoji/banner/" + req.getId());
        req.setImageUrl(url);

        bannerQueryMapper.uploadBannerImageUrl(req.getId(), url);
    }

    // 배너 순서 변경
    @Transactional
    public void reorderBanner(List<ReorderBanner> req) {
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        for (ReorderBanner banner : req) {
            banner.setAdminId(adminId);
            bannerQueryMapper.reorderBanner(banner);
        }
    }

    // 배너 수정
    public void modifyBanner(ModifyBanner req) {
//        BannerDetail bannerDetail = bannerQueryMapper.bannerDetail(req.getId());

//        if(BannerStatus.LIVE.getValue().equals(bannerDetail.getStatus())) {
//            throw new BannerAlreadyLiveException();
//        }

        int cnt = bannerQueryMapper.checkBannerNameDuplicate(req.getId(), req.getName());

        if (cnt > 0) {
            throw new DuplicateNameException();
        }
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        req.setUpdId(adminId);

        if (req.getImageFile() != null) {
            String url = s3Service.save(req.getImageFile(), "emoji/banner/" + req.getId());
            req.setImageUrl(url);
        }

        bannerQueryMapper.modifyBanner(req);

        if (req.getImageFile() != null) {
            s3Service.delete(req.getBannerImageUrl());
        }
    }

    // 배너 삭제
    public void deleteBanner(Integer id) {
//        BannerDetail bannerDetail = bannerQueryMapper.bannerDetail(id);

//        if(BannerStatus.LIVE.getValue().equals(bannerDetail.getStatus())) {
//            throw new BannerAlreadyLiveException();
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        UpdateBannerStatus req = new UpdateBannerStatus();
        req.setAdminId(adminId);
        req.setStatus(BannerStatus.DELETED.getValue());
        req.setId(id);
        bannerQueryMapper.updateBannerStatus(req);
    }

    public void updateBannerStatus(UpdateBannerStatus req) {
//        BannerDetail bannerDetail = bannerQueryMapper.bannerDetail(req.getId());
//
//        if(BannerStatus.LIVE.getValue().equals(bannerDetail.getStatus())) {
//            throw new BannerAlreadyLiveException();
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        req.setAdminId(adminId);
        bannerQueryMapper.updateBannerStatus(req);
    }
}