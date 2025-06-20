package com.angkorchat.emoji.cms.domain.studio.sales.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.AuthenticationFailException;
import com.angkorchat.emoji.cms.domain.studio.sales.dto.response.StudioSalesStatus;
import com.angkorchat.emoji.cms.domain.studio.sales.dao.mapper.StudioSalesQueryMapper;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudioSalesService {
    private final StudioSalesQueryMapper studioSalesQueryMapper;

    public StudioSalesStatus studioSalesStatus() {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if (id == null) {
            throw new AuthenticationFailException();
        }

        return studioSalesQueryMapper.studioSalesStatus(id);
    }

    public byte[] getCSProfileQrCode() {
        String url = "https://angkorlife.onelink.me/MIbi?u=nyancat";
        byte[] imageByte;

        try {
            imageByte = CommonUtils.generateQRCodeImage(url, 1000, 1000);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return imageByte;
    }
}
