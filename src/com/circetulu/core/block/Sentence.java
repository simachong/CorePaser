package com.circetulu.core.block;

import java.util.ArrayList;

public class Sentence {

	private ArrayList<Token> tokens;
	
	public Sentence() {
		
		tokens = new ArrayList<Token>();
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public String toString() {
		
		String sent = "";
		for(Token token: tokens) {
			
			sent += " " + token.getTokenName();
		}
		return sent;
	}
}
