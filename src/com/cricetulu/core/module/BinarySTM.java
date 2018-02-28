package com.cricetulu.core.module;

public class BinarySTM extends AST{

	// ACCEPT ADD MOVE SET
	private String from;
	private String to;
	private AST ast;
	
	public BinarySTM(AST pAst) {
		
		ast = new AST();
		ast.setAstName("BinaryOp");
		pAst.getAst().add(ast);
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
}
