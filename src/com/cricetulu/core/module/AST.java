package com.cricetulu.core.module;

import java.util.ArrayList;

import com.circetulu.core.block.Token;

public class AST {

	private ArrayList<AST> asts;
	protected AST pAst;
	private ArrayList<Token> tokens;
	private String astName;
	
	public AST() {
		
		asts = new ArrayList<AST>();
		tokens = new ArrayList<Token>();
	}

	public String toString() {
		
		String sent = "";
		for(Token token: tokens) {
			
			sent += " " + token.getTokenName();
		}
		return sent;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	public ArrayList<AST> getAsts() {
		return asts;
	}

	public void setAsts(ArrayList<AST> ast) {
		this.asts = ast;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}
}
