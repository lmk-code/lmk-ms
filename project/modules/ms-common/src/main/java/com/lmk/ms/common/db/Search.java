package com.lmk.ms.common.db;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 查询过滤器
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Search {
	
	/** 查询字段 */
	private String name;
	
	/** 判断值 */
	private Object value;

	/** 起始值用于 between */
	private Object start;

	/** 终止值用于 between */
	private Object end;

	/** 值列表，用于多值查询 */
	private List<Object> values;
	
	/** 操作符 */
	private Operator operator;

	/**
	 * 快速创建等值查询
	 * @param name
	 * @param value
	 * @return
	 */
	public static Search eq(String name, Object value){
		return new Search().setName(name).setValue(value).setOperator(Operator.eq);
	}

	/**
	 * 快速创建不等值查询
	 * @param name
	 * @param value
	 * @return
	 */
	public static Search notEq(String name, Object value){
		return new Search().setName(name).setValue(value).setOperator(Operator.notEq);
	}

	/**
	 * 快速创建小于条件查询
	 * @param name
	 * @param value
	 * @return
	 */
	public static Search lt(String name, Object value){
		return new Search().setName(name).setValue(value).setOperator(Operator.lt);
	}

	/**
	 * 快速创建大于条件查询
	 * @param name
	 * @param value
	 * @return
	 */
	public static Search gt(String name, Object value){
		return new Search().setName(name).setValue(value).setOperator(Operator.gt);
	}

	/**
	 * 范围查询
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public static Search between(String name, Object start, Object end){
		return new Search().setName(name).setStart(start).setEnd(end).setOperator(Operator.between);
	}

	/**
	 * IN 查询
	 * @param name
	 * @param values	List列表
	 * @return
	 */
	public static Search in(String name, Object values){
		List<Object> valueList = (List<Object>) values;
		return new Search().setName(name).setValues(valueList).setOperator(Operator.in);
	}

	/**
	 * 快速创建模糊查询
	 * @param name
	 * @param value
	 * @return
	 */
	public static Search like(String name, Object value){
		return new Search().setName(name).setValue(value).setOperator(Operator.like);
	}

	public static String build(List<Search> searchList) {
		if(searchList == null || searchList.size() == 0){
			return null;
		}

		String separator = " and ";
		StringBuilder sb = new StringBuilder();
		searchList.forEach(s -> {
				switch (s.getOperator()){
					case like:
						sb.append(s.getName())
								.append(" ").append(s.getOperator().getText()).append(" '%")
								.append(s.getValue()).append("%'").append(separator);
						break;
					case in:
						if(s.getValues() != null && s.getValues().size() > 0){
							sb.append(s.getName()).append(" ").append(s.getOperator().getText()).append(" (");
							for (Object v : s.getValues()){
								if(v instanceof String){
									sb.append("'").append(v).append("', ");
								}else{
									sb.append(v).append(", ");
								}
							}
							sb.delete(sb.length() -2, sb.length());
							sb.append(")").append(separator);
						}
						break;
					default:
						sb.append(s.getName())
								.append(" ").append(s.getOperator().getText()).append(" '")
								.append(s.getValue()).append("'").append(separator);
				}
			}
		);

		String search = sb.toString();
		if(search.endsWith(separator)){
			search = search.substring(0, search.lastIndexOf(separator));
		}
		return search;
	}
}
