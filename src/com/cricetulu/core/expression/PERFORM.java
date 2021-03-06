package com.cricetulu.core.expression;

import java.util.Stack;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.IfSTM;
import com.cricetulu.core.module.PerformSTM;

public class PERFORM extends Expression {

	private static Stack<IfSTM> perStack;
	PerformSTM ps;
	
	public PERFORM() {
		
		if (perStack == null) {
			
			perStack = new Stack<IfSTM>();
		}
	}
	
	public void clear() {
		
		perStack = null;
	}
	
	public int execute(AST ast, Sentence sentence, Index i) {
		

		boolean stFst = false;
		if (ast == sentence.getAst()) {
			stFst = true;
		}
		
		ps = new PerformSTM(ast);
		tokens = sentence.getTokens();
		
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		//CASE1 PERFORM from [TRUE to]
		
		//CASE2 PERFORM from [THRU to] count TIMES [statement END-PERFORM]
		
		//CASE3 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] UNTIL condition [loopstatement END-PERFORM]
		
		//CASE4 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] VARYING {identifier, IndexName} FROM {Identifier2, IndexName2, Literal} 
		//      BY {Identifier3, Literal} UNTIL condition1 
		//      [AFTER {Identifier4, IndexName3} FROM {Identifier5, IndexName4, Literal} BY {Identifier6, Literal} UNTIL condition2] [loopstatement END-PERFORM]
		
		int begin = i.i;
		// multi loop
		for (; i.i < tokens.size(); ++i.i) {
			
			String tokenName = tokens.get(i.i).getTokenName();
			
			if (i.i == begin + 1) {
				
				if (!tokenName.equals("UNTIL") && !tokenName.equals("VARYING") && !tokenName.equals("WITH")) {
					
					ps.setFrom(tokenName);
				}
			}
				
			switch (tokenName) {
			case "THRU" : ps.setTo(tokens.get(i.i + 1).getTokenName()); break;
			case "UNTIL" : break;
			case "VARYUING" : break;
//			case "NEXT" :
//				if (tokens.get(i.i + 1).getTokenName().equals("SENTENCE")) {
//					return 1;
//				}
//				break;
			default : break;
			}
			
			Expression exp = null;
			if (i.i > begin && GlobalDef.isExp(tokenName)) {
				
				if (stFst) {
					exp = GlobalDef.expressions.get(tokenName);
					if (1 ==exp.execute(ast, sentence, i)) {
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
