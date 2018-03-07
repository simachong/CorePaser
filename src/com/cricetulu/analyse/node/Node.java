package com.cricetulu.analyse.node;

public class Node {

	private Node nextNode;
	private String nodeName;
	
	public Node(Node node) {
		
		this.nextNode = node.getNextNode();
		this.nodeName = node.getNodeName();
	}
	
	public Node(String name) {
		
		nodeName = name;
	}
	
	public Node getNextNode() {
		return nextNode;
	}
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
