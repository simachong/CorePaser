package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Token;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.PERFORMSTM;

public class PERFORM extends Expression {

	public void execute (AST ast, ArrayList<Token> tokens) {
		
		PERFORMSTM ps = (PERFORMSTM)ast;
		
		if (tokens.size() < 1) {
			return; // error log
		}
		
		//CASE1 PERFORM from [TRUE to]
		
		//CASE2 PERFORM from [THRU to] count TIMES [statement END-PERFORM]
		
		//CASE3 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] UNTIL condition [loopstatement END-PERFORM]
		
		//CASE4 PERFORM [from TRUE to] [WITH TEST {BEFORE, AFTER}] VARYING {identifier, IndexName} FROM {Identifier2, IndexName2, Literal} 
		//      BY {Identifier3, Literal} UNTIL condition1 
		//      [AFTER {Identifier4, IndexName3} FROM {Identifier5, IndexName4, Literal} BY {Identifier6, Literal} UNTIL condition2] [loopstatement END-PERFORM]
		
		
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			if (i == 1) {
				
				if (!tokenName.equals("UNTIL") && !tokenName.equals("VARYING") && !tokenName.equals("WITH")) {
					
					ps.setFrom(tokenName);
				}
			}
				
			switch (tokenName) {
			case "THRU" : ps.setTo(tokens.get(i + 1).getTokenName()); break;
			case "UNTIL" : break;
			case "VARYUING" : break;
			default : break;
			}

		}
	}
}
