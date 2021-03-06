package com.cricetulu.core.expression;

import java.util.Stack;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.IfSTM;

public class IF extends Expression{

	
	// IF condition [THEN] {statement} [ELSE] {statement} [END-IF]
	
	// condition end: EXP NEXT SENTENCE  THEN  END-IF ELSE
	private static Stack<IfSTM> ifStack;
	private IfSTM ifstm = null;
	
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
	
	public int execute (AST ast, Sentence st, Index i) {
		
		boolean stFst = false;
		if (ast == st.getAst()) {
			stFst = true;
		}

		init();
		ifstm = new IfSTM(ast);
		ifStack.push(ifstm); 
		tokens = st.getTokens();
		if (tokens.size() < 1) {
			return -1; // error log
		}
		
		Expression exp = null;
		
		boolean isIf = true;
		boolean isElse = false;
	
		AST tmpAst = null;
		int begin = i.i;
		//boolean 
		for (; i.i < tokens.size(); ++i.i) {
			
			String tokenName = tokens.get(i.i).getTokenName();
			
			switch (tokenName.toUpperCase()) {
				
				case "ELSE" : 
					tmpAst = null;
					if (isElse) {
						// if else end
						//System.out.println(st.toString());
						ifstm = ifStack.pop();
						return 2;
					}
					isIf = false; isElse = true; break;
				case "END-IF" : 	
					// if else end
					//System.out.println(st.toString() + i.i);
					isIf = false; isElse = false;
					if (ifStack.isEmpty()) {
						tmpAst = ast;
					}
					else {
						ifstm = ifStack.pop();
					}
					
					if (stFst) {
						break;
					}
					else {
						return 3;
					}
				case "NEXT" :
					if (tokens.get(i.i + 1).getTokenName().equals("SENTENCE")) {
						isIf = false;
						AST nextS = new AST();
						nextS.setAstName("NEXT SENTENCE");
						nextS.getTokens().add(new Token("NEXT", -1, false));
						nextS.getTokens().add(new Token("SENTENCE", -1, false));
						tmpAst.getAsts().add(nextS);
					}
					break;
				case "CONINUE" :
				//scan until
					break;
				default: break;
			}
			
			if (isIf && !GlobalDef.isExp(tokenName)) {
				
				AST conditions = ifstm.getConditions();
				conditions.getTokens().add(tokens.get(i.i));
				if (tmpAst == null) {
					
					tmpAst = ifstm.getIfStm();
				}
			}
			
			if (isElse && tmpAst == null) {
				
				tmpAst = ifstm.getElseStm();
			}
			
			if (i.i > begin && GlobalDef.isExp(tokenName)) {
			
				if (tmpAst == null) {
					
					System.out.println("ERR");
					System.out.println(st.toString());
					System.out.println(tokenName);
				}
				exp = GlobalDef.expressions.get(tokenName);
				int rtc = exp.execute(tmpAst, st, i);
				if (1 == rtc) {
					
					return 1;
				}
				else if (2 == rtc) {
					
					ifstm = ifStack.peek();
					isIf = false; isElse = false;
					tmpAst = null;
					--i.i;
					
				}
				else if (3 == rtc) {
					if (!ifStack.isEmpty()) {
						ifstm = ifStack.peek();
					}
					//tmpAst = null;
					
				}
				
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
