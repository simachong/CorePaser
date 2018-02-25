package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.PERFORMSTM;

public class PERFORM {

	public void execute (DataModule dataModule, Sentence sentence) {
		
		PERFORMSTM ps = (PERFORMSTM)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		
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
			
			switch (tokenName) {

			}

		}
	}
}
