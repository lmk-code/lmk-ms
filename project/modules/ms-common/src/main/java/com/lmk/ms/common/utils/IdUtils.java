package com.lmk.ms.common.utils;

import com.baomidou.mybatisplus.core.toolkit.Sequence;

import java.util.UUID;

/**
 * ID生成工具类
 * @author LaoMake
 * @email laomake@hotmail.com
 */
public class IdUtils {

    /** 默认的雪花ID生成器 */
    private static Sequence worker = new Sequence(0, 0);

    /**
     * 获得雪花ID
     * @return
     */
    public static long snowflakeId(){
        return worker.nextId();
    }

    /**
     * 获得雪花ID字符串
     * @return
     */
    public static String snowflakeIdByText(){
        return String.valueOf(worker.nextId());
    }

    /**
     * 获得一个随机生成的UUID
     * @return 去除横线后的字符串
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得一个随机生成的UUID
     * @return 包含横线的字符串
     */
    public static String uuidFull(){
        return UUID.randomUUID().toString();
    }

}
