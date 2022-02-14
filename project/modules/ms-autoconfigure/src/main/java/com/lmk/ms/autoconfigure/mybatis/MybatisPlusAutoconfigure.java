package com.lmk.ms.autoconfigure.mybatis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * MybatisPlus自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Configuration
@EnableConfigurationProperties(MyBatisPlusProperties.class)
@ConditionalOnClass(MybatisPlusInterceptor.class)
@ConditionalOnProperty(name = "ms.config.mybatis.plus.enabled", matchIfMissing = true)
public class MybatisPlusAutoconfigure {

    /**
     * 添加 MybatisPlus 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyBatisPlusProperties myBatisPlusProperties){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(myBatisPlusProperties.getDbType()));
        return interceptor;
    }

}
