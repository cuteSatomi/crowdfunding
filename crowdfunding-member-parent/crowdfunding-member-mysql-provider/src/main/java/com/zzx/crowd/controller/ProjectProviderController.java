package com.zzx.crowd.controller;

import com.zzx.crowd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzx
 * @date 2021-01-19 10:46:36
 */
@RestController
public class ProjectProviderController {

    @Autowired
    private ProjectService projectService;
}
