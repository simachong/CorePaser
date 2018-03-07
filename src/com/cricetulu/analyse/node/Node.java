package com.cricetulu.analyse.node;

import java.util.ArrayList;

public class Node {

	private ArrayList<Node> nextNodes;
	private String nodeName;
	private String desc = "";
	boolean isPrint = false;
	
	public Node(String name) {
		
		setNextNodes(new ArrayList<Node>());
		nodeName = name;
	}
	
	public String getNodeName() {
		return nodeName;
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
	
	
}
