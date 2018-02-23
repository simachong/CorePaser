package com.cricetulu.core.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 
 * @author simachong
 *
 */
public class DataStorage extends DataModule{
	
	private String hierarchy;
	private String name;
	private String value;
	private String dataType;
	
	private String copybook = "wk";
	private String fileName;
	
	private int ref;

	private boolean isHierarchy;
	
	private String usage;
	
	private int occurTime = 0;
	private String index;
	private String denpences;
	private int minTime = 0;
	private int maxTime = 0;
	
	private boolean isItem88;
	private LinkedHashMap<String, Item88> item88s;
	
	private boolean isCondition;
	private ArrayList<String> conditionVals;
	
	private ArrayList<ValueLink> valueLinks;
	
	private HashMap<String, DataStorage> nestDs;

	public DataStorage() {
		
		setItem88s(new LinkedHashMap<String, Item88>());
	}
	
	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public boolean isItem88() {
		return isItem88;
	}

	public void setItem88(boolean isItem88) {
		this.isItem88 = isItem88;
	}

	public boolean isHierarchy() {
		return isHierarchy;
	}

	public void setHierarchy(boolean isHierarchy) {
		this.isHierarchy = isHierarchy;
	}

	public HashMap<String, DataStorage> getNestDs() {
		return nestDs;
	}

	public void setNestDs(HashMap<String, DataStorage> nestDs) {
		this.nestDs = nestDs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCopybook() {
		return copybook;
	}

	public void setCopybook(String copybook) {
		this.copybook = copybook;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public int getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(int occurTime) {
		this.occurTime = occurTime;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDenpences() {
		return denpences;
	}

	public void setDenpences(String denpences) {
		this.denpences = denpences;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public LinkedHashMap<String, Item88> getItem88s() {
		return item88s;
	}

	public void setItem88s(LinkedHashMap<String, Item88> item88s) {
		this.item88s = item88s;
	}

	public boolean isCondition() {
		return isCondition;
	}

	public void setCondition(boolean isCondition) {
		this.isCondition = isCondition;
	}

	public ArrayList<String> getConditionVals() {
		return conditionVals;
	}

	public void setConditionVals(ArrayList<String> conditionVals) {
		this.conditionVals = conditionVals;
	}

	public ArrayList<ValueLink> getValueLinks() {
		return valueLinks;
	}

	public void setValueLinks(ArrayList<ValueLink> valueLinks) {
		this.valueLinks = valueLinks;
	}

}
//*