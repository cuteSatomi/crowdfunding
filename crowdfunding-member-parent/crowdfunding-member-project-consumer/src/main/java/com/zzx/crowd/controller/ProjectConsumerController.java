package com.zzx.crowd.controller;

import com.zzx.crowd.MySQLRemoteService;
import com.zzx.crowd.config.OssProperties;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.vo.MemberConfirmInfoVO;
import com.zzx.crowd.entity.vo.MemberLoginVO;
import com.zzx.crowd.entity.vo.ProjectVO;
import com.zzx.crowd.entity.vo.ReturnVO;
import com.zzx.crowd.util.CrowdUtil;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-19 15:06:38
 */
@Controller
public class ProjectConsumerController {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO) {
        // 从session中取回临时存储的projectVO对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMP_PROJECT_MISSING);
        }
        // 将表单提交的确认信息存储到projectVO对象中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 从session中获取当前登录的member，需要获取他的id
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = memberLoginVO.getId();
        // 调用远程方法保存projectVO对象
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberId);

        // 判断远程的保存操作是否成功
        String result = saveResultEntity.getResult();
        if(ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }

        // 将临时的ProjectVO对象从Session域移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);

        // 如果远程保存成功则跳转到最终完成页面
        return "redirect:http://localhost:8888/project/create/success";
    }

    /**
     * 提交保存所有回报信息
     *
     * @param returnVO
     * @param session  需要从session中取回projectVO对象
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMP_PROJECT_MISSING);
            }
            // 得到returnVO对象集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            // 判断returnVOList是否为null
            if (returnVOList == null) {
                // 如果returnVOList为null则现对其进行初始化
                returnVOList = new ArrayList<ReturnVO>();
                // 将初始化完成的returnVOList塞回projectVO
                projectVO.setReturnVOList(returnVOList);
            }
            // 将收集了表单数据的returnVO对象存入集合
            returnVOList.add(returnVO);

            // 将projectVO重新设置回session
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 回报中要上传的图片
     *
     * @param returnPicture
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(
            @RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());
        return uploadReturnPicResultEntity;
    }

    /**
     * @param projectVO         接收除了上传图片之外的数据
     * @param headerPicture     接收上传的头图
     * @param detailPictureList 接收上传详情图片
     * @param session           将收集了一部分数据的projectVO对象存入session域
     * @param modelMap
     * @return
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList,
                                       HttpSession session, ModelMap modelMap) throws IOException {

        // 一、完成头图上传
        // 1.获取当前headerPicture对象是否为空
        boolean headerPictureIsEmpty = headerPicture.isEmpty();

        if (headerPictureIsEmpty) {
            // 2.如果没有上传头图则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";

        }
        // 3.如果用户确实上传了有内容的文件，则执行上传
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadHeaderPicResultEntity.getResult();

        // 4.判断头图是否上传成功
        if (ResultEntity.SUCCESS.equals(result)) {
            // 5.如果成功则从返回的数据中获取图片访问路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();
            // 6.存入ProjectVO对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            // 7.如果上传失败则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";

        }

        // 二、上传详情图片
        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();

        // 2.检查detailPictureList是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }

        // 3.遍历detailPictureList集合
        for (MultipartFile detailPicture : detailPictureList) {
            // 4.当前detailPicture是否为空
            if (detailPicture.isEmpty()) {
                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }

            // 6.执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());

            // 7.检查上传结果
            String detailUploadResult = detailUploadResultEntity.getResult();

            if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                String detailPicturePath = detailUploadResultEntity.getData();
                // 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(detailPicturePath);
            } else {
                // 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }

        }

        // 10.将存放了详情图片访问路径的集合存入ProjectVO中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 三、后续操作
        // 1.将ProjectVO对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT, projectVO);

        // 2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://localhost:8888/project/return/info/page";
    }
}
