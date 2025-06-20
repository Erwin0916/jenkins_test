package com.angkorchat.emoji.cms.domain.studio.sales.dao.mapper;

import com.angkorchat.emoji.cms.domain.studio.sales.dto.response.SalesByEmoji;
import com.angkorchat.emoji.cms.domain.studio.sales.dto.response.StudioSalesStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudioSalesQueryMapper {

    StudioSalesStatus studioSalesStatus(@Param("id") Integer id);
}
