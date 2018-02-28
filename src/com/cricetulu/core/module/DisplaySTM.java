package com.cricetulu.core.module;

public class DisplaySTM extends AST {

	private AST dspAst;
	
	public DisplaySTM(AST pAst) {
		
		this.pAst = pAst;
		dspAst = new AST();
		pAst.getAsts().add(dspAst);
	}

	public AST getDspAst() {
		return dspAst;
	}

	public void setDspAst(AST dspAst) {
		this.dspAst = dspAst;
	}
	
	
}
