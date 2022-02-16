package com.lmk.ms.common.auth.vo;

import lombok.Data;

/**
 * Token数据
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/12/09
 */
@Data
public class JwtTokenPayload {
    private String iss;
    private String jti;
    private Long iat;
    private Long exp;
    private String sub;
}
