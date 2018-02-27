package com.cricetulu.core.expression;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.GotoSTM;

public class GO extends Expression{

	public int execute(AST ast, Sentence sentence, int i) {
		
		GotoSTM gt = new GotoSTM(ast);
		tokens = sentence.getTokens();
		
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		for (; i < tokens.size(); ++i) {
		
			String tokenName = tokens.get(i).getTokenName();
			switch (tokenName.toUpperCase()) {
			
			case "TO" : gt.setTo(tokens.get(i + 1).getTokenName()); break;
			default : break;
			}
			
			Expression exp = null;
			if (i > 0 && GlobalDef.isExp(tokenName)) {
				
				exp = GlobalDef.expressions.get(tokenName);
				if (1 == exp.execute(ast, sentence, i)) {
					return 1;
				}
			}
		}
		return 0;
	}
}
