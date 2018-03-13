package com.cricetulu.core.expression;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.ComputeSTM;

public class COMPUTE extends Expression {

	public int execute(AST ast, Sentence sentence, Index i) {
		
		boolean stFst = false;
		if (ast == sentence.getAst()) {
			stFst = true;
		}
		
		ComputeSTM cs = new ComputeSTM(ast);
		tokens = sentence.getTokens();
		
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		int begin = i.i;
		boolean rightExp = false;
		for (; i.i < tokens.size(); ++i.i) {
		
			String tokenName = tokens.get(i.i).getTokenName();
			
			switch (tokenName.toUpperCase()) {
			
			case "=" :
				cs.setLeft(tokens.get(i.i - 1).getTokenName()); 
				rightExp = true;
				break;
//			case "NEXT" :
//				if (tokens.get(i.i + 1).getTokenName().equals("SENTENCE")) {
//					return 1;
//				}
//				break;
			default : break;
			}
			
			if (rightExp) {
				
				cs.getAst().getTokens().add(tokens.get(i.i));
			}
			
			Expression exp = null;
			
			if (i.i > begin && GlobalDef.isExp(tokenName)) {
				
				if (stFst) {
					exp = GlobalDef.expressions.get(tokenName);
					if (1 == exp.execute(ast, sentence, i)) {
						return 1;
					}
				}
				else {
					--i.i;
					return 0;
				}
			}
			
			if (i.i > begin && isEnd(tokenName)) {
				--i.i;
				return 0;
			}
		}
		return 0;
	}

}
