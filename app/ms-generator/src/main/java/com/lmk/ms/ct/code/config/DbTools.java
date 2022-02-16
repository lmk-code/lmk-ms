package com.lmk.ms.ct.code.config;

/**
 * 数据库操作语句
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/15
 */
public class DbTools {

    /** 数据库类型：MySQL */
    public static final String MySQL = "MySQL";

    /** 数据库类型：PostgreSQL */
    public static final String PostgreSQL = "PostgreSQL";

    /**
     * 判断当前数据库是否为 MySQL
     * @return
     */
    public static boolean isMySQL(){
        return MySQL.equals(AppConfig.appProperties.getDbType());
    }

    /**
     * 判断当前数据库是否为 PostgreSQL
     * @return
     */
    public static boolean isPostgreSQL(){
        return PostgreSQL.equals(AppConfig.appProperties.getDbType());
    }

    /**
     * 获取驱动类
     * @param dbType
     * @return
     */
    public static String getDriverClassName(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.DRIVER_CLASS_NAME;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.DRIVER_CLASS_NAME;
                break;
        }
        return result;
    }

    /**
     * 获取URL连接模板
     * @param dbType
     * @return
     */
    public static String getUrlTemplate(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.TEMPLATE_URL;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.TEMPLATE_URL;
                break;
        }
        return result;
    }

    /**
     * 获取查询表名的查询语句
     * @param dbType
     * @return
     */
    public static String getSqlTableName(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.SQL_TABLE_NAMES;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.SQL_TABLE_NAMES;
                break;
        }
        return result;
    }

    /**
     * 获取统计主键的数目语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getSqlCountPrimaryKey(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.SQL_COUNT_PRIMARY_KEY;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.SQL_COUNT_PRIMARY_KEY;
                break;
        }
        return result;
    }

    /**
     * 获取PostgreSQL查询表主键语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getPostgreSQLPrimaryKey(){
        return PostgreSQLConfig.SQL_TABLE_PRIMARY_KEY;
    }

    /**
     * 获取查询表字段语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getSqlTableFields(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.SQL_TABLE_FIELDS;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.SQL_TABLE_FIELDS;
                break;
        }
        return result;
    }

    /**
     * 获取查询所有外键语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getSqlAllForeignKey(String dbType){
        String result = null;
        switch (dbType){
            case MySQL:
                result = MySQLConfig.SQL_ALL_FOREIGN_KEY;
                break;
            case PostgreSQL:
                result = PostgreSQLConfig.SQL_ALL_FOREIGN_KEY;
                break;
        }
        return result;
    }

    /**
     * 获取查询多对多中间表的Key语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getMySQLManyToMany(){
        return MySQLConfig.SQL_MANY_TO_MANY;
    }

    /**
     * 获取查询多对多中间表的Key语句
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    public static String getPostgreManyToMany(){
        return PostgreSQLConfig.SQL_TABLE_FOREIGN_KEY;
    }

    /**
     * MySQL配置
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    class MySQLConfig {

        /** 数据库驱动名称 */
        public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

        /** 数据库链接地址模板 */
        public static final String TEMPLATE_URL = "jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";

        /** 查询所有表 */
        public static final String SQL_TABLE_NAMES = "SELECT table_name, table_comment FROM information_schema.tables WHERE table_schema = ?";

        /** 统计主键的数目 */
        public static final String SQL_COUNT_PRIMARY_KEY = "SELECT COUNT(k.column_name) FROM information_schema.key_column_usage k WHERE k.constraint_name = 'PRIMARY' AND k.table_schema = ? AND k.table_name = ?";

        /** 查询表的所有字段 */
        public static final String SQL_TABLE_FIELDS = "SHOW FULL FIELDS FROM ";

        /** 查询所有外键关联 */
        public static final String SQL_ALL_FOREIGN_KEY = "SELECT table_name AS source_table, column_name AS source_column, referenced_table_name AS target_table, referenced_column_name AS target_column FROM information_schema.key_column_usage WHERE referenced_table_name IS NOT NULL AND table_schema = ?";

        /** 查询多对多中间表的Key */
        public static final String SQL_MANY_TO_MANY = "SELECT DISTINCT k.REFERENCED_TABLE_NAME AS table_name, k.COLUMN_NAME AS column_name FROM information_schema.KEY_COLUMN_USAGE k WHERE k.CONSTRAINT_SCHEMA = ? AND k.REFERENCED_TABLE_NAME IS NOT NULL AND k.TABLE_NAME = ?";
    }

    /**
     * PostgreSQL配置
     * @author laomake@hotmail.com
     * @version 1.0
     * @date 2022/02/15
     */
    class PostgreSQLConfig {

        /** 数据库驱动名称 */
        public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

        /** 数据库链接地址模板 */
        public static final String TEMPLATE_URL = "jdbc:postgresql://%s:%s/%s?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";

        /** 查询所有表 */
        public static final String SQL_TABLE_NAMES = "SELECT relname AS table_name, cast( obj_description ( relfilenode, 'pg_class' ) AS VARCHAR ) AS table_comment FROM pg_class c WHERE relkind = 'r' AND relname NOT LIKE 'pg_%' AND relname NOT LIKE 'sql_%' ORDER BY relname";

        /** 统计主键的数目 */
        public static final String SQL_COUNT_PRIMARY_KEY = "SELECT count(0) FROM pg_constraint INNER JOIN pg_class ON pg_constraint.conrelid = pg_class.oid INNER JOIN pg_attribute ON pg_attribute.attrelid = pg_class.oid AND pg_attribute.attnum = pg_constraint.conkey [1] INNER JOIN pg_type ON pg_type.oid = pg_attribute.atttypid WHERE pg_class.relname = ? AND pg_constraint.contype = 'p'";

        /** 查询表的所有字段 */
        public static final String SQL_TABLE_FIELDS = "SELECT col_description (a.attrelid, a.attnum ) AS COMMENT, format_type ( a.atttypid, a.atttypmod ) AS type, a.attname AS NAME, a.attnotnull AS notnull FROM pg_class AS c, pg_attribute AS a WHERE c.relname = ? AND a.attrelid = c.oid AND a.attnum > 0";

        /** 查询表的主键 */
        public static final String SQL_TABLE_PRIMARY_KEY = "SELECT pg_constraint.conname AS pk_name, pg_attribute.attname AS colname, pg_type.typname AS typename FROM pg_constraint INNER JOIN pg_class ON pg_constraint.conrelid = pg_class.oid INNER JOIN pg_attribute ON pg_attribute.attrelid = pg_class.oid AND pg_attribute.attnum = pg_constraint.conkey [1] INNER JOIN pg_type ON pg_type.oid = pg_attribute.atttypid WHERE pg_class.relname = ? AND pg_constraint.contype = 'p'";

        /** 查询所有外键关联 */
        public static final String SQL_ALL_FOREIGN_KEY = "SELECT distinct tc.constraint_name, tc.table_name, kcu.column_name, ccu.table_name AS foreign_table_name, ccu.column_name AS foreign_column_name, tc.is_deferrable, tc.initially_deferred FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'FOREIGN KEY'";

        /** 查询表的外键 */
        public static final String SQL_TABLE_FOREIGN_KEY = "SELECT distinct tc.constraint_name, tc.table_name, kcu.column_name, ccu.table_name AS foreign_table_name, ccu.column_name AS foreign_column_name, tc.is_deferrable, tc.initially_deferred FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name=?";

    }
}
