package com.cricetulu.core.expression;

import java.util.Stack;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.IfSTM;

public class IF extends Expression{

	
	// IF condition [THEN] {statement} [ELSE] {statement} [END-IF]
	
	// condition end: EXP NEXT SENTENCE  THEN  END-IF ELSE
	
	private static Stack<IfSTM> ifStack;
	IfSTM ifstm;
	
	public IF() {
		
		if (ifStack == null) {
			
			ifStack = new Stack<IfSTM>();
		}
	}
	public void init() {
		
		if (ifStack == null) {
			
			ifStack = new Stack<IfSTM>();
		}
	}
	
	public void clear() {
		
		ifStack = null;
	}
	
	public int execute (AST ast, Sentence st, Integer i) {
		
		ifstm = new IfSTM(ast);
		tokens = st.getTokens();
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		Expression exp = null;
		
		boolean isIf = false;
		boolean isElse = false;
	
		AST tmpAst = null;
		int begin = i;
		//boolean 
		for (; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			switch (tokenName.toUpperCase()) {
				
				case "IF" : 
					ifStack.push(ifstm); 
					isIf = true; isElse = false; break;
				case "ELSE" : 
					if (isElse) {
						ifstm = ifStack.pop();
					}
					isIf = false; isElse = true; break;
				case "END-IF" : 
					ifstm = ifStack.pop();
					isIf = false; isElse = false;
					if (ifStack.size() == 0) {
						tmpAst = ast;
					}; 
					break;
				case "NEXT" :
					if (tokens.get(i).getTokenName().equals("SENTENCE")) {
						return 1;
					}
					break;
				case "CONINUE" :
				//scan until
					break;
				default: break;
			}
			
			if (isIf && !GlobalDef.isExp(tokenName)) {
				
				AST conditions = ifstm.getConditions();
				conditions.getTokens().add(tokens.get(i));
				if (tmpAst == null) {
					
					tmpAst = ifstm.getIfStm();
				}
			}
			
			if (isElse && tmpAst == null) {
				
				tmpAst = ifstm.getElseStm();
			}
			
			if (i > begin && GlobalDef.isExp(tokenName) && tmpAst != null) {
			
				exp = GlobalDef.expressions.get(tokenName);
				if (1 == exp.execute(tmpAst, st, i)) {
					
					return 1;
				}
				tmpAst = null;
			}
		}
		return 0;
	}

	public IfSTM getIfstm() {
		return ifstm;
	}

	public void setIfstm(IfSTM ifstm) {
		this.ifstm = ifstm;
	}
}
