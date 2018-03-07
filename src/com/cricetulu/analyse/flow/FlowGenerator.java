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
import com.cricetulu.core.module.EvaluateSTM;
import com.cricetulu.core.module.GotoSTM;
import com.cricetulu.core.module.IfSTM;
import com.cricetulu.core.module.PerformSTM;
import com.cricetulu.core.utils.FileOperation;

public class FlowGenerator {

	 
	private ArrayList<Block> blksIter;
	private LinkedHashMap<String, Node> nodeMap = new LinkedHashMap<String, Node>();
	private LinkedHashMap<String, Routine> routineIndex;
	private LinkedHashMap<String, Section> sectionIndex;
	private Queue<Block> queue;
	private String text = "";
	private int condCount = 0;
		
	public FlowGenerator(ArrayList<Block> blksIter, LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
			
		this.queue = new LinkedList<Block>();
		this.routineIndex = routineIndex;
		this.sectionIndex = sectionIndex;
		this.blksIter = blksIter;
	}
	
	public void printNode(Node node, String conditions) {
		
		while (!node.getNextNodes().isEmpty() && !node.isPrint()) {
			
			if (node instanceof Condition) {
				
				Condition cond = (Condition)node;
				
				if (cond.getConditionType().equals("IF")) {
					
					for (Node nd : cond.getNextNodes()) {
						String cds = nd.getDesc();
						if (!cds.isEmpty()) {
							cds = "|" + cds + "|";
						}
						text += node.getNodeName() + " --> " + cds + nd.getNodeName() + "\n";
						cds = "";
						printNode(nd, "");
					}
				}
				else if (cond.getConditionType().equals("EVALUATE")) {
					// TODO
				}
				
			}
			else {
				if (!conditions.isEmpty()) {
					conditions = "|" + conditions + "|";
				}
				text += node.getNodeName() + " --> " + conditions + node.getNextNodes().get(0).getNodeName() + "\n";
				conditions = "";
			}
			node.setPrint(true);
			node = node.getNextNodes().get(0);
			
		}
	}
	
	public void printNode() {
		
		Iterator<Entry<String, Node>> iter = nodeMap.entrySet().iterator();
		while(iter.hasNext()) {
			
			text = "";
			Entry<String, Node> entry = iter.next();
			Node node = entry.getValue();
			System.out.println(node.getNodeName() + " is Printing");
			printNode(node, "");
			FileOperation.createFile(".." + File.separator + node.getNodeName(), text);
		}
		//Node node = nodeMap.get("MAIN-LINE-LOGIC");
		//printNode(node, "");
		
		
	}
	
	private Node rtFlowWide(Routine rt, Node node, String desc) {
		
		ArrayList<Block> rtblks = rt.getSentences();
		Node Iter = node;
		for (Block blk : rtblks) {
			
			if (blk instanceof Sentence) {
				
				Sentence st = (Sentence)blk;
				Iter = buildFlow(st.getAst(), Iter, desc);
			}
		}
		return node;
	}
	
	private Node secFlowWide(Section sec, Node node, String desc) {
		
		ArrayList<Block> secblks = sec.getBlks();
		// Section
		for (Block secblk : secblks) {
									
			// Section Contains Routine
			if (secblk instanceof Routine) {
										
				Routine rt = (Routine)secblk;
				
				if (!nodeMap.containsKey(rt.getName())) {
					queue.offer(rt);
					Node rtNd = new Node(rt.getName());
					rtNd.setDesc(desc);
					Node rtNd2 = new Node(rt.getName());
					node.getNextNodes().add(rtNd);
					nodeMap.put(rtNd.getNodeName(), rtNd2);
					node = rtNd;
				}
				else {
					Node nodert = nodeMap.get(rt.getName());
					nodert.setDesc(desc);
					node.getNextNodes().add(nodert);
					node = nodert;
				}
			}						
			// Sentence
			if (secblk instanceof Sentence) {
				
				Node Iter = node;
				Sentence st = (Sentence)secblk;
				Iter = buildFlow(st.getAst(), Iter, "");
			}
			
		}
		return node;
	}
		
	private Node buildFlow(AST ast, Node node, String desc) {
		
		ArrayList<AST> asts = ast.getAsts();
		for (AST iter : asts) {
				
			if (iter instanceof IfSTM) {

				condCount++;
				IfSTM ifStm = (IfSTM)iter;
				AST ifAst = ifStm.getIfStm();
				AST elseAst = ifStm.getElseStm();
				AST condition = ifStm.getConditions();
				//Condition cond = new Condition(condition.toString() + ":" + condCount);
				//cond.setIfCondition(condition.toString() + ":" + condCount);
				Condition cond = new Condition("CONDITION:" + condCount);
				cond.setIfCondition("CONDITION:" + condCount);
				cond.setConditionType("IF");
				cond.setDesc(desc);
				
				Node iter1 = cond;
				if (!ifAst.getAsts().isEmpty()) {
					
					iter1 = buildFlow(ifAst, iter1, "TRUE");
				}
				
				Node iter2 = cond;
				if (!elseAst.getAsts().isEmpty()) {
					
					iter2 = buildFlow(elseAst, iter2, "FALSE");
				}
				
				if (iter1 != cond || iter2 != cond) {
					
					Node cd = new Node("Condition-" + condCount + "-End");
					node.getNextNodes().add(cond);
					if (!iter1.getNodeName().equals(cd.getNodeName())) {
						iter1.getNextNodes().add(cd);
					}
					if (!iter2.getNodeName().equals(cd.getNodeName())) {
						iter2.getNextNodes().add(cd);
					}
					node = cd;
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
				if (from != null && !from.equals("CCSI-ABEND")) {
					node = jumpTo(from, node, desc);
				}
			}
			else if (iter instanceof GotoSTM) {
				
				GotoSTM gstm = (GotoSTM)iter;
				String to = gstm.getTo();
				if (to != null && !to.equals("CCSI-ABEND")) {
					node = jumpTo(to, node, desc);
				}
			}
			else if (iter instanceof CallSTM) {
					
				CallSTM cstm = (CallSTM)iter;
				String proc = cstm.getProg();
				Node pnode= new Node(proc);
				pnode.setDesc(desc);
				node.getNextNodes().add(pnode);
				node = pnode;
			}
		}
		return node;
	}
	
	private Node jumpTo(String from, Node node, String desc) {
		
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
				Node rtNd2 = new Node(from);
				Node rtNd = new Node(from);
				rtNd.setDesc(desc);
				node.getNextNodes().add(rtNd);
				nodeMap.put(rtNd.getNodeName(), rtNd2);
				node = rtNd;
			}
			else {
				Node nodert = new Node(from);
				nodert.setDesc(desc);
				node.getNextNodes().add(nodert);
				node = nodert;
			}
		}
		return node;
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
				node.getNextNodes().add(blkNd);
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
				node.getNextNodes().add(rtNd);
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
					node.getNextNodes().add(new Node("STOP RUN"));
					
					break;
				}
			}	
		}
		while (!queue.isEmpty()) {
			
			Block blkq = queue.poll();
			if (blkq instanceof Section) {
				
				Section secq = (Section)blkq;
				Node qNd = nodeMap.get(secq.getName());
				secFlowWide(secq, qNd, "");
			}
			else if (blkq instanceof Routine) {
				
				Routine rtq = (Routine)blkq;
				Node qNd= nodeMap.get(rtq.getName());
				rtFlowWide(rtq, qNd, "");
			}
		}
	}
	
	// deep
	public void buildFlowByDeep(LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
	
	}
}
