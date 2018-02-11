package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Token;

public class Expression {

	protected String exp;
	protected String endInd;
	protected ArrayList<Token> tokens;
	
	public Expression() {
		
		tokens = new ArrayList<Token>();
	}
	
	public String execute(int line) {
		
		return "";
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getEndInd() {
		return endInd;
	}

	public void setEndInd(String endInd) {
		this.endInd = endInd;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}	
}
