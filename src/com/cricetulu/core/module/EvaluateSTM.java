package com.cricetulu.core.module;

public class EvaluateSTM {

	private AST evaluateAst;
	private AST conditionsDs;
	private AST whenCondition;
	private AST whenStm;
	
	public EvaluateSTM(AST ast) {
		
		conditionsDs = new AST();
		whenCondition = new AST();
		whenStm = new AST();
		evaluateAst = new AST();
		evaluateAst.getAst().add(conditionsDs);
		evaluateAst.getAst().add(whenCondition);
		evaluateAst.getAst().add(whenStm);
		ast.getAst().add(evaluateAst);
	}

	public AST getConditionsDs() {
		return conditionsDs;
	}

	public void setConditionsDs(AST conditionsDs) {
		this.conditionsDs = conditionsDs;
	}

	public AST getEvaluateAst() {
		return evaluateAst;
	}

	public void setEvaluateAst(AST evaluateAst) {
		this.evaluateAst = evaluateAst;
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
