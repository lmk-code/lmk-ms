package com.lmk.ms.autoconfigure.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MyBatisPlus自动配置
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
@Component
@ConditionalOnClass(DbType.class)
@ConfigurationProperties(prefix = "ms.config.mybatis.plus")
public class MyBatisPlusProperties {

    /** 启用缓存功能 */
    private boolean enabled = true;

    /** 数据库类型，默认为 PostgreSQL */
    private DbType dbType = DbType.POSTGRE_SQL;

    public MyBatisPlusProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }
}
