package com.cricetulu.analyse.flow;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

import com.circetulu.core.block.Block;
import com.circetulu.core.block.Routine;
import com.circetulu.core.block.Section;
import com.circetulu.core.block.Sentence;
import com.cricetulu.analyse.node.Condition;
import com.cricetulu.analyse.node.Node;
import com.cricetulu.core.module.AST;
import com.cricetulu.core.module.CallSTM;
import com.cricetulu.core.module.DataStorage;
import com.cricetulu.core.module.EvaluateSTM;
import com.cricetulu.core.module.GotoSTM;
import com.cricetulu.core.module.IfSTM;
import com.cricetulu.core.module.Item88;
import com.cricetulu.core.module.PerformSTM;
import com.cricetulu.core.utils.FileOperation;

public class FlowGenerator {

	 
	private ArrayList<Block> blksIter;
	private LinkedHashMap<String, Node> nodeMap = new LinkedHashMap<String, Node>();
	private LinkedHashMap<String, Routine> routineIndex;
	private LinkedHashMap<String, Section> sectionIndex;
	private Queue<Block> queue;
	private String text = "";
		
	public FlowGenerator(ArrayList<Block> blksIter, LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
			
		this.queue = new LinkedList<Block>();
		this.routineIndex = routineIndex;
		this.sectionIndex = sectionIndex;
		this.blksIter = blksIter;
	}
	
	public void printNode(Node node, String conditions) {
		
		while (node.getNextNode() != null) {
			
			if (node instanceof Condition) {
				
				Condition cond = (Condition)node;
				if (cond.getConditionType().equals("IF")) {
					
					printNode(cond.getLeftTrue(), "TRUE");
					printNode(cond.getRightFalse(), "FALSE");
				}
				else if (cond.getConditionType().equals("EVALUATE")) {
					// TODO
				}
			}
			else {
				if (!conditions.isEmpty()) {
					conditions = "|" + conditions + "|";
				}
				text += node.getNodeName() + " --> " + conditions + node.getNextNode().getNodeName();
				node = node.getNextNode();
			}
		}
	}
	
	public void printNode() {
		text = "";
//		Iterator<Entry<String, Node>> iter = nodeMap.entrySet().iterator();
//		while(iter.hasNext()) {
//			
//			Entry<String, Node> entry = iter.next();
//			Node node = entry.getValue();
//			printNode(node, "");
//		}
		Node node = nodeMap.get("MAIN-LINE-LOGIC");
		printNode(node, "");
		
		FileOperation.createFile(".." + File.separator + "CPD110_routine_flow", text);
	}
	
	private Node rtFlowWide(Routine rt, Node node) {
		
		ArrayList<Block> rtblks = rt.getSentences();
		for (Block blk : rtblks) {
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				node = buildFlow(st.getAst(), node);
			}
		}
		return node;
	}
	
	private Node secFlowWide(Section sec, Node node) {
		
		ArrayList<Block> secblks = sec.getBlks();
		// Section
		for (Block secblk : secblks) {
									
			// Section Contains Routine
			if (secblk instanceof Routine) {
										
				Routine rt = (Routine)secblk;
				
				if (!nodeMap.containsKey(rt.getName())) {
					queue.offer(rt);
					Node rtNd = new Node(rt.getName());
					node.setNextNode(rtNd);
					nodeMap.put(rtNd.getNodeName(), rtNd);
					node = rtNd;
				}
				else {
					Node nodert = nodeMap.get(rt.getName());
					node.setNextNode(nodert);
					node = nodert;
				}
			}						
			// Sentence
			if (secblk instanceof Sentence) {
				
				Sentence st = (Sentence)secblk;
				node = buildFlow(st.getAst(), node);
			}
			
		}
		return node;
	}
		
	private Node buildFlow(AST ast, Node node) {
		
		ArrayList<AST> asts = ast.getAsts();
		for (AST iter : asts) {
				
			if (iter instanceof IfSTM) {

				IfSTM ifStm = (IfSTM)iter;
				AST ifAst = ifStm.getIfAst();
				AST elseAst = ifStm.getElseStm();
				AST condition = ifStm.getConditions();
				Condition cond = new Condition(condition.toString());
				cond.setIfCondition(condition.toString());
				cond.setConditionType("IF");
				
				if (!ifAst.getAsts().isEmpty()) {
				
					node = buildFlow(ifAst, cond.getLeftTrue());
				}
				
				if (!elseAst.getAsts().isEmpty()) {
					
					node = buildFlow(ifAst, cond.getLeftTrue());
				}
				
				if (cond.getLeftTrue().getNextNode() != null || cond.getRightFalse().getNextNode() != null) {
					
					node.setNextNode(cond);
					node = cond;
				}
			}
			else if (iter instanceof EvaluateSTM) {
					
//				EvaluateSTM estm = (EvaluateSTM)iter;
//				AST conditions = estm.getConditionsDs();
//				Condition cond = new Condition(conditions.toString());
//				cond.setConditionType("EVALUATE");
//				ArrayList<String> whenConditions = cond.getWhenCondition();
//				ArrayList<Node> whenNode = cond.getWhens();
//				estm.get
			}
			else if (iter instanceof PerformSTM) {
				
				PerformSTM pstm = (PerformSTM)iter;
				String from = pstm.getFrom();
				node = jumpTo(from, node);
				
			}
			else if (iter instanceof GotoSTM) {
				
				GotoSTM gstm = (GotoSTM)iter;
				String to = gstm.getTo();
				node = jumpTo(to, node);
			}
			else if (iter instanceof CallSTM) {
					
				CallSTM cstm = (CallSTM)iter;
				String proc = cstm.getProg();
				Node pnode= new Node(proc);
				node.setNextNode(pnode);
			}
		}
		return node;
	}
	
	private Node jumpTo(String from, Node node) {
		
		Block blk = null;
		if (routineIndex.containsKey(from)) {
			
			blk = routineIndex.get(from);
		}
		else if (sectionIndex.containsKey(from)) {
			
			blk = sectionIndex.get(from);
		}
		
		
		if (blk != null) {
		
			if (!nodeMap.containsKey(from)) {
				queue.offer(blk);
				Node rtNd = new Node(from);
				node.setNextNode(rtNd);
				nodeMap.put(rtNd.getNodeName(), rtNd);
				node = rtNd;
			}
			else {
				Node nodert = nodeMap.get(from);
				node.setNextNode(nodert);
				node = nodert;
			}
		}
		return node;
	}
		
	private void buildFlow(Sentence st, Node node) {
			
		buildFlow(st.getAst(), node);
	}
		
	
	// wide
	public void buildFlowByWide() {
		
		Node node = new Node("START");
		nodeMap.put("START", node);
		
		for (Block blk : blksIter) {
			
			// Section
			if (blk instanceof Section) {
				
				Section sec = (Section)blk;
				Node blkNd = new Node(sec.getName());
				node.setNextNode(blkNd);
				node = blkNd;
				queue.offer(sec);
				nodeMap.put(blkNd.getNodeName(), blkNd);
				if (sec.isStop()) {
					break;
				}
			}
			
			// Routine
			if (blk instanceof Routine) {
							
				Routine rt = (Routine)blk;
				queue.offer(rt);
				Node rtNd = new Node(rt.getName());
				node.setNextNode(rtNd);
				nodeMap.put(rtNd.getNodeName(), rtNd);
				node = rtNd;
				if (rt.isStop()) {
					break;
				}
			}
			
			// Sentence
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				if (st.isStop()) {
					node.setNextNode(new Node("STOP RUN"));
					
					break;
				}
			}	
		}
		while (!queue.isEmpty()) {
			
			Block blkq = queue.poll();
			if (blkq instanceof Section) {
				
				Section secq = (Section)blkq;
				Node qNd = nodeMap.get(secq.getName());
				secFlowWide(secq, qNd);
			}
			else if (blkq instanceof Routine) {
				
				Routine rtq = (Routine)blkq;
				Node qNd= nodeMap.get(rtq.getName());
				rtFlowWide(rtq, qNd);
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
