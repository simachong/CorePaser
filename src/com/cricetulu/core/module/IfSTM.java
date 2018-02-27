package com.cricetulu.core.module;

public class IfSTM extends AST {

	private AST ifStm;
	private AST elseStm;
	private AST conditions;
	private AST ifAst;
	
	public IfSTM(AST pAst) {
		
		ifAst = new AST();
		ifStm = new AST();
		elseStm = new AST();
		conditions = new AST();
		ifAst.setAstName("IF");
		ifAst.getAst().add(ifStm);     // if
		ifAst.getAst().add(elseStm);   // else
		ifAst.getAst().add(conditions);// conditions
		pAst.getAst().add(ifAst);
	}

	public AST getIfStm() {
		return ifStm;
	}

	public void setIfStm(AST ifStm) {
		this.ifStm = ifStm;
	}

	public AST getElseStm() {
		return elseStm;
	}

	public void setElseStm(AST elseStm) {
		this.elseStm = elseStm;
	}

	public AST getConditions() {
		return conditions;
	}

	public void setConditions(AST conditions) {
		this.conditions = conditions;
	}

	public AST getIfAst() {
		return ifAst;
	}

	public void setIfAst(AST ifAst) {
		this.ifAst = ifAst;
	}
	
	
}
