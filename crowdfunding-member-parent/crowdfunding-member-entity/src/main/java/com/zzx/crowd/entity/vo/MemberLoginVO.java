package com.zzx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzx
 * @date 2021-01-15 14:29:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO {

    private Integer id;
    private String username;
    private String email;
}
