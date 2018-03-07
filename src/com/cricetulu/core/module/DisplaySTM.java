package com.cricetulu.core.module;

public class DisplaySTM extends AST {
	public DisplaySTM(AST pAst) {
		
		this.pAst = pAst;
		pAst.getAsts().add(this);
	}

	public AST getDspAst() {
		return this;
	}	
}
