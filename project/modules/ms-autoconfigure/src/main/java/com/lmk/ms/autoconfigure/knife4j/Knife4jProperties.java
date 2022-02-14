package com.lmk.ms.autoconfigure.knife4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Knife4j配置参数
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/14
 */
@Component
@ConfigurationProperties(prefix = "ms.config.knife4j")
public class Knife4jProperties {

    /** 启用自定义Knife4j功能 */
    private boolean enabled = true;

    /** 分组名称 */
    private String groupName = "MS";

    /** 基础包名 */
    private String basePackage = "com.lmk";

    /** 页面标题 */
    private String title = "Mirco Service Framework";

    /** 版本号 */
    private String version = "1.0";

    /** 页面副标题 */
    private String description = "服务接口文档及测试";

    /** 介绍页面 */
    private String url = "https://www.laomake.com";

    /** 联系人姓名 */
    private String contactName = "laomake";

    /** 联系人网址 */
    private String contactUrl = "https://www.laomake.com";

    /** 联系人邮箱 */
    private String contactEmail = "laomake@hotmail.com";

    public Knife4jProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
