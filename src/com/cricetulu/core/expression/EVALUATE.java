package com.cricetulu.core.expression;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.EvaluateSTM;

public class EVALUATE extends Expression {

	// EVALUATE 
	// ALSO 
	// WHEN ANY|TRUE|FALSE|condition|NOT|THRU
	// WHEN	OTHER
	// END-EVALUEATE
	//
	public int execute(AST ast, Sentence sentence, Index i) {
		
		EvaluateSTM es = new EvaluateSTM(ast);
		tokens = sentence.getTokens();
		
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		AST tmpAst = null;
		Expression exp = null;
		boolean isEvaluate = false;
		boolean isWhen = false;
		int begin = i.i;
		for (; i.i < tokens.size(); ++i.i) {
			
			String tokenName = tokens.get(i.i).getTokenName();
			switch (tokenName.toUpperCase()) {
				
				case "EVALUATE" : isEvaluate = true; isWhen = false;
					break;
				case "WHEN" : isWhen = true; isEvaluate = false;
					break;
				case "END-EVALUATE" : isEvaluate = false; isWhen = false;
					break;
//				case "NEXT" :
//					if (tokens.get(i.i + 1).getTokenName().equals("SENTENCE")) {
//						return 1;
//					}
//					break;
				case "CONINUE" :
				//scan until
					break;
				default: break;
			}
			
			if (isEvaluate) {
				
				AST conditions = es.getConditionsDs();
				conditions.getTokens().add(tokens.get(i.i));
			}
			
			if (isWhen) {
				
				AST conditions = es.getWhenCondition();
				conditions.getTokens().add(tokens.get(i.i));
				if (tmpAst == null) {
					
					tmpAst = es.getWhenStm();
				}
			}	
			
			if (i.i > begin && GlobalDef.isExp(tokenName) && tmpAst != null) {
				
				exp = GlobalDef.expressions.get(tokenName);
				if (1 == exp.execute(tmpAst, sentence, i)) {
					
					return 1;
				}
				tmpAst = null;
			}
		}
		
		return 0;
	}
	
}
