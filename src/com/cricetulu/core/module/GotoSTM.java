package com.cricetulu.core.module;

public class GotoSTM extends AST {

	private AST gotoAst;

	private String to;
	
	public GotoSTM(AST ast) {
		
		this.pAst = ast;
		gotoAst = new AST();
		gotoAst.setAstName("GOTO");
		ast.getAsts().add(gotoAst);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
