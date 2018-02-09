package com.cricetulu.core.paser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.cricetulu.core.expression.Expression;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.utils.FileOperation;

public class Lexer {
	
	private Expression exp = null;
	private String currSentence = "";
	private String currExpStr = "";
	public StringBuffer firstScan(String fileName) {
		
		StringBuffer sb = new StringBuffer();

		try {
			Scanner sc = new Scanner(new File(fileName));
			int lineNum = 1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() > 6) {
					if (line.charAt(6) != '*' && line.charAt(6) != '/') {
						currSentence += line;
						String copy = lexCopy(lineNum);
						if (!copy.isEmpty()) {
							
							currSentence = currSentence.replace(currExpStr, copy);
							line = currSentence;
						}
						sb.append(line);
						sb.append("\n");
					}
				}
				++lineNum;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
		FileOperation.createFile("D:\\CPD110_12", sb.toString());
		return sb;
	}
	
	public String lexCopy(int lineNum) {
		
		String [] tokens = currSentence.split(" ");
		int len = tokens.length;
		if (tokens[len - 1].endsWith(".")) {
			
			
			for (int i = 0; i < len; ++i) {
				
				tokens[i] = tokens[i].trim();
				if (tokens[i].equalsIgnoreCase("COPY")) {
					currExpStr = "";
					currExpStr += tokens[i];
					exp = GlobalDef.expressions.get(tokens[i].toUpperCase());				
					
				}
				//currExpStr += " " + tokens[i];
				if (exp != null && exp.getEndInd().contains(tokens[i])) {
					
					if (i == len) {
						currSentence = "";
					}
					
					String expand = exp.execute(currExpStr, lineNum);
					currExpStr = "";
					return expand;
				}
				
				if (tokens[i].endsWith(".")) {
					currSentence = "";
				}
			}
		}
		return "";
	}
	
	public void lex(int lineNum) {
		
		String [] tokens = currSentence.split(" ");
		int len = tokens.length;
		if (tokens[len - 1].endsWith(".")) {
			
			String expStr = "";
			for (int i = 0; i < len; ++i) {
				
				if (GlobalDef.expressions.containsKey(tokens[i])) {
					expStr = "";
					expStr += tokens[i];
					exp = GlobalDef.expressions.get(tokens[i]);				
					
				}
				expStr += tokens[i];
				if (exp != null && exp.getEndInd().contains(tokens[i])) {
					
					if (i == len) {
						currSentence = "";
					}
					exp.execute(expStr, lineNum);
				}
			}
		}
	}

}
