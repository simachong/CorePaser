package com.cricetulu.analyse.flow;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.circetulu.core.block.Block;
import com.circetulu.core.block.Routine;
import com.circetulu.core.block.Section;
import com.circetulu.core.block.Sentence;
import com.cricetulu.analyse.node.Node;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.CallSTM;
import com.cricetulu.core.module.EvaluateSTM;
import com.cricetulu.core.module.GotoSTM;
import com.cricetulu.core.module.IfSTM;
import com.cricetulu.core.module.PerformSTM;

public class FlowGenerator {

	 
	private ArrayList<Block> blksIter;
	private LinkedHashMap<String, Node> nodeMap = new LinkedHashMap<String, Node>();
	private LinkedHashMap<String, Block> blkMap = new LinkedHashMap<String, Block>();
	private LinkedHashMap<String, Routine> routineIndex;
	private LinkedHashMap<String, Section> sectionIndex;
		
	public FlowGenerator(ArrayList<Block> blksIter, LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
			
		this.routineIndex = routineIndex;
		this.sectionIndex = sectionIndex;
		this.blksIter = blksIter;
	}
		
	private void buildFlow(ArrayList<AST> ast, Node node) {
			
		for (AST iter : ast) {
				
			if (iter instanceof IfSTM) {

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
		
	private void buildFlow(Sentence st, Node node) {
			
		ArrayList<AST> ast = st.getAst().getAsts();
		buildFlow(ast, node);
	}
		
	
	// wide
	public void buildFlowByWide(LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
		
		Node node = new Node("START");
		nodeMap.put("START", node);
		
		for (Block blk : blksIter) {
			
			// Section
			if (blk instanceof Section) {
				
				Section sec = (Section)blk;
				Node blkNd = new Node(sec.getName());
				node.setNextNode(blkNd);
			}
			
			// Routine
			if (blk instanceof Routine) {
							
				Routine rt = (Routine)blk;
				Node rtNd = new Node(rt.getName());
				node.setNextNode(rtNd);
				if (rt.isEnd()) {
					
					break;
				}
			}
			
			// Sentence
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				if (st.toString().equals("STOP RUN")) {
						
					break;
				}
			}
		}
	}
	
	// deep
	public void buildFlowByDeep(LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
			
		Node node = new Node("START");
		nodeMap.put("START", node);
		
		for (Block blk : blksIter) {
				
			// Section
			if (blk instanceof Section) {
					
				Section sec = (Section)blk;
				Node nodeSecblk = new Node(sec.getName());
				nodeMap.put(sec.getName(), nodeSecblk);
				
				ArrayList<Block> secblks = sec.getBlks();
				for (Block secblk : secblks) {
						
					// Section Contains Routine
					if (secblk instanceof Routine) {
							
						Routine rt = (Routine)secblk;
						ArrayList<Block> rtblks = rt.getSentences();

						Node nodeSecRtblk = new Node(rt.getName());
						nodeMap.put(rt.getName(), nodeSecRtblk);
						for (Block rtblk : rtblks) {
							
							// Sentence
							if (rtblk instanceof Sentence) {

								Sentence st = (Sentence)rtblk;
								buildFlow(st, nodeSecRtblk);
								if (st.toString().equals("STOP RUN")) {
										
									return;
								}
							}
						}
					}
						
					// Section Contains Sentence
					if (secblk instanceof Sentence) {
							
						Sentence st = (Sentence)secblk;
						buildFlow(st, nodeSecblk);
						if (st.toString().equals("STOP RUN")) {
								
							return;
						}
					}
				}
			}
			
			// Routine
			if (blk instanceof Routine) {
				
				Routine rt = (Routine)blk;
				ArrayList<Block> rtblks = rt.getSentences();
				
				Node nodeRtlk = new Node(rt.getName());
				nodeMap.put(rt.getName(), nodeRtlk);
					
				for (Block rtblk : rtblks) {
						
					// Sentence
					if (rtblk instanceof Sentence) {
							
						Sentence st = (Sentence)rtblk;
						buildFlow(st, nodeRtlk);
						if (st.toString().equals("STOP RUN")) {
								
							return;
						}
					}
				}
					
				if (!rt.isEnd()) {
					System.out.println(rt.getName());
				}
			}
				
			// Sentence
			if (blk instanceof Sentence) {
					
				Sentence st = (Sentence)blk;
				//buildStack(st);
				if (st.toString().equals("STOP RUN")) {
						
					return;
				}
			}
		}
	}
}
