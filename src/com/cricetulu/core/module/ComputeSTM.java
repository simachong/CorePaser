package com.cricetulu.core.module;

import java.util.ArrayList;

import com.circetulu.core.block.Token;

public class ComputeSTM extends AST {

	private String left;
	
	public ComputeSTM(AST pAst) {
		
		this.setAstName("COMPUTE");
		this.pAst = pAst;
		pAst.getAsts().add(this);
	}
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}

	public AST getAst() {
		return this;
	}
}
