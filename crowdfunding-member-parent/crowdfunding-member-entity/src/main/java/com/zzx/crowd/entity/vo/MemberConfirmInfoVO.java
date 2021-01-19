package com.zzx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzx
 * @date 2021-01-19 10:59:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 易付宝账号
    private String paynum;
    // 法人身份证号
    private String cardnum;
}
