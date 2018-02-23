package com.circetulu.core.block;

import java.util.ArrayList;

public class Routine extends Block{

	private String name;
	private String from;
	private String to;
	private ArrayList<Block> sentences;
	private boolean isEnd;
	private Routine nextRoutine;
	
	public Routine(String name) {
		
		this.name = name;
		this.from = name;
		this.sentences = new ArrayList<Block>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public ArrayList<Block> getSentences() {
		return sentences;
	}
	public void setSentences(ArrayList<Block> sentences) {
		this.sentences = sentences;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Routine getNextRoutine() {
		return nextRoutine;
	}
	public void setNextRoutine(Routine nextRoutine) {
		this.nextRoutine = nextRoutine;
	}
}
