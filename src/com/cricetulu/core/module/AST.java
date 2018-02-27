package com.cricetulu.core.module;

import java.util.ArrayList;

import com.circetulu.core.block.Token;

public class AST {

	ArrayList<AST> ast;
	ArrayList<Token> tokens;
	String astName;
	
	public AST() {
		
		ast = new ArrayList<AST>();
		tokens = new ArrayList<Token>();
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	public ArrayList<AST> getAst() {
		return ast;
	}

	public void setAst(ArrayList<AST> ast) {
		this.ast = ast;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}
}
