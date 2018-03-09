package com.cricetulu.core.expression;

import com.circetulu.core.block.Sentence;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;

public class STOP extends Expression {

	public int execute (AST ast, Sentence st, Index i) {
 
		i.i = st.getTokens().size();
		return 4;
	}
}
