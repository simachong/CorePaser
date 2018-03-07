package com.cricetulu.core.module;

public class BinarySTM extends AST{

	// ACCEPT ADD MOVE SET
	private String from;
	private String to;
	
	public BinarySTM(AST pAst) {
		
		this.pAst = pAst;
		this.setAstName("BinaryOp");
		pAst.getAsts().add(this);
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
