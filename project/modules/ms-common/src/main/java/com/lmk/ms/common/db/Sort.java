package com.lmk.ms.common.db;

import java.util.List;

/**
 * 排序条件
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public class Sort {
    /** 字段名称 */
    private String column;

    /** 排序方式 */
    private String type;

    public Sort() {
    }

    public Sort(String column, String type) {
        this.column = column;
        this.type = type;
    }

    public static Sort asc(String column){
        return new Sort(column, "ASC");
    }

    public static Sort desc(String column){
        return new Sort(column, "DESC");
    }

    public static String build(List<Sort> sortList) {
        if(sortList == null || sortList.size() == 0){
            return null;
        }

        String separator = ", ";
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        sortList.forEach(s ->
                sb.append(s.getColumn())
                        .append(" ").append(s.getType()).append(separator)
        );

        String sort = sb.toString();
        if(sort.endsWith(separator)){
            sort = sort.substring(0, sort.lastIndexOf(separator));
        }
        return sort;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
