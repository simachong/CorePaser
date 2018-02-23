package com.cricetulu.core.module;

public class ValueLink {

	private Condition condtion;
	private DataStorage fromDs;
	private String constVal;
	private boolean isConst;
	
	public Condition getCondtion() {
		return condtion;
	}
	public void setCondtion(Condition condtion) {
		this.condtion = condtion;
	}
	public DataStorage getFromDs() {
		return fromDs;
	}
	public void setFromDs(DataStorage fromDs) {
		this.fromDs = fromDs;
	}
	public String getConstVal() {
		return constVal;
	}
	public void setConstVal(String constVal) {
		this.constVal = constVal;
	}
	public boolean isConst() {
		return isConst;
	}
	public void setConst(boolean isConst) {
		this.isConst = isConst;
	}	
}
