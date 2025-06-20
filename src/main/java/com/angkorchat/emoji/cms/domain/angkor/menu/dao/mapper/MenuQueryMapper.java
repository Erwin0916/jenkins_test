package com.angkorchat.emoji.cms.domain.angkor.menu.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.AddMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.ReorderMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.UpdateMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuDetail;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuList;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.SimpleMenuList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuQueryMapper {
    List<SimpleMenuList> simpleMenuList(@Param("level") String level);

    List<MenuList> menuList(@Param("topId") Integer topId);

    MenuDetail menuDetail(@Param("id") Integer id);

    void addMenu(AddMenu req);

    void addMenuLevel(@Param("id") Integer id,
                      @Param("level") String level);

    void updateMenu(UpdateMenu req);

    void deleteMenuLevel(@Param("id") Integer id);

    void reorderMenu(ReorderMenu req);

    void deleteMenu(@Param("ids") List<Integer> ids);
}
