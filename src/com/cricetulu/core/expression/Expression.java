package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.DataModule;

public class Expression {

	protected String exp;
	protected String endInd;
	protected ArrayList<Token> tokens;
	protected ArrayList<AST> asts;
	
	public Expression() {
		
		tokens = new ArrayList<Token>();
	}
	
	public void init() {
		
	}
	
	public void clear() {
		
	}
	
	public void execute(DataModule dataModule, Sentence sentence) {
		
	}
	
	public int execute(AST ast, Sentence sentence, Index i) {
		
		ast.setTokens(tokens);
		return 0;
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
