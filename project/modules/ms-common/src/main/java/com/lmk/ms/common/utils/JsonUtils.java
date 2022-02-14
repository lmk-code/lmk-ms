package com.lmk.ms.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public class JsonUtils {

    /** 通用的解析器 */
    public static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 获取JSON字符串
     * @param obj
     * @return
     */
    public static String toJSON(Object obj){
        if(obj == null)
            return null;

        String json = null;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 解析对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String json, Class<T> type){
        if(StringUtils.isBlank(json) || type == null)
            return null;

        Object obj = null;
        try {
            obj = MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 将JSON文本解析为Map对象
     * @param json  文本
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String json){
        if(StringUtils.isBlank(json))
            return null;

        Map<String, Object> obj = null;
        try {
            obj = MAPPER.readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 解析列表
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseList(String json, Class<T> type) {
        if(StringUtils.isBlank(json) || type == null)
            return null;

        JavaType listType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, type);
        Object obj = null;
        try {
            obj = MAPPER.readValue(json, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (List<T>) obj;
    }

    /**
     * 将JSON文本解析为Map对象的列表
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseMapList(String json){
        if(StringUtils.isBlank(json))
            return null;

        JavaType listType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, HashMap.class);
        List<Map<String, Object>> obj = null;
        try {
            obj = MAPPER.readValue(json, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
