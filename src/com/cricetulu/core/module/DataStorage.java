package com.cricetulu.core.module;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author simachong
 *
 */
public class DataStorage extends DataModule{
	
	// 灞傜骇 01 03 05..
	private String hierarchy;
	// 鍙橀噺鍚�
	private String name;
	// 鍊�
	private String value;
	// 鏁版嵁绫诲瀷
	private String dataType;
	// 鎵�灞瀋opybook  鏈湴瀹氫箟涓簑k
	private String copybook = "wk";
	
	private String fileName;
	
	private int ref;
	// 88椤�
	private Item88 item88;
	// 鏄惁涓�88椤�
	private boolean isItem88;
	// 鏄惁涓哄祵濂� 
	private boolean isHierarchy;
	
	private String usage;
	
	private int occurTime;
	private ArrayList<String> values;
	private String index;
	private String denpences;
	
	// 宓屽鏁版嵁缁撴瀯  <鍙橀噺鍚嶏紝瀹氫箟>
	private HashMap<String, DataStorage> nestDs;
	// 鍙橀噺閲嶅畾涔�
	private ArrayList<DataStorage> redefines; 

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

	public Item88 getItem88() {
		return item88;
	}

	public void setItem88(Item88 item88) {
		this.item88 = item88;
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

	public ArrayList<DataStorage> getRedefines() {
		return redefines;
	}

	public void setRedefines(ArrayList<DataStorage> redefines) {
		this.redefines = redefines;
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

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
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
}
//*