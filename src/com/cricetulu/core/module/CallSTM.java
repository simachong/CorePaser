package com.cricetulu.core.module;

public class CallSTM extends AST {

	private String prog;
	private String tblName;
	
	public CallSTM (AST pAst) {
		
		this.setAstName("CALL");
		
		this.pAst = pAst;
		pAst.getAsts().add(this);
	}

	public String getProg() {
		return prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public AST getAst() {
		return this;
	}
}
