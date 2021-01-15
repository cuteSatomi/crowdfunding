package com.zzx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzx
 * @date 2021-01-15 10:56:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String code;
}
