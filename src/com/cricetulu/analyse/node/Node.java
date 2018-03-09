package com.cricetulu.analyse.node;

import java.util.ArrayList;

public class Node {

	private ArrayList<Node> nextNodes;
	private String nodeName;
	private String desc = "";
	private int seq = 0;
	boolean isPrint = false;
	boolean isCondition = false;
	
	public Node(String name) {
		
		setNextNodes(new ArrayList<Node>());
		nodeName = name;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void addSeq() {
		seq++;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ArrayList<Node> getNextNodes() {
		return nextNodes;
	}

	public void setNextNodes(ArrayList<Node> nextNodes) {
		this.nextNodes = nextNodes;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

	public boolean isCondition() {
		return isCondition;
	}

	public void setCondition(boolean isCondition) {
		this.isCondition = isCondition;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	
}
