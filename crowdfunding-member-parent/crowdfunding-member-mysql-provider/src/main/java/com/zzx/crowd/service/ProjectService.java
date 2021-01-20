package com.zzx.crowd.service;

import com.zzx.crowd.entity.vo.ProjectVO;

/**
 * @author zzx
 * @date 2021-01-19 10:47:05
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
}
