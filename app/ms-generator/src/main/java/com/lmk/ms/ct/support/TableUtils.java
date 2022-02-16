package com.lmk.ms.ct.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.lmk.ms.ct.code.bean.*;
import com.lmk.ms.ct.code.config.AppConfig;
import com.lmk.ms.ct.code.config.DbTools;
import org.apache.commons.lang3.StringUtils;
import com.lmk.ms.ct.code.config.CodeConfig;

/**
 * 数据库表工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/05
 */
public class TableUtils {

    /**
     * 判断是否为多对多
     * @return
     */
    public static boolean isMiddleTable(String dataBaseName, String tableName){
        if(DbTools.isMySQL()){
            String sql = DbTools.getSqlCountPrimaryKey(AppConfig.appProperties.getDbType());
            long count = DbUtils.queryLong(sql, dataBaseName, tableName);
            return count != 1l;
        }

        boolean yes = false;
        // 只有两个字段，且都是外键的表
        List<DbField> fieldList = getPostgreSQLAllFields(tableName);

        if(fieldList.size() == 2){
            List<ForeignKey> foreignKeyList = parseForeignKeys(tableName);
            if(foreignKeyList.size() == 2){
                int count = 0;
                for (DbField field : fieldList){
                    for (ForeignKey fk : foreignKeyList){
                        if(field.getTableFieldName().equals(fk.getSourceColumnName())){
                            count++;
                        }
                    }
                }
                if(count == 2){
                    yes = true;
                }
            }
        }
        return yes;
    }

    public static List<DbField> getMySQLAllFields(String tableName){
        List<DbField> fields = new ArrayList<DbField>();
        DbField field = null;

        Connection conn = DbUtils.getConn();
        if(conn != null){
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String sql = DbTools.getSqlTableFields(AppConfig.appProperties.getDbType());
                ps = conn.prepareStatement(sql + tableName);
                if(ps != null){
                    rs = ps.executeQuery();
                    while(rs.next()){
                        String str = rs.getString("Field");
                        field = new DbField();
                        field.setTableFieldName(str);
                        String filedName = convertToFieldName(str);
                        field.setFieldName(filedName);
                        field.setFieldNameFirstUpper(Character.toUpperCase(filedName.charAt(0)) + filedName.substring(1));

                        str = rs.getString("Type");
                        if (str.contains("varchar")) {
                            field.setFieldDataType(FieldDataType.String);
                            field.setLength(Integer.valueOf(StringUtils.substringBetween(str, "(", ")")));
                        } else if (str.contains("text")) {
                            field.setFieldDataType(FieldDataType.String);
                            field.setLength(65535);
                        } else if ("bigint".equals(str) || str.contains("bigint(20)")) {
                            field.setFieldDataType(FieldDataType.Long);
                        } else if ("int".equals(str) || str.contains("int(11)")) {
                            field.setFieldDataType(FieldDataType.Integer);
                        } else if (str.contains("smallint(6)")) {
                            field.setFieldDataType(FieldDataType.Short);
                        } else if (str.contains("tinyint(4)")) {
                            field.setFieldDataType(FieldDataType.Byte);
                        } else if (str.contains("tinyint(1)")) {
                            field.setFieldDataType(FieldDataType.Boolean);
                        } else if (str.contains("double")) {
                            field.setFieldDataType(FieldDataType.Double);
                        } else if (str.contains("decimal")) {
                            field.setFieldDataType(FieldDataType.Double);
                        } else if (str.contains("float")) {
                            field.setFieldDataType(FieldDataType.Float);
                        } else if (str.equals("datetime") || str.contains("timestamp")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(19);
                        }else if (str.equals("date")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(10);
                        }else if (str.equals("time")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(8);
                        }

                        str = rs.getString("Null");
                        field.setNotNull(str.equals("NO"));

                        field.setDefaultValue(rs.getString("Default"));
                        field.setComment(rs.getString("Comment"));

                        str = rs.getString("Key");
                        if(StringUtils.isBlank(str)){
                            field.setKeyType(KeyType.NotKey);
                            fields.add(field);
                        }else{
                            switch (str){
                                case "UNI":
                                    // 注意：通过PowerDesigner生成SQL时，需要将候补键（Alternate Key）的创建方式改为：Outside
                                    // 否则生成的唯一索引键的类型将为 MUL，将会与外键类型混淆，导致字段解析失误
                                    field.setKeyType(KeyType.UniqueKey);
                                    fields.add(field);
                                    break;
                                case "PRI":
                                    field.setKeyType(KeyType.PrimaryKey);
                                    fields.add(field);
                                    break;
                                case "MUL":
                                    field.setKeyType(KeyType.ForeignKey);
                                    break;
                                default:
                                    field.setKeyType(KeyType.NotKey);
                                    fields.add(field);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(rs, ps, conn);
            }
        }

        return fields;
    }

    public static List<DbField> getPostgreSQLAllFields(String tableName){
        List<DbField> fields = new ArrayList<DbField>();
        DbField field = null;

        Connection conn = DbUtils.getConn();
        if(conn != null){
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String sql = DbTools.getSqlTableFields(AppConfig.appProperties.getDbType());
                ps = conn.prepareStatement(sql);
                if(ps != null){
                    ps.setString(1, tableName);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        String str = rs.getString("name");
                        field = new DbField();
                        field.setTableFieldName(str);
                        String filedName = convertToFieldName(str);
                        field.setFieldName(filedName);
                        field.setFieldNameFirstUpper(Character.toUpperCase(filedName.charAt(0)) + filedName.substring(1));

                        str = rs.getString("type");
                        if(str == null){
                            continue;
                        }
                        if (str.contains("character")) {
                            field.setFieldDataType(FieldDataType.String);

                            try {
                                field.setLength(Integer.valueOf(StringUtils.substringBetween(str, "(", ")")));
                            } catch (NumberFormatException e) {}
                        } else if (str.contains("text")) {
                            field.setFieldDataType(FieldDataType.String);
                            field.setLength(65535);
                        } else if ("bigint".equals(str) || str.contains("bigint(20)")) {
                            field.setFieldDataType(FieldDataType.Long);
                        } else if ("int".equals(str) || str.contains("int(11)")) {
                            field.setFieldDataType(FieldDataType.Integer);
                        } else if (str.contains("smallint(6)")) {
                            field.setFieldDataType(FieldDataType.Short);
                        } else if (str.contains("tinyint(4)")) {
                            field.setFieldDataType(FieldDataType.Byte);
                        } else if (str.contains("tinyint(1)")) {
                            field.setFieldDataType(FieldDataType.Boolean);
                        } else if (str.contains("numeric")) {
                            field.setFieldDataType(FieldDataType.Double);
                        } else if (str.contains("decimal")) {
                            field.setFieldDataType(FieldDataType.Double);
                        } else if (str.contains("float")) {
                            field.setFieldDataType(FieldDataType.Float);
                        } else if (str.equals("datetime") || str.contains("timestamp")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(19);
                        }else if (str.equals("date")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(10);
                        }else if (str.equals("time")) {
                            field.setFieldDataType(FieldDataType.Date);
                            field.setLength(8);
                        }

                        str = rs.getString("notnull");
                        field.setNotNull(str.equals("t"));

                        str = rs.getString("comment");
                        if(str == null){
                            str = "";
                        }
                        field.setComment(str);

                        fields.add(field);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(rs, ps, conn);
            }
        }

        return fields;
    }


    /**
     * 去除主键
     * @param tableName
     * @param fields
     */
    public static DbField removePrimaryField(String tableName, List<DbField> fields) {
        DbField primaryField = null;

        switch (AppConfig.appProperties.getDbType()){
            case DbTools.MySQL:
                for(DbField field : fields){
                    if(field.getKeyType() == KeyType.PrimaryKey){
                        primaryField = field;
                        fields.remove(field);
                        break;
                    }
                }
                break;
            case DbTools.PostgreSQL:
                String primaryKey = parsePostgreSQLPrimaryKey(tableName);
                for (DbField field : fields){
                    if (field.getTableFieldName().equals(primaryKey)){
                        primaryField = field;
                        fields.remove(primaryField);
                        break;
                    }
                }
        }
        return primaryField;
    }

    /**
     * 解析主键
     * @param tableName
     * @return
     */
    public static String parsePostgreSQLPrimaryKey(String tableName) {
        String primaryField = null;
        Connection conn = DbUtils.getConn();
        if (conn != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(DbTools.getPostgreSQLPrimaryKey());
                if (ps != null) {
                    ps.setString(1, tableName);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        primaryField = rs.getString("colname");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(rs, ps, conn);
            }
        }
        return primaryField;
    }

    /**
     * 解析外键关联
     * @param tableName
     */
    public static List<ForeignKey> parseForeignKeys(String tableName){
        List<ForeignKey> foreignKeys = new ArrayList<>();
        Connection conn = DbUtils.getConn();
        if(conn != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(DbTools.getPostgreManyToMany());
                if (ps != null) {
                    ps.setString(1, tableName);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        foreignKeys.add(new ForeignKey(
                                rs.getString("table_name"),
                                rs.getString("column_name"),
                                rs.getString("foreign_table_name"),
                                rs.getString("foreign_column_name")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DbUtils.close(rs, ps, conn);
            }
        }
        return foreignKeys;
    }

    /**
     * 将字段名转换为属性名
     * @param fieldName
     * @return
     */
    private static String convertToFieldName(String fieldName){
        StringBuffer entityName = new StringBuffer();
        String[] names = fieldName.split("_");

        boolean firstName = true;
        for(String name : names){
            if(firstName){
                entityName.append(name);
                firstName = false;
            }else{
                entityName.append(Character.toUpperCase(name.charAt(0)) + name.substring(1));
            }
        }

        return entityName.toString();
    }

    /**
     * 校验字段中的外键
     * @param tableName
     * @param fields
     */
    public static void checkForeignKey(String tableName, List<DbField> fields) {
        List<ForeignKey> foreignKeys = parseForeignKeys(tableName);
        Set<String> keys = new HashSet<>(foreignKeys.size());
        foreignKeys.forEach(fk -> keys.add(fk.getSourceColumnName()));
        fields.forEach(field -> {
            if(keys.contains(field.getTableFieldName())){
                field.setKeyType(KeyType.ForeignKey);
            }
        });
    }
}
