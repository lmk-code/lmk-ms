package com.lmk.ms.ct.code.config;

import java.io.File;
import java.util.*;

import com.lmk.ms.ct.support.AppProperties;
import com.lmk.ms.ct.support.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应用配置
 */
public class AppConfig {

    /** 日志记录器 */
    private static Logger log = LoggerFactory.getLogger(AppConfig.class);

    /** Java源码输出目录 */
    public static String javaRootFolder;

    /** Mapper文件输出目录 */
    public static String mapperRootFolder;

    public static AppProperties appProperties;


    public static void init(AppProperties ap){
        appProperties = ap;

        String driverClass = DbTools.getDriverClassName(ap.getDbType());
        DbUtils.init(ap.getDbUser(), ap.getDbPassword(), ap.getDbUrl(), driverClass);

        // 初始化代码生成规则
        CodeUtils.parseTablePrefix = appProperties.getParseModule();
        CodeUtils.buildManyToOne = appProperties.getManyToOne();
        CodeUtils.buildManyToMany = appProperties.getManyToMany();
        CodeUtils.buildOneToMany = appProperties.getOneToMany();
    }


    /**
     * 解析模块
     * @param modules
     */
    public static Map<String, Object> parseMoudle(Set<String> modules){
        // 初始化代码文件目录
        String[] packageNameItem = appProperties.getPackageName().split("\\.");

        // Java源码目录
        StringBuffer sb = new StringBuffer()
                .append(appProperties.getClassPathRoot()).append(File.separator)
                .append(CodeConfig.SOURCE_NAME).append(File.separator)
                .append(CodeConfig.SOURCE_NAME_JAVA).append(File.separator);

        for(String nameItem : packageNameItem)
            sb.append(nameItem).append(File.separator);

        javaRootFolder = sb.toString();
        FileUtils.makeSureFolderExits(javaRootFolder);
        FileUtils.clearFolder(javaRootFolder);

        // 映射文件源码目录
        sb = new StringBuffer()
                .append(appProperties.getClassPathRoot()).append(File.separator)
                .append(CodeConfig.SOURCE_NAME).append(File.separator)
                .append(CodeConfig.SOURCE_NAME_MAPPER).append(File.separator);

        mapperRootFolder = sb.toString();
        FileUtils.makeSureFolderExits(mapperRootFolder);
        FileUtils.clearFolder(mapperRootFolder);

        // 初始化各子模块的源码目录
        String javaFolderPath;
        String entityFolder, daoFolder, serviceFolder, serviceImplFolder, webFolder;
        for(String moduleName : modules){

            javaFolderPath = javaRootFolder + moduleName + File.separator;
            entityFolder = javaFolderPath + CodeConfig.SOURCE_NAME_ENTITY + File.separator;
            daoFolder = javaFolderPath + CodeConfig.SOURCE_NAME_DAO + File.separator;
            serviceFolder = javaFolderPath + CodeConfig.SOURCE_NAME_SERVICE + File.separator;
            serviceImplFolder = serviceFolder + "impl" + File.separator;
            webFolder = javaFolderPath + CodeConfig.SOURCE_NAME_WEB + File.separator;

            FileUtils.makeSureFolderExits(entityFolder);
            FileUtils.makeSureFolderExits(daoFolder);
            FileUtils.makeSureFolderExits(serviceFolder);
            FileUtils.makeSureFolderExits(serviceImplFolder);
            FileUtils.makeSureFolderExits(webFolder);
        }

        // 构建代码注释等信息
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("author", appProperties.getAuthor());
        data.put("email", appProperties.getEmail());
        data.put("version", appProperties.getVersion());
        data.put("date", DateUtils.format(new Date(), DateUtils.FORMAT_LONG));
        data.put("packageName", appProperties.getPackageName());

        return data;
    }

}
