package com.lmk.ms.common.config.bean;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.FactoryBean;

/**
 * MybatisPlus分页插件
 * @author laomake@hotmail.com
 * @version 1.0
 */
public class MybatisPlusInterceptorFactoryBean implements FactoryBean<MybatisPlusInterceptor> {
    @Override
    public MybatisPlusInterceptor getObject() throws Exception {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public Class<?> getObjectType() {
        return MybatisPlusInterceptor.class;
    }
}
