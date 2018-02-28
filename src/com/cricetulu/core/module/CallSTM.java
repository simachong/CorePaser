package com.cricetulu.core.module;

public class CallSTM extends AST {

	private String prog;
	private String tblName;
	private AST callAst;
	
	public CallSTM (AST pAst) {
		
		callAst = new AST();
		callAst.setAstName("CALL");
		
		this.pAst = pAst;
		pAst.getAsts().add(callAst);
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
		return callAst;
	}

	public void setAst(AST ast) {
		this.callAst = ast;
	}
}
