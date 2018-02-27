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
	
	private int analyse(Sentence st) {
		
		
		ArrayList<Token> tokens = st.getTokens();
		
		if (tokens.size() > 2) {
			
			Expression exp = GlobalDef.expressions.get(tokens.get(0).getTokenName());
			if (exp != null) {
				exp.init();
				if (1 == exp.execute(st.getAst(), st, 0)) {
					exp.clear();
					return 1;
				}
			}
			else {
				System.out.println(tokens.get(0).getTokenName());
			}
			exp.clear();
		}
		
		return 0;
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
					
					if (secblk instanceof Sentence) {
						
						Sentence st = (Sentence)secblk;
						analyse(st);
					}
				}
			}
			
			if (blk instanceof Routine) {
				
				Routine rt = (Routine)blk;
				ArrayList<Block> rtblks = rt.getSentences();
				
				for (Block rtblk : rtblks) {
					
					if (rtblk instanceof Sentence) {
						
						Sentence st = (Sentence)rtblk;
						analyse(st);
					}
				}
				
				if (!rt.isEnd()) {
					System.out.println(rt.getName());
				}
			}
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				analyse(st);
			}
		}
	}
}
