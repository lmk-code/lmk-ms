package com.lmk.ms.gateway.filter;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import com.lmk.ms.common.auth.config.JwtProperties;
import com.lmk.ms.common.auth.utils.JwtUtils;
import com.lmk.ms.common.auth.vo.JwtUser;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.api.ResponseResult;
import com.lmk.ms.common.utils.GatewayUtils;

/**
 * 授权过滤器
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/16
 */
@Slf4j
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeConfig> {

    JwtProperties jwtProperties;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public AuthorizeGatewayFilterFactory() {
        super(AuthorizeConfig.class);
        this.jwtProperties = new JwtProperties();
    }

    public AuthorizeGatewayFilterFactory(JwtProperties jwtProperties) {
        super(AuthorizeConfig.class);
        this.jwtProperties = jwtProperties;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled", "auth", "permitAll");
    }

    @Override
    public GatewayFilter apply(AuthorizeConfig config) {
        return (exchange, chain) -> {

            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String requestPath = request.getPath().pathWithinApplication().value();

            // 记录请求信息
            log.info("{} {} {}", request.getMethodValue(), request.getURI(), request.getHeaders());

            if(log.isDebugEnabled()) {
                log.debug("QueryParams: {}", request.getQueryParams());
                log.debug("Headers: {}", request.getHeaders());
            }

            // 不鉴权，则直接放行
            if(!config.isAuth()){
                return chain.filter(exchange);
            }

            // 放行白名单列表
            if (config.getPermitAllList().stream().anyMatch(pattern -> antPathMatcher.match(pattern, requestPath))) {
                return chain.filter(exchange);
            }

            // 开始校验用户权限
            try {
                String accessToken = GatewayUtils.getAccessToken(jwtProperties, request);
                JwtUser user = JwtUtils.getJwtUserByAccessToken(accessToken, jwtProperties.getKeyPair().getPublic());
                if(user != null){
                    // TODO 可以继续增加权限验证

                    HttpHeaders headers = HttpHeaders.writableHttpHeaders(request.getHeaders());
                    headers.set(jwtProperties.getHeaderUserId(), user.getId().toString());
                    return chain.filter(exchange);
                }
            }catch (Exception e) {
                log.error("鉴权失败", e);
            }
            return GatewayUtils.getErrorResponseMono(response, HttpStatus.UNAUTHORIZED, ResponseResult.error(StatusEnum.UNAUTHORIZED_NONE));
        };
    }
}
