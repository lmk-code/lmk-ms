package com.lmk.ms.common.mvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * K8S心跳检测服务
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/11
 */
@Api(tags = "数据服务")
@RestController
@ConditionalOnMissingBean(K8SController.class)
public class K8SController {

    @ApiOperation(value = "服务是否可用", hidden = true)
    @GetMapping("/heartbeat")
    public String heartbeat()  {
        return "SUCCESS";
    }

}

