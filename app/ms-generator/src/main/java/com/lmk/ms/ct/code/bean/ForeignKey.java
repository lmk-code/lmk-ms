package com.lmk.ms.ct.code.bean;

public class ForeignKey {
	private String sourceTableName;
	private String sourceColumnName;
	private String targetTableName;
	private String targetColumnName;

	public ForeignKey() {
		super();
	}

	public ForeignKey(String sourceTableName, String sourceColumnName, String targetTableName, String targetColumnName) {
		this.sourceTableName = sourceTableName;
		this.sourceColumnName = sourceColumnName;
		this.targetTableName = targetTableName;
		this.targetColumnName = targetColumnName;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public String getSourceColumnName() {
		return sourceColumnName;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}
}
