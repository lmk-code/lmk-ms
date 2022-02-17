package com.lmk.ms.ct.support;

import java.io.Serializable;

/**
 * 配置文件参数
 * @author zhudefu
 * @email laomake@hotmail.com
 */
public class AppProperties implements Serializable {

    /** 类的根路径 */
    private String classPathRoot;

    /** 数据库类型 */
    private String dbType;

    /** 数据库服务器主机 */
    private String dbHost;

    /** 数据库服务器端口 */
    private String dbPort;

    /** 数据库用服务器户名 */
    private String dbUser;

    /** 数据库服务器密码 */
    private String dbPassword;

    /** 数据库名 */
    private String dbName;

    /** 数据库链接地址 */
    private String dbUrl;

    /** 基础包名 */
    private String packageName;

    /** 作者 */
    private String author;

    /** 邮箱 */
    private String email;

    /** 版本号 */
    private String version;

    /** 是否将表名前缀解析为模块 */
    private Boolean parseModule;

    /** 生成多对一 */
    private Boolean manyToOne;

    /** 生成多对多 */
    private Boolean manyToMany;

    /** 生成一对多 */
    private Boolean oneToMany;

    public AppProperties() {
        classPathRoot = FileUtils.getRootClassPath(AppProperties.class);
    }

    public String getClassPathRoot() {
        return classPathRoot;
    }

    public void setClassPathRoot(String classPathRoot) {
        this.classPathRoot = classPathRoot;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getParseModule() {
        return parseModule;
    }

    public void setParseModule(Boolean parseModule) {
        this.parseModule = parseModule;
    }

    public Boolean getManyToOne() {
        return manyToOne;
    }

    public void setManyToOne(Boolean manyToOne) {
        this.manyToOne = manyToOne;
    }

    public Boolean getManyToMany() {
        return manyToMany;
    }

    public void setManyToMany(Boolean manyToMany) {
        this.manyToMany = manyToMany;
    }

    public Boolean getOneToMany() {
        return oneToMany;
    }

    public void setOneToMany(Boolean oneToMany) {
        this.oneToMany = oneToMany;
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + dbHost + '\'' +
                ", port='" + dbPort + '\'' +
                ", user='" + dbUser + '\'' +
                ", password='" + dbPassword + '\'' +
                ", dataBase='" + dbName + '\'' +
                ", url='" + dbUrl + '\'' +
                ", packageName='" + packageName + '\'' +
                ", author='" + author + '\'' +
                ", email='" + email + '\'' +
                ", version='" + version + '\'' +
                ", parseModule=" + parseModule +
                ", manyToOne=" + manyToOne +
                ", manyToMany=" + manyToMany +
                ", oneToMany=" + oneToMany +
                '}';
    }
}
