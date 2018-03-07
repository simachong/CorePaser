package com.cricetulu.core.module;

public class EvaluateSTM extends AST{

	private AST conditionsDs;
	private AST whenCondition;
	private AST whenStm;
	
	public EvaluateSTM(AST ast) {
		
		this.pAst = ast;
		conditionsDs = new AST();
		whenCondition = new AST();
		whenStm = new AST();
		this.setAstName("EVALUATE");
		this.getAsts().add(conditionsDs);
		this.getAsts().add(whenCondition);
		this.getAsts().add(whenStm);
		ast.getAsts().add(this);
	}

	public AST getConditionsDs() {
		return conditionsDs;
	}

	public void setConditionsDs(AST conditionsDs) {
		this.conditionsDs = conditionsDs;
	}

	public AST getEvaluateAst() {
		return this;
	}

	public AST getWhenCondition() {
		return whenCondition;
	}

	public void setWhenCondition(AST whenCondition) {
		this.whenCondition = whenCondition;
	}

	public AST getWhenStm() {
		return whenStm;
	}

	public void setWhenStm(AST whenStm) {
		this.whenStm = whenStm;
	}
}
