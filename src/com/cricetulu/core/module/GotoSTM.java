package com.cricetulu.core.module;

public class GotoSTM extends AST {

	private AST gotoAst;

	private String to;
	
	public GotoSTM(AST ast) {
		
		gotoAst = new AST();
		ast.getAst().add(gotoAst);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
