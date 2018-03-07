package com.cricetulu.core.module;

public class IfSTM extends AST {

	private AST ifStm;
	private AST elseStm;
	private AST conditions;
	
	public IfSTM(AST pAst) {
		
		this.pAst = pAst;
		
		ifStm = new AST();
		ifStm.setAstName("IF-STM");
		elseStm = new AST();
		elseStm.setAstName("ELSE-STM");
		conditions = new AST();
		conditions.setAstName("CONDITIONS");
		this.setAstName("IF");
		this.getAsts().add(ifStm);     // if
		this.getAsts().add(elseStm);   // else
		this.getAsts().add(conditions);// conditions
		pAst.getAsts().add(this);
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
		return this;
	}
	
}
