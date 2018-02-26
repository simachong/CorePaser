package com.cricetulu.core.module;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.circetulu.core.block.Token;

public class AST {

	LinkedHashMap<String, AST> ast;
	ArrayList<Token> tokens;
	
	public AST() {
		
		ast = new LinkedHashMap<String, AST>();
	}

	public LinkedHashMap<String, AST> getAst() {
		return ast;
	}

	public void setAst(LinkedHashMap<String, AST> ast) {
		this.ast = ast;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
}
