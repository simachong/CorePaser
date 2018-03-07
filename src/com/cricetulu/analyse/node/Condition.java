package com.cricetulu.analyse.node;

import java.util.ArrayList;

public class Condition extends Node {

	private ArrayList<Node> whens;
	private ArrayList<String> whenCondition;
	private String ifCondition;
	private String conditionType;
	
	public Condition(String name) {
		
		super(name);
		whens = new ArrayList<Node>();
		whenCondition = new ArrayList<String>();
	}
	
	public ArrayList<Node> getWhens() {
		return whens;
	}
	public void setWhens(ArrayList<Node> whens) {
		this.whens = whens;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getIfCondition() {
		return ifCondition;
	}

	public void setIfCondition(String ifCondition) {
		this.ifCondition = ifCondition;
	}

	public ArrayList<String> getWhenCondition() {
		return whenCondition;
	}

	public void setWhenCondition(ArrayList<String> whenCondition) {
		this.whenCondition = whenCondition;
	}
	
	
}
