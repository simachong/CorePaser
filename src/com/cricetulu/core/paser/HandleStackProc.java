package com.cricetulu.core.paser;

import java.util.ArrayList;

import com.circetulu.core.block.Block;
import com.circetulu.core.block.Routine;
import com.circetulu.core.block.Section;
import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.expression.Expression;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;

public class HandleStackProc {
 
	private ArrayList<Block> blksIter;
	
	public HandleStackProc(ArrayList<Block> blksIter) {
		
		this.blksIter = blksIter;
	}
	
private int analyse(Sentence st, Index i) {
		
		ArrayList<Token> tokens = st.getTokens();
		
		if (tokens.size() > 1) {
			
			Expression exp = GlobalDef.expressions.get(tokens.get(i.i).getTokenName());
			if (exp != null) {
				exp.init();
				int rt = exp.execute(st.getAst(), st, i);
			
				//System.out.println();
				if (i.i < st.getTokens().size() - 1) {
					
					return analyse(st, i);
				}
				if (i.i < st.getTokens().size() - 1) {
					System.out.println(i.i + ":" + st.getTokens().size() + st.toString());
				}
				if (1 == rt ) {
					exp.clear();
					return 1;
				}
				else if (4 == rt) {
					
					return 4;
				}
			}
			else {
				System.out.println(tokens.get(0).getTokenName());
			}
			if (exp != null) {
				
				exp.clear();
			}
			else {
				
				System.out.println(st.toString());
			}
			
		}
		
		return 0;
	}
	
	private int analyse(Sentence st) {
		
		ArrayList<Token> tokens = st.getTokens();
		
		if (tokens.size() > 1) {
			
			Expression exp = GlobalDef.expressions.get(tokens.get(0).getTokenName());
			if (exp != null) {
				exp.init();
				Index i = new Index(0);
				return analyse(st, i);
			}
		}
		return 0;
	}
	
	public void buildAST() {
		
		for (Block blk : blksIter) {
			
			if (blk instanceof Section) {
				
				Section sec = (Section)blk;
				//System.out.println(sec.getName());
				ArrayList<Block> secblks = sec.getBlks();
				for (Block secblk : secblks) {
					
					if (secblk instanceof Routine) {
						
						Routine rt = (Routine)secblk;
						
						ArrayList<Block> rtblks = rt.getSentences();
						
						for (Block rtblk : rtblks) {
							
							if (rtblk instanceof Sentence) {
								
								Sentence st = (Sentence)rtblk;
								if (4 == analyse(st)) {
									rt.setStop(true);
									st.setStop(true);
								}
							}
						}
						if (!rt.isEnd()) {
							System.out.println(rt.getName());
							
						}
					}
					
					if (secblk instanceof Sentence) {
						
						Sentence st = (Sentence)secblk;
						if (4 == analyse(st)) {
							sec.setStop(true);
							st.setStop(true);
						}
					}
				}
			}
			
			if (blk instanceof Routine) {
				
				Routine rt = (Routine)blk;
				ArrayList<Block> rtblks = rt.getSentences();
				
				for (Block rtblk : rtblks) {
					
					if (rtblk instanceof Sentence) {
						
						Sentence st = (Sentence)rtblk;
						if (4 == analyse(st)) {
							rt.setStop(true);
							st.setStop(true);
						}
					}
				}
				
				if (!rt.isEnd()) {
					System.out.println(rt.getName());
				}
			}
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				if (4 == analyse(st)) {
					st.setStop(true);
				}
			}
		}
	}
}
