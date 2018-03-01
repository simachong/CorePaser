package com.cricetulu.analyse;

import java.util.ArrayList;

public class Condition extends Node {

	private Node leftTrue;
	private Node rightFalse;
	private ArrayList<Node> whens;
	private ArrayList<String> whenCondition;
	private String ifCondition;
	private String conditionType;
	
	public Condition() {
		
		whens = new ArrayList<Node>();
		whenCondition = new ArrayList<String>();
	}
	
	public Node getLeftTrue() {
		return leftTrue;
	}
	public void setLeftTrue(Node leftTrue) {
		this.leftTrue = leftTrue;
	}
	public Node getRightFalse() {
		return rightFalse;
	}
	public void setRightFalse(Node rightFalse) {
		this.rightFalse = rightFalse;
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