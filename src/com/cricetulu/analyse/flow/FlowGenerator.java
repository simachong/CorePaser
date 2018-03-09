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
	private LinkedHashMap<String, String> condMap = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, Node> cNodeMap = new LinkedHashMap<String, Node>();
	private LinkedHashMap<String, Node> secnodeMap;
	private LinkedHashMap<String, Node> rtnodeMap;
	private LinkedHashMap<String, Routine> routineIndex;
	private LinkedHashMap<String, Section> sectionIndex;
	private Queue<Block> queue;
	private String text = "";
	private String contxt = "";
	private int condCount = 0;
	
		
	public FlowGenerator(ArrayList<Block> blksIter, LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
			
		this.queue = new LinkedList<Block>();
		this.routineIndex = routineIndex;
		this.sectionIndex = sectionIndex;
		this.blksIter = blksIter;
	}
	
	public void printCondtions() {
		
		String txt = "";
		Iterator<Entry<String, String>> iter = condMap.entrySet().iterator();
		while(iter.hasNext()) {
			
			Entry<String, String> entry = iter.next();
			txt += entry.getKey() + "," + entry.getValue() + "\n";
		}
		FileOperation.createFile(".." + File.separator + "Condtions.txt", txt);
	}
	
	public void printNode(Node node, String conditions) {
		
		while (!node.getNextNodes().isEmpty() && !node.isPrint()) {
			
			if (node instanceof Condition) {
				
				Condition cond = (Condition)node;
				
				if (cond.getConditionType().equals("IF") || cond.getConditionType().equals("EVALUATE")) {
					
					if (cond.getConditionType().equals("EVALUATE")) {
						System.out.println();
					}
					contxt += "click " + cond.getCondition() + " callback " + "\"" 
					+ condMap.get(node.getNodeName()) + "\"\r\n";
					
					for (Node nd : cond.getNextNodes()) {
						String cds = nd.getDesc();
						if (!cds.isEmpty()) {
							cds = "|" + cds + "|";
						}
						String seq = "";
						if (0 != nd.getSeq()) {
							
							seq = ":" + nd.getSeq();
						}
						text += node.getNodeName() + " --> " + cds + nd.getNodeName() + seq + "\n";
						cds = "";
						printNode(nd, "");
					}
				}				
			}
			else {
				if (!conditions.isEmpty()) {
					conditions = "|" + conditions + "|";
				}
				
				String seqL = "";
				if (0 != node.getSeq()) {
					
					seqL = ":" + node.getSeq();
				}
				
				String seqR = "";
				if (0 != node.getNextNodes().get(0).getSeq()) {
					
					seqR = ":" + node.getNextNodes().get(0).getSeq();
				}
				text += node.getNodeName() + seqL + " --> " + conditions 
						+ node.getNextNodes().get(0).getNodeName() + seqR + "\n";
				conditions = "";
			}
			node.setPrint(true);
			node = node.getNextNodes().get(0);
			
		}
	}
	
	public void printNode() {
		
		Iterator<Entry<String, Node>> iter = nodeMap.entrySet().iterator();
		while(iter.hasNext()) {
			
			text = "```mermaid\r\n" + 
					"   graph TD\r\n";
			contxt = "";
			Entry<String, Node> entry = iter.next();
			Node node = entry.getValue();
			//System.out.println(node.getNodeName() + " is Printing");
			printNode(node, "");
			contxt = contxt.replace("<=", "< =");
			FileOperation.createFile(".." + File.separator + node.getNodeName() + ".md", text + contxt);
		}
	}
	
	private Node rtFlowWide(Routine rt, Node node, String desc) {
		
		rtnodeMap = new LinkedHashMap<String, Node>();
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
				cond.setCondition("CONDITION:" + condCount);
				
				condMap.put("CONDITION:" + condCount, condition.toString());
				cond.setConditionType("IF");
				cond.setDesc(desc);
				cond.setCondition(true);
				
				Node iter1 = cond;
				if (!ifAst.getAsts().isEmpty()) {
					
					iter1 = buildFlow(ifAst, iter1, "TRUE");
				}
				
				Node iter2 = cond;
				if (!elseAst.getAsts().isEmpty()) {
					
					iter2 = buildFlow(elseAst, iter2, "FALSE");
				}
				
				if (iter1 != cond || iter2 != cond) {
					
					Node cd = null;
					if (!cNodeMap.containsKey("Condition-" + condCount + "-End")) {
						
						cd = new Node("Condition-" + condCount + "-End");
						cNodeMap.put("Condition-" + condCount + "-End", cd);
					}
					else {
						cd = cNodeMap.get("Condition-" + condCount + "-End");
					}
					
					cd.setCondition(true);
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
					
				condCount++;
				EvaluateSTM estm = (EvaluateSTM)iter;
				AST evalCond = estm.getConditionsDs();
				AST whenCond = estm.getWhenCondition();
				AST whenStm = estm.getWhenStm();
				
				Condition cond = new Condition("EVALUATE:" + condCount);
				condMap.put("EVALUATE:" + condCount, evalCond.getTokens().toString());
				cond.setConditionType("EVALUATE");
				cond.setDesc(desc);
				cond.setCondition(true);
				cond.setCondition("EVALUATE:" + condCount);
				
				ArrayList<AST> whenCondAsts = whenCond.getAsts();
				ArrayList<AST> whenStmAsts = whenStm.getAsts();
				Node iterW = null;
				boolean firstWhen = true;
				for (int i = 0; i < whenStmAsts.size(); ++i) {
					
					AST astW = whenStmAsts.get(i);
					iterW = cond;
					iterW = buildFlow(astW, iterW, whenCondAsts.get(i).toString());
			
					if (iterW != cond) {
						
						if (firstWhen) {
							node.getNextNodes().add(cond);	
						}
						
						Node cd = null;
						if (!cNodeMap.containsKey("EVALUATE-" + condCount + "-End")) {
							
							cd = new Node("EVALUATE-" + condCount + "-End");
							cNodeMap.put("EVALUATE-" + condCount + "-End", cd);
						}
						else {
							cd = cNodeMap.get("EVALUATE-" + condCount + "-End");
						}
						iterW.getNextNodes().add(cd);
						if (firstWhen) {
							node = cd;
							firstWhen = false;
						}
					}
				}
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
					node = jumpTo(to, node, desc + "GO TO");
				}
			}
			else if (iter instanceof CallSTM) {
					
				CallSTM cstm = (CallSTM)iter;
				String proc = cstm.getProg();
				
				
				Node mnode = new Node(proc);
				Node pnode = new Node(proc);
				if (!rtnodeMap.containsKey(proc)) {

					mnode.addSeq();
					rtnodeMap.put(proc, mnode);
				}
				else {
					
					pnode.setSeq(rtnodeMap.get(proc).getSeq());
					rtnodeMap.get(proc).addSeq();
				}
				
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
		
		Node mnode = new Node(from);
		Node pnode = new Node(from);
		if (!rtnodeMap.containsKey(from)) {

			mnode.addSeq();
			rtnodeMap.put(from, mnode);
		}
		else {
				
			if (!desc.contains("GO TO")) {
				pnode.setSeq(rtnodeMap.get(from).getSeq());
				rtnodeMap.get(from).addSeq();
			}
		}
			
		if (!nodeMap.containsKey(from)) {
			
			//TODO tmp solution
			if (blk != null) {
				queue.offer(blk);
			}
			
			Node rtNd2 = new Node(from);
			pnode.setDesc(desc);
			node.getNextNodes().add(pnode);
			nodeMap.put(from, rtNd2);
			node = pnode;
		}
		else {
			pnode.setDesc(desc);
			node.getNextNodes().add(pnode);
			node = pnode;
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
				Node qNd = null;
				if (nodeMap.containsKey(rtq.getName())) {
					qNd = nodeMap.get(rtq.getName());
				}
				else {
					qNd = new Node(rtq.getName());
					nodeMap.put(rtq.getName(), qNd);
				}
				rtFlowWide(rtq, qNd, "");
				Node tmp = qNd;
				while (tmp != null && !tmp.getNextNodes().isEmpty()) {
					
					tmp = tmp.getNextNodes().get(0);
				}
				if (!rtq.isEnd()) {
					System.out.println(rtq.getName() + "xxx");
					
					
					tmp.getNextNodes().add(new Node("NEXT-ROUTINE:" + rtq.getNextRoutine().getName()));
					queue.offer(rtq.getNextRoutine());
				}
				tmp.getNextNodes().add(new Node("..."));
			}
		}
	}
	
	// deep
	public void buildFlowByDeep(LinkedHashMap<String, Routine> routineIndex, LinkedHashMap<String, Section> sectionIndex) {
	
		// TODO
	}
}
