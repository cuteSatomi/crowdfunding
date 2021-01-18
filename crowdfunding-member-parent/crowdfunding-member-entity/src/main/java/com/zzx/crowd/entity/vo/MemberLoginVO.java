package com.zzx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzx
 * @date 2021-01-15 14:29:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String email;
}
