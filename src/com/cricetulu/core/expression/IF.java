package com.cricetulu.core.expression;

import java.util.ArrayList;
import java.util.Stack;

import com.circetulu.core.block.Token;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.IFSTM;

public class IF {

	
	// IF condition [THEN] {statement} [ELSE] {statement} [END-IF]
	
	// condition end: EXP NEXT SENTENCE  THEN  END-IF ELSE
	
	private static Stack<IF> ifStack = new Stack<IF>();
	
	public IF() {
		
	}
	
	public void execute (AST ast, ArrayList<Token> tokens) {
		
		IFSTM im = (IFSTM)ast;
		
		if (tokens.size() < 1) {
			return; // error log
		}
		
		int expCount = 0;
		Expression exp = null;
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			switch (tokenName.toUpperCase()) {
				
				case "IF": ifStack.push(this); break;
				case "ELSE" : IF ifexp = ifStack.pop(); break;
				case "END-IF" : break;
				default: break;
			}
			
			if (GlobalDef.isExp(tokenName)) {
				
				if (expCount != 0) {

					//exp.execute(st.getAst(), expTks);
				}
				++expCount;
//				/exp = GlobalDef.expressions.get(tk.getTokenName());
				//expTks = new ArrayList<Token>();
			}
			//expTks.add(tk);
		
			if (exp != null) {

				//exp.execute(st.getAst(), expTks);
			}
		}
	}
}
