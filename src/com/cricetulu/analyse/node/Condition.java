package com.cricetulu.analyse.node;

public class Condition extends Node {


	private String Condition;
	private String conditionType;
	
	public Condition(String name) {
		
		super(name);
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getCondition() {
		return Condition;
	}

	public void setCondition(String ifCondition) {
		this.Condition = ifCondition;
	}
}
