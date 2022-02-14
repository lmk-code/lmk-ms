package com.lmk.ms.autoconfigure.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Knife4j层自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties(Knife4jProperties.class)
@ConditionalOnProperty(name = "ms.config.knife4j.enabled", matchIfMissing = true)
public class Knife4jAutoconfigure {

    @Autowired
    Knife4jProperties knife4jProperties;

    /** 多个基础包名的分隔符 */
    private static final String splitor = ";";

    /**
     * 处理多个基础包名
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * 解析多个基础包名
     * @param basePackage
     * @return
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * 处理包名
     * @param input
     * @return
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    @Bean
    public Docket defaultApi2() {

        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(knife4jProperties.getGroupName())
                .select()
                .apis(basePackage(knife4jProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters());
        return docket;
    }

    /**
     * 全局参数
     * @return
     */
    private List<RequestParameter> getGlobalRequestParameters(){
        List<RequestParameter> parameters = new ArrayList<>();

        parameters.add(new RequestParameterBuilder()
                .name("Authorization")
                .description("请求头授权信息")
                .required(false)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());

        parameters.add(new RequestParameterBuilder()
                .name("MS-USER-ID")
                .description("用户ID")
                .required(false)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }

    /**
     * 基本信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(knife4jProperties.getTitle())
                .description(knife4jProperties.getDescription())
                .contact(new Contact(knife4jProperties.getContactName(), knife4jProperties.getContactUrl(), knife4jProperties.getContactEmail()))
                .version(knife4jProperties.getVersion())
                .build();
    }
}
