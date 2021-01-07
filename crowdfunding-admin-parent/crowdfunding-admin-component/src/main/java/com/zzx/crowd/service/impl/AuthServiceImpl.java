package com.zzx.crowd.service.impl;

import com.zzx.crowd.mapper.AuthMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zzx
 * @date 2021-01-07 16:54:44
 */
@Service
public class AuthServiceImpl {

    @Resource
    private AuthMapper authMapper;
}
