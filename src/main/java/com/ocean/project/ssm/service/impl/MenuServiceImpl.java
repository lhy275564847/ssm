package com.ocean.project.ssm.service.impl;

import com.ocean.project.ssm.dao.MenuDao;
import com.ocean.project.ssm.dto.MenuDto;
import com.ocean.project.ssm.service.MenuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haiyang.li on 2017/8/29.
 */
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Override
    public List<MenuDto> findAllMenu() {
        return menuDao.selectAllDto();
    }
}
