package com.circetulu.core.block;

import java.util.ArrayList;

import com.cricetulu.core.module.AST;

public class Sentence extends Block {

	private ArrayList<Token> tokens;
	private boolean isLable;
	private boolean isSection;
	private String lableName;
	private AST ast;
	
	public Sentence() {
		
		tokens = new ArrayList<Token>();
		ast = new AST();
		lableName = "";
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

	public boolean isLable() {
		return isLable;
	}

	public void setLable(boolean isLable) {
		this.isLable = isLable;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public boolean isSection() {
		return isSection;
	}

	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}

	public AST getAst() {
		return ast;
	}

	public void setAst(AST ast) {
		this.ast = ast;
	}
}
