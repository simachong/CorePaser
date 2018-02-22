package com.cricetulu.core.module;

import java.util.LinkedHashMap;

public class FileC extends DataModule{

	private String jclFilename;
	private String filenName;
	private String accessMethod;
	private String organization;
	private String status;
	private String recKey;
	
	private String recNum;
	private String charNum;
	private String labelRec;
	private String recMode;
	
	private LinkedHashMap<String,DataStorage> ds = new LinkedHashMap<String,DataStorage>();
	
	public String getJclFilename() {
		return jclFilename;
	}
	public void setJclFilename(String jclFilename) {
		this.jclFilename = jclFilename;
	}
	public String getFilenName() {
		return filenName;
	}
	public void setFilenName(String filenName) {
		this.filenName = filenName;
	}
	public String getAccessMethod() {
		return accessMethod;
	}
	public void setAccessMethod(String accessMethod) {
		this.accessMethod = accessMethod;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecKey() {
		return recKey;
	}
	public void setRecKey(String recKey) {
		this.recKey = recKey;
	}
	public String getRecNum() {
		return recNum;
	}
	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}
	public String getCharNum() {
		return charNum;
	}
	public void setCharNum(String charNum) {
		this.charNum = charNum;
	}
	public String getLabelRec() {
		return labelRec;
	}
	public void setLabelRec(String labelRec) {
		this.labelRec = labelRec;
	}
	public String getRecMode() {
		return recMode;
	}
	public void setRecMode(String recMode) {
		this.recMode = recMode;
	}
	
	
	public LinkedHashMap<String, DataStorage> getDs() {
		return ds;
	}
	public void setDs(LinkedHashMap<String, DataStorage> ds) {
		this.ds = ds;
	}
	
	
}
