package com.cricetulu.core.module;

import java.util.ArrayList;

import com.circetulu.core.block.Token;

public class ComputeSTM extends AST {

	private String left;
	private AST ast;
	
	public ComputeSTM(AST pAst) {
		
		ast = new AST();
		ast.setAstName("COMPUTE");
		this.pAst = pAst;
	}
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}

	public AST getAst() {
		return ast;
	}

	public void setAst(AST ast) {
		this.ast = ast;
	}
	
	
}
