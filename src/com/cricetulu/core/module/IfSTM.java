package com.cricetulu.core.module;

public class IfSTM extends AST {

	private AST ifStm;
	private AST elseStm;
	private AST conditions;
	private AST ifAst;
	
	public IfSTM(AST pAst) {
		
		this.pAst = pAst;
		ifAst = new AST();
		ifStm = new AST();
		ifStm.setAstName("IF-STM");
		elseStm = new AST();
		elseStm.setAstName("ELSE-STM");
		conditions = new AST();
		conditions.setAstName("CONDITIONS");
		ifAst.setAstName("IF");
		ifAst.getAsts().add(ifStm);     // if
		ifAst.getAsts().add(elseStm);   // else
		ifAst.getAsts().add(conditions);// conditions
		pAst.getAsts().add(ifAst);
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
