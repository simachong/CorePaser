package com.circetulu.core.block;

import java.util.ArrayList;

public class Section extends Block{

	private String name;
	private ArrayList<Block> blks;
	
	private String from;
	private String to;
	private boolean isEnd;
	
	public Section (String name) {
		
		this.name = name;
		this.from = name;
		blks = new ArrayList<Block>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Block> getBlks() {
		return blks;
	}
	public void setBlks(ArrayList<Block> blks) {
		this.blks = blks;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
	
}
