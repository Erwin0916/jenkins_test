package com.angkorchat.emoji.cms.domain.angkor.menu.service;

import com.angkorchat.emoji.cms.domain.angkor.menu.dao.mapper.MenuQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.AddMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.ReorderMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.UpdateMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuDetail;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuList;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.SimpleMenuList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuQueryMapper menuQueryMapper;

    public List<SimpleMenuList> simpleMenuList(String level) {
        List<SimpleMenuList> res = menuQueryMapper.simpleMenuList(level);
        Map<Integer, SimpleMenuList> menuMap = new HashMap<>();
        List<SimpleMenuList> root = new ArrayList<>();

        // 메뉴를 map 에 메뉴 id 를 키값으로 저장
        for (SimpleMenuList menu : res) {
            menuMap.put(menu.getId(), menu);
        }

        // 각 메뉴에 대해 부모를 찾고 해당 부모의 자식 리스트에 추가
        for (SimpleMenuList menu : res) {
            if (menu.getParentId() == 0) {
                root.add(menu);
            } else {
                SimpleMenuList parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.addChildren(menu);
                }
            }
        }

        return root;
    }

    public List<MenuList> topMenuList() {
        List<MenuList> menuList = menuQueryMapper.menuList(null);

        for(MenuList menu : menuList) {

            if(!menu.getLevels().isEmpty()) {
                List<String> levels = List.of(menu.getLevels().get(0).split(","));
                menu.setLevels(levels);
            }
        }

        return menuList;
    }

    public List<MenuList> subMenuList(Integer topId) {
        List<MenuList> menuList = menuQueryMapper.menuList(topId);

        for(MenuList menu : menuList) {

            if(!menu.getLevels().isEmpty()) {
                List<String> levels = List.of(menu.getLevels().get(0).split(","));
                menu.setLevels(levels);
            }
        }

        return menuList;
    }

    public MenuDetail menuDetail(Integer id) {
        MenuDetail menuDetail = menuQueryMapper.menuDetail(id);

        if (menuDetail != null && !menuDetail.getLevels().isEmpty()) {
            List<String> levels = List.of(menuDetail.getLevels().get(0).split(","));
            menuDetail.setLevels(levels);
        }

        return menuDetail;
    }

    @Transactional
    public void addMenu(AddMenu req) {
        menuQueryMapper.addMenu(req);

        for(String level : req.getLevels()) {
            menuQueryMapper.addMenuLevel(req.getId(), level);
        }
    }

    @Transactional
    public void updateMenu(UpdateMenu req) {
        menuQueryMapper.updateMenu(req);
        menuQueryMapper.deleteMenuLevel(req.getId());

        for (String level : req.getLevels()) {
            menuQueryMapper.addMenuLevel(req.getId(), level);
        }
    }

    @Transactional
    public void reorderMenu(List<ReorderMenu> req) {
        for (ReorderMenu menu : req) {
            menuQueryMapper.reorderMenu(menu);
        }
    }

    public void deleteMenu(List<Integer> req) {
        menuQueryMapper.deleteMenu(req);
    }
}
