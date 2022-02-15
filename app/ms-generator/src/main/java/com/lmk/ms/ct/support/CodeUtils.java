package com.lmk.ms.ct.support;

import java.io.File;
import java.util.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lmk.ms.ct.MainFrame;
import com.lmk.ms.ct.code.config.DbTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import com.lmk.ms.ct.code.bean.*;
import com.lmk.ms.ct.code.config.AppConfig;
import com.lmk.ms.ct.code.config.CodeConfig;

/**
 * 代码工具类
 * @author zhudefu
 * @version 1.0
 */
public class CodeUtils {

	/** 日志记录器 */
	private static Logger log = LoggerFactory.getLogger(CodeUtils.class);
	
	/** 安全的随机数生成器 */
	private static SecureRandom random = new SecureRandom();

	/** 生成一对多映射 */
	public static Boolean parseTablePrefix = true;

	/** 生成多对一映射 */
	public static Boolean buildManyToOne = false;

	/** 生成多对多映射 */
	public static Boolean buildManyToMany = true;

	/** 生成一对多映射 */
	public static Boolean buildOneToMany = false;

	/**
	 * 查询所有实体名
	 * @param frame
	 * @param dataBaseName
	 * @return
	 */
	public static Map<String, String> getTableNames(MainFrame frame, final String dataBaseName){
		log.info("查询表结构...");
		frame.outputText("查询表结构...");

		Map<String, String> tableNames = new LinkedHashMap<>();
		
		Connection conn = DbUtils.getConn();
		if(conn != null){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = DbTools.getSqlTableName(AppConfig.appProperties.getDbType());
				ps = conn.prepareStatement(sql);
				if(ps != null){
					if(DbTools.isMySQL()){
						ps.setString(1, dataBaseName);
					}

					rs = ps.executeQuery();
					String tableName, entityComment;
					while(rs.next()){
						tableName = rs.getString("table_name");
						entityComment = rs.getString("table_comment");
						if(entityComment == null)
							entityComment = "";//避免FreeMarker报null错误
						if(!TableUtils.isMiddleTable(dataBaseName, tableName))
							tableNames.put(tableName, entityComment);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtils.close(rs, ps, conn);
			}
		}
		
		return tableNames;
	}

	/**
	 * 解析实体类
	 * @param frame
	 * @param tableName
	 * @param tableComment
	 * @param modules
	 * @return
	 */
	public static Entity parseEntity(MainFrame frame, String tableName, String tableComment, Set<String> modules) {
		String text = "分析：" + tableName;
		log.info(text);
		frame.outputText(text);

		Entity entity = new Entity();
		entity.setSerialVersionUID(String.valueOf(random.nextLong()));
		entity.setTableName(tableName);
		entity.setEntityComment(tableComment);
		
		if(parseTablePrefix)
			entity.setModuleName(StringUtils.substringBefore(tableName, "_"));
		else
			entity.setModuleName("");
		
		modules.add(entity.getModuleName());
		String entityName = convertToEntityName(tableName, parseTablePrefix);
		if(StringUtils.isBlank(entityName)){
			return null;
		}

		entity.setEntityName(entityName);
		entity.setEntityNameLower(Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1));
		
		List<DbField> fields = null;
		switch (AppConfig.appProperties.getDbType()){
			case DbTools.MySQL:
				fields = TableUtils.getMySQLAllFields(tableName);
				break;
			case DbTools.PostgreSQL:
				fields = TableUtils.getPostgreSQLAllFields(tableName);
				break;
		}

		if(DbTools.isPostgreSQL()){
			TableUtils.checkForeignKey(tableName, fields);
		}

		DbField primaryField = TableUtils.removePrimaryField(tableName, fields);

		entity.setFields(fields);
		entity.setPrimaryKey(primaryField);
		
		//判断是否有日期类型
		boolean hasDateType = false;
		for(DbField field : fields){
			if(field.getFieldDataType() == FieldDataType.Date){
				hasDateType = true;
				break;
			}
		}

		entity.setHasDateType(hasDateType);
		return entity;
	}

	/**
	 * 解析外键关联
	 * @param frame
	 * @param entitys
	 * @param dataBaseName
	 */
	public static void parseForeignKey(MainFrame frame, List<Entity> entitys, String dataBaseName){
		log.info("关联所有外键...");
		frame.outputText("关联所有外键...");
		Map<String, Entity> entityMap = new HashMap<String, Entity>();
		for(Entity entity : entitys){
			if(entity == null){
				continue;
			}
			entityMap.put(entity.getTableName(), entity);
		}


		List<ForeignKey> foreignKeys = new ArrayList<>();
		Connection conn = DbUtils.getConn();
		if(conn != null){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = DbTools.getSqlAllForeignKey(AppConfig.appProperties.getDbType());
				ps = conn.prepareStatement(sql);
				if(ps != null){
					if(DbTools.isMySQL()){
						ps.setString(1, dataBaseName);
						rs = ps.executeQuery();
						while(rs.next()){
							foreignKeys.add(new ForeignKey(
									rs.getString("source_table"),
									rs.getString("source_column"),
									rs.getString("target_table"),
									rs.getString("target_column")));
						}
					}else if(DbTools.isPostgreSQL()){
						rs = ps.executeQuery();
						while(rs.next()){
							foreignKeys.add(new ForeignKey(
									rs.getString("table_name"),
									rs.getString("column_name"),
									rs.getString("foreign_table_name"),
									rs.getString("foreign_column_name")));
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtils.close(rs, ps, conn);
			}

			Entity sourceEntity;
			Entity targetEntity;
			ManyToOne mto = null;
			Set<String> mtmTableSet = new HashSet<String>();
			for(ForeignKey foreignKey : foreignKeys){
				sourceEntity = entityMap.get(foreignKey.getSourceTableName());
				if(sourceEntity != null){//排除多对多关联
					targetEntity = entityMap.get(foreignKey.getTargetTableName());
					if(targetEntity != null){
						//生成一对多
						if(buildOneToMany){
							targetEntity.getChildren().add(sourceEntity);
							targetEntity.getChildMap().put(sourceEntity.getEntityName(), foreignKey.getSourceColumnName());
						}

						//生成多对一
						if(buildManyToOne){
							mto = new ManyToOne(targetEntity.getTableName(), foreignKey.getSourceColumnName(), targetEntity.getModuleName(), targetEntity.getEntityName(), targetEntity.getEntityNameLower());
							mto.setEntityComment(targetEntity.getEntityComment());
							sourceEntity.getManyToOnes().add(mto);
						}
					}
				}else
					mtmTableSet.add(foreignKey.getSourceTableName());
			}

			//生成多对多
			if(buildManyToMany){
				Iterator<String> iterator = mtmTableSet.iterator();
				while (iterator.hasNext()){
					switch (AppConfig.appProperties.getDbType()){
						case DbTools.MySQL:
							setMySQLManyToMany(entityMap, dataBaseName, iterator.next());
							break;
						case DbTools.PostgreSQL:
							setPostgreManyToMany(entityMap, dataBaseName, iterator.next());
							break;
					}
				}
			}
		}
	}

	/**
	 * 生成代码
	 * @param frame
	 * @param entity
	 * @param data
	 * @return
	 */
	public static void makeFile(MainFrame frame, Entity entity, Map<String, Object> data){
		if(entity == null){
			return;
		}

		String entityName = entity.getEntityName();
		data.put("entityName", entityName);
		data.put("entityNameLower", Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1));

		//生成Java代码
		String javaFolderPath = AppConfig.javaRootFolder + File.separator + entity.getModuleName() + File.separator;
		String entityFile = javaFolderPath + CodeConfig.SOURCE_NAME_ENTITY + File.separator + entityName + ".java";
		String daoFile = javaFolderPath + CodeConfig.SOURCE_NAME_DAO + File.separator + entityName + "Dao.java";
		String serviceFile = javaFolderPath + CodeConfig.SOURCE_NAME_SERVICE + File.separator + entityName + "Service.java";
		String serviceImplFile = javaFolderPath + CodeConfig.SOURCE_NAME_SERVICE + File.separator + "impl" + File.separator + entityName + "ServiceImpl.java";
		String controllerFile = javaFolderPath + CodeConfig.SOURCE_NAME_WEB + File.separator + entityName + "Controller.java";

		FreeMarkerUtils.makeFile(data, "entity.ftl", entityFile);
		FreeMarkerUtils.makeFile(data, "dao.ftl", daoFile);
		FreeMarkerUtils.makeFile(data, "service.ftl", serviceFile);
		FreeMarkerUtils.makeFile(data, "serviceImpl.ftl", serviceImplFile);
		FreeMarkerUtils.makeFile(data, "controller.ftl", controllerFile);

		//生成Mapper
		String mapperFolderPath = AppConfig.mapperRootFolder + File.separator + entity.getModuleName() + File.separator;
		FileUtils.makeSureFolderExits(mapperFolderPath);

		switch (AppConfig.appProperties.getDbType()){
			case DbTools.MySQL:
				FreeMarkerUtils.makeFile(data, "mapper_mysql.ftl", mapperFolderPath + entity.getEntityName() + "Dao.xml");
				break;
			case DbTools.PostgreSQL:
				FreeMarkerUtils.makeFile(data, "mapper_postgresql.ftl", mapperFolderPath + entity.getEntityName() + "Dao.xml");
				break;
		}

		String str = "输出：" + entity.getModuleName() + "." +entityName;
		log.info(str);
		frame.outputText(str);
	}

	/**
	 * MySQL设置多对多关联
	 * @param entityMap
	 * @param dataBaseName
	 * @param joinTableName
	 */
	private static void setMySQLManyToMany(Map<String, Entity> entityMap, String dataBaseName, String joinTableName){

		String[][] tableArray = new String[2][2];
		Connection conn = DbUtils.getConn();
		if(conn != null){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(DbTools.getMySQLManyToMany());
				if(ps != null){
					ps.setString(1, dataBaseName);
					ps.setString(2, joinTableName);
					rs = ps.executeQuery();
					int i = 0;
					while(rs.next()){
						tableArray[i][0] = rs.getString("table_name");
						tableArray[i][1] = rs.getString("column_name");
						i++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtils.close(rs, ps, conn);
			}

			ManyToMany mtm;
			Entity targetEntity;
			Entity sourceEntity = entityMap.get(tableArray[0][0]);
			if(sourceEntity != null){
				targetEntity = entityMap.get(tableArray[1][0]);
				if(targetEntity != null){
					//开始正向关联
					mtm = new ManyToMany(
							dataBaseName,
							joinTableName,
							targetEntity.getTableName(),
							targetEntity.getModuleName(),
							targetEntity.getEntityName(),
							targetEntity.getEntityNameLower(),
							targetEntity.getEntityComment(),
							tableArray[0][1],
							tableArray[1][1]);
					sourceEntity.getMtms().add(mtm);

					//开始反向关联
					mtm = new ManyToMany(
							dataBaseName,
							joinTableName,
							sourceEntity.getTableName(),
							sourceEntity.getModuleName(),
							sourceEntity.getEntityName(),
							sourceEntity.getEntityNameLower(),
							sourceEntity.getEntityComment(),
							tableArray[1][1],
							tableArray[0][1]);
					targetEntity.getMtms().add(mtm);
				}
			}
		}
	}

	/**
	 * PostgreSQL设置多对多关联
	 * @param entityMap
	 * @param joinTableName
	 */
	private static void setPostgreManyToMany(Map<String, Entity> entityMap, String dataBaseName, String joinTableName){
		String[][] tableArray = new String[2][2];
		Connection conn = DbUtils.getConn();
		if(conn != null){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(DbTools.getPostgreManyToMany());
				if(ps != null){
					ps.setString(1, joinTableName);
					rs = ps.executeQuery();
					int i = 0;
					while(rs.next()){
						tableArray[i][0] = rs.getString("foreign_table_name");
						tableArray[i][1] = rs.getString("column_name");
						i++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtils.close(rs, ps, conn);
			}
			
			ManyToMany mtm;
			Entity targetEntity;
			Entity sourceEntity = entityMap.get(tableArray[0][0]);
			if(sourceEntity != null){
				targetEntity = entityMap.get(tableArray[1][0]);
				if(targetEntity != null){
					//开始正向关联
					mtm = new ManyToMany(
							dataBaseName,
							joinTableName,
							targetEntity.getTableName(),
							targetEntity.getModuleName(),
							targetEntity.getEntityName(),
							targetEntity.getEntityNameLower(),
							targetEntity.getEntityComment(),
							tableArray[0][1], 
							tableArray[1][1]);
					sourceEntity.getMtms().add(mtm);
					
					//开始反向关联
					mtm = new ManyToMany(
							dataBaseName,
							joinTableName,
							sourceEntity.getTableName(),
							sourceEntity.getModuleName(),
							sourceEntity.getEntityName(),
							sourceEntity.getEntityNameLower(),
							sourceEntity.getEntityComment(),
							tableArray[1][1], 
							tableArray[0][1]);
					targetEntity.getMtms().add(mtm);
				}
			}
		}
		
	}

	/**
	 * 表名转换成实体名
	 * @param fullTableName
	 * @param parseTablePrefix
	 * @return
	 */
	private static String convertToEntityName(String fullTableName, Boolean parseTablePrefix){
		String tableName = fullTableName;
		if(parseTablePrefix)
			tableName = StringUtils.substringAfter(fullTableName, "_");
		
		StringBuffer entityName = new StringBuffer();
		String[] names = tableName.split("_");
		for(String name : names){
			if(StringUtils.isNotBlank(name)){
				entityName.append(Character.toUpperCase(name.charAt(0)) + name.substring(1));
			}
		}
		return entityName.toString();
	}
}
