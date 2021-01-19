package com.zzx.crowd.service.impl;

import com.zzx.crowd.mapper.ProjectPOMapper;
import com.zzx.crowd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zzx
 * @date 2021-01-19 10:47:10
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;
}
