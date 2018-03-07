package com.cricetulu.core.module;

public class PerformSTM extends AST{

	//CASE1 PERFORM from [TRUE to]
	//CASE2 PERFORM from [THRU to] count TIMES [statement END-PERFORM]	
	//CASE3 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] UNTIL condition [loopstatement END-PERFORM]
	//CASE4 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] VARYING {identifier, IndexName} FROM {Identifier2, IndexName2, Literal} 
	//      BY {Identifier3, Literal} UNTIL condition1 
	//      [AFTER {Identifier4, IndexName3} FROM {Identifier5, IndexName4, Literal} BY {Identifier6, Literal} UNTIL condition2] [loopstatement END-PERFORM]
	
	private String from;
	private String to;

	public PerformSTM(AST ast) {
		
		this.pAst = ast;
		this.setAstName("PERFORM");
		ast.getAsts().add(this);
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
