package com.cricetulu.core.module;

public class GotoSTM extends AST {
	
	private String to;
	
	public GotoSTM(AST ast) {
		
		this.pAst = ast;
		this.setAstName("GOTO");
		ast.getAsts().add(this);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
