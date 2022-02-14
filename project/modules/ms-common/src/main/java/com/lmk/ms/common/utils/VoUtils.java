package com.lmk.ms.common.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * VO 转换工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/30
 */
public class VoUtils {

    /**
     * 将实体类对象列表转换为VO对象列表
     * @param vc
     * @param eList
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E, V> List<V> getVos(Class<V> vc, List<E> eList){
        if(eList == null || eList.size() == 0){
            return null;
        }
        List<V> vs = new ArrayList<>(eList.size());
        eList.forEach(e -> vs.add(getVo(vc, e)));
        return vs;
    }

    /**
     * 将实体类对象转换为VO对象
     * @param vc        VO类型
     * @param e         实体类对象
     * @param <E>       实体类型
     * @param <V>       VO类型
     * @return
     */
    public static <E, V> V getVo(Class<V> vc, E e){
        if(vc == null || e == null)
            return null;

        V vo = null;
        try {
            Class<E> entityClass = (Class<E>) e.getClass();
            // 提取实体类的字段
            Field[] entityFields = entityClass.getDeclaredFields();
            HashMap<String, Field> fieldMap = new HashMap<>();
            for (Field f : entityFields){
                fieldMap.put(f.getName(), f);
            }

            // 创建VO对象
            vo = vc.newInstance();

            // 提取VO类的字段
            Field[] voFields = vc.getDeclaredFields();

            // 为字段赋值
            for (Field voField : voFields){
                Field eFiled = fieldMap.get(voField.getName());
                if(eFiled != null){
                    eFiled.setAccessible(true);
                    voField.setAccessible(true);

                    // 两方数据类型相同
                    if(voField.getGenericType().equals(eFiled.getGenericType())){
                        voField.set(vo, eFiled.get(e));
                    }else{
                        switch (voField.getGenericType().toString()){
                            case "class java.lang.String":
                                // 处理日期格式化
                                if(eFiled.getGenericType().toString().equals("class java.util.Date")){
                                    String pattern = null;

                                    JsonFormat jsonFormat = eFiled.getAnnotation(JsonFormat.class);
                                    if(jsonFormat != null){
                                        pattern = jsonFormat.pattern();
                                    }else{
                                        pattern = "yyyy-MM-dd HH:mm:ss";
                                    }

                                    voField.set(vo, DateUtils.format((Date) eFiled.get(e), pattern));
                                }else{
                                    voField.set(vo, String.valueOf(eFiled.get(e)));
                                }
                                break;
                            case "class java.lang.Integer":
                            case "int":
                                voField.set(vo, Integer.valueOf(eFiled.get(e).toString()));
                                break;
                            case "class java.lang.Long":
                            case "long":
                                voField.set(vo, Long.valueOf(eFiled.get(e).toString()));
                                break;
                            case "class java.lang.Double":
                            case "class java.lang.Float":
                            case "float":
                            case "double":
                                voField.set(vo, Double.valueOf(eFiled.get(e).toString()));
                                break;
                            case "class java.lang.Boolean":
                            case "boolean":
                                voField.set(vo, Boolean.valueOf(eFiled.get(e).toString()));
                                break;
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return vo;
    }

}
