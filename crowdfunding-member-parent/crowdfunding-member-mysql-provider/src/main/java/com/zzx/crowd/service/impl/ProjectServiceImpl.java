package com.zzx.crowd.service.impl;

import com.zzx.crowd.entity.po.MemberConfirmInfoPO;
import com.zzx.crowd.entity.po.MemberLaunchInfoPO;
import com.zzx.crowd.entity.po.ProjectPO;
import com.zzx.crowd.entity.po.ReturnPO;
import com.zzx.crowd.entity.vo.MemberConfirmInfoVO;
import com.zzx.crowd.entity.vo.MemberLauchInfoVO;
import com.zzx.crowd.entity.vo.ProjectVO;
import com.zzx.crowd.entity.vo.ReturnVO;
import com.zzx.crowd.mapper.*;
import com.zzx.crowd.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-19 10:47:10
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 保存projectPO对象，先创建一个空的projectPO对象，再从projectVO中复制属性
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO, projectPO);

        // 把memberId设置到projectPO中
        projectPO.setMemberid(memberId);
        // 生成创建时间
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createdate);
        // 设置status为0表示即将开始
        projectPO.setStatus(0);

        // 执行保存，因为是自增的主键，所以需要在mapper文件中配置插入后获取project的id
        projectPOMapper.insertSelective(projectPO);
        // 保存成功获取projectId
        Integer projectId = projectPO.getId();

        // 保存项目、分类关联信息
        // 获取typeIdList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectId);

        // 保存项目、标签关联信息
        // 获取tagIdList
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectId);

        // 保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectId, detailPicturePathList);

        // 保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        // 保存回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<ReturnPO>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectId);

        // 保存确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }
}
