package com.zzx.crowd.service.impl;

import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.entity.AdminExample;
import com.zzx.crowd.exception.LoginFailedException;
import com.zzx.crowd.mapper.AdminMapper;
import com.zzx.crowd.service.api.AdminService;
import com.zzx.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zzx
 * @date 2020-12-25 15:46:04
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> listAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 构建AdminExample，写入查询条件查询
        AdminExample adminExample = new AdminExample();
        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 添加查询条件 LoginAcct 等于传入的loginAcct
        criteria.andLoginAcctEqualTo(loginAcct);
        // 使用通用mapper查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 判断查询出的对象是否为空
        // list是null或者长度为0都直接抛出异常
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 查出的list长度大于1，说明查询的结果也是有问题，抛出运行时异常
        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGINACCT_NOT_UNIQUE);
        }

        // 如果查出的对象为null，也抛出登录失败异常
        Admin admin = list.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 查询的对象不为null，则验证密码是否正确
        // userPswdDB表示从库中查询出的密码，userPswdForm表示从表单获取的密码加密后的结果
        String userPswdDB = admin.getUserPswd();
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 对两个字符串进行比较
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 结果一致返回Admin对象
        return admin;
    }


}
