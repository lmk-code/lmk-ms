package com.lmk.ms.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * Bean工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
public class BeanUtils {

    /**
     * 获取父类型的第一个泛型
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<T> getSuperClassFirstGenerics(Class<?> clazz){
        return getSuperClassGenerics(clazz, 0);
    }

    /**
     * 获取父类型的指定索引位置的泛型
     * @param clazz
     * @param index
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenerics(Class<?> clazz, int index){
        Class<T> type = null;
        // 获取父类的类型
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        // 获取实体类的类型
        // 获取泛型数组
        Type[] types = parameterizedType.getActualTypeArguments();
        if(types != null && types.length > 0){
            type = (Class<T>) types[index];
        }
        return type;
    }

    /**
     * 根据注解类型获取属性值
     * @param obj
     * @param annotationClass
     * @return
     */
    public static Object findFieldValueByAnnotation(Object obj, Class<java.lang.annotation.Annotation> annotationClass){
        Object value = null;
        Class<?> clazz = obj.getClass();
        Field field = findFieldByAnnotation(clazz, annotationClass);
        if(field != null){
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            log.error("获取属性值失败失败：{}, {}", clazz, annotationClass);
        }
        return value;
    }

    /**
     * 按注解类型搜索属性字段
     * @param clazz
     * @param annotationClass
     * @return
     */
    public static Field findFieldByAnnotation(Class<?> clazz, Class<java.lang.annotation.Annotation> annotationClass){
        Field field = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field fieldItem : fields){
            if(fieldItem.getAnnotation(annotationClass) != null){
                field = fieldItem;
                break;
            }
        }
        return field;
    }
}
