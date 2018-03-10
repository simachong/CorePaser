package com.cricetulu.core.expression;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
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
	
	private static final String [] evalCondEnds = {"WHEN", "END-EVALUATE"};
	private static final String [] whenCondEnds = {"ELSE", "END-IF", "WHEN", "NEXT", "CONTINUE"};
	
	private boolean isEnd(String name, String [] ends) {
		
		for (int i = 0; i < ends.length; ++i) {
			
			if (name.equals(ends[i])) {
				return true;
			}
		}
		return false;
	}
	
	public int execute(AST ast, Sentence sentence, Index i) {
		
		boolean stFst = false;
		if (ast == sentence.getAst()) {
			stFst = true;
		}

		
		EvaluateSTM es = new EvaluateSTM(ast);
		tokens = sentence.getTokens();
		
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		AST tmpAst = null;
		Expression exp = null;
		boolean isEvaluate = false;
		boolean isWhenCond = false;
		boolean isWhenStm = false;
		int begin = i.i;
		for (; i.i < tokens.size(); ++i.i) {
			
			boolean fj = false;
			String tokenName = tokens.get(i.i).getTokenName();

			if (tokenName.equals("CPS404-FILE-NMBR")) {
				System.out.println();
			}
			switch (tokenName.toUpperCase()) {
				
				case "EVALUATE" : isEvaluate = true; isWhenCond = false; isWhenStm=false; fj = true;
					break;
				case "WHEN" : 
					isWhenCond = true; isWhenStm=false; isEvaluate = false; fj = true;
					tmpAst = null;
					break;
				case "END-EVALUATE" : isEvaluate = false; isWhenCond = false;isWhenStm=false;
					if (stFst) {
						break;
					}
					else {
						return 0;
					}
				case "NEXT" :
					if (tokens.get(i.i + 1).getTokenName().equals("SENTENCE")) {
						AST nextS = new AST();
						nextS.setAstName("NEXT SENTENCE");
						nextS.getTokens().add(new Token("NEXT", -1, false));
						nextS.getTokens().add(new Token("SENTENCE", -1, false));
						tmpAst.getAsts().add(nextS);
					}
					break;
				case "CONINUE" :
					break;
				default: break;
			}
			
			if (fj) {
				continue;
			}
			
			if (isEvaluate && isEnd(tokenName, evalCondEnds)) {
				isEvaluate = false;
				--i.i;
			}
			
			if (isWhenCond && (isEnd(tokenName, whenCondEnds)|| GlobalDef.isExp(tokenName))) {
				isWhenCond = false;
				isWhenStm = true;
			}
			if (isEvaluate) {
				
				AST conditions = es.getConditionsDs();
				conditions.getTokens().add(tokens.get(i.i));
			}
			
			if (isWhenCond && !GlobalDef.isExp(tokenName)) {
				
				AST conditions = es.getWhenCondition();
				AST astC = new AST();
				conditions.getAsts().add(astC);
				astC.getTokens().add(tokens.get(i.i));
			}	
			
			if (isWhenStm && tmpAst == null) {
				AST whenA = new AST();
				es.getWhenStm().getAsts().add(whenA);
				tmpAst = whenA;
			}
			
			if (i.i > begin && GlobalDef.isExp(tokenName) && tmpAst != null) {
				
				exp = GlobalDef.expressions.get(tokenName);
				if (1 == exp.execute(tmpAst, sentence, i)) {
					
					return 1;
				}
			}
		}
		
		return 0;
	}
	
}
