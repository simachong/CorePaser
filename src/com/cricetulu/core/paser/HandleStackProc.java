package com.cricetulu.core.paser;

import java.util.ArrayList;

import com.circetulu.core.block.Block;
import com.circetulu.core.block.Routine;
import com.circetulu.core.block.Section;
import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.expression.Expression;
import com.cricetulu.core.global.GlobalDef;

public class HandleStackProc {

	private ArrayList<Block> blksIter;
	
	public HandleStackProc(ArrayList<Block> blksIter) {
		
		this.blksIter = blksIter;
	}
	
	public void process() {
		
		for (Block blk : blksIter) {
			
			if (blk instanceof Section) {
				
				Section sec = (Section)blk;
				//System.out.println(sec.getName());
				ArrayList<Block> secblks = sec.getBlks();
				for (Block secblk : secblks) {
					
					if (secblk instanceof Routine) {
						
						Routine rt = (Routine)secblk;
						if (!rt.isEnd()) {
							System.out.println(rt.getName());
						}
					}
				}
			}
			
			if (blk instanceof Routine) {
				
				Routine rt = (Routine)blk;
				if (!rt.isEnd()) {
					System.out.println(rt.getName());
				}
			}
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				ArrayList<Token> tokens = st.getTokens();
				
				if (tokens.size() > 2) {
					GlobalDef.expressions.get(tokens.get(0).getTokenName()).execute(st.getAst(), st);
				}
				
//				for (int i = 0; i < tokens.size(); ++i) {
//					
//					Token tk = tokens.get(i);
//					
//					if (GlobalDef.isExp(tk.getTokenName())) {
//						
//						if (expCount != 0) {
//
//							exp.execute(st.getAst(), expTks);
//						}
//						++expCount;
//						exp = GlobalDef.expressions.get(tk.getTokenName());
//						expTks = new ArrayList<Token>();
//					}
//					expTks.add(tk);
//				}
//				
//				if (exp != null) {
//
//					exp.execute(st.getAst(), expTks);
//				}
			}
		}
	}
}
