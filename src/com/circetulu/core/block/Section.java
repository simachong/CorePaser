package com.circetulu.core.block;

import java.util.ArrayList;

public class Section extends Block{

	private String name;
	private ArrayList<Block> blks;
	
	public Section (String name) {
		
		this.name = name;
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
	
	
}
