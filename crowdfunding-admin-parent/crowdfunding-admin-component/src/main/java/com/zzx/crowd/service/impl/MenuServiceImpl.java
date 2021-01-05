package com.zzx.crowd.service.impl;

import com.zzx.crowd.entity.Menu;
import com.zzx.crowd.entity.MenuExample;
import com.zzx.crowd.mapper.MenuMapper;
import com.zzx.crowd.service.api.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-05 21:51
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }
}
