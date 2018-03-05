package com.cricetulu.core.paser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.circetulu.core.block.Block;
import com.circetulu.core.block.Routine;
import com.circetulu.core.block.Section;
import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.analyse.Condition;
import com.cricetulu.analyse.Node;
import com.cricetulu.core.expression.Expression;
import com.cricetulu.core.expression.IF;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.global.Index;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.CallSTM;
import com.cricetulu.core.module.EvaluateSTM;
import com.cricetulu.core.module.GotoSTM;
import com.cricetulu.core.module.IfSTM;
import com.cricetulu.core.module.PerformSTM;

public class HandleStackProc {

	private ArrayList<Block> blksIter;
	private LinkedHashMap<String, Node> nodeMap;
	
	public HandleStackProc(ArrayList<Block> blksIter) {
		
		this.blksIter = blksIter;
	}
	
	private void buildStack(ArrayList<AST> ast, Node node) {
		
		for (AST iter : ast) {
			
			if (iter instanceof IfSTM) {
				
				IfSTM ifS = (IfSTM)iter;
				Condition cd = new Condition();
				cd.setIfCondition(ifS.toString());
				node.setNextNode(cd);
				cd.setConditionType("IF");
				
				buildStack(ifS.getIfStm().getAsts(), cd.getLeftTrue());
				buildStack(ifS.getElseStm().getAsts(), cd.getRightFalse());
			}
			else if (iter instanceof EvaluateSTM) {
				
			}
			else if (iter instanceof PerformSTM) {
				
			}
			else if (iter instanceof GotoSTM) {
				
			}
			else if (iter instanceof CallSTM) {
				
			}
		}
	}
	
	private Node buildStack(Sentence st, Node node) {
		
		ArrayList<AST> ast = st.getAst().getAsts();
		buildStack(ast, node);
	}
	
	public void printStack(LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
		
		Node node = new Node("START");
		
		for (Block blk : blksIter) {
			
			if (blk instanceof Section) {
				
				Section sec = (Section)blk;
				ArrayList<Block> secblks = sec.getBlks();
				for (Block secblk : secblks) {
					
					if (secblk instanceof Routine) {
						
						Routine rt = (Routine)secblk;
						ArrayList<Block> rtblks = rt.getSentences();
						Sentence st = (Sentence)rtblk;
						Node nodeSecblk = new Node();
						node.setNextNode(nodeSecblk);
						
						for (Block rtblk : rtblks) {
							
							if (rtblk instanceof Sentence) {
								
								
								buildStack(st, nodeSecblk);
								if (st.toString().equals("STOP RUN")) {
									
									return;
								}
							}
						}
					}
					
					if (secblk instanceof Sentence) {
						
						Sentence st = (Sentence)secblk;
						buildStack(st);
						if (st.toString().equals("STOP RUN")) {
							
							return;
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
						buildStack(st);
						if (st.toString().equals("STOP RUN")) {
							
							return;
						}
					}
				}
				
				if (!rt.isEnd()) {
					System.out.println(rt.getName());
				}
			}
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				buildStack(st);
				if (st.toString().equals("STOP RUN")) {
					
					return;
				}
			}
		}
	}
	
	private int analyse(Sentence st) {
		
		ArrayList<Token> tokens = st.getTokens();
		
		if (tokens.size() > 2) {
			
			Expression exp = GlobalDef.expressions.get(tokens.get(0).getTokenName());
			if (exp != null) {
				exp.init();
				Index i = new Index(0);
				if (1 == exp.execute(st.getAst(), st, i)) {
					exp.clear();
					return 1;
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
								analyse(st);
							}
						}
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
