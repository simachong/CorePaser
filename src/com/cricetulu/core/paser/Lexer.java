package com.cricetulu.core.paser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.utils.FileOperation;

public class Lexer {
	
	//private LinkedHashMap<Integer, String> code = new LinkedHashMap<Integer, String>();
	private ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	public void scan(String fileName, LinkedHashMap<Integer, String> code) {

		try {
			Scanner sc = new Scanner(new File(fileName));
			int lineNum = 1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() > 6) {
					if (line.charAt(6) != '*' && line.charAt(6) != '/') {
						code.put(lineNum, line);
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
		firstScanCode(code);
	}
	
	public void printCode() {
		
	}
	
	public void printSentences(String file) {
	
		String sentenceCode = "";
		for (Sentence sent : sentences) {
			sentenceCode += sent.toString();
			sentenceCode += "\n";
		}
		FileOperation.createFile(file, sentenceCode);
	}
	
	private void firstScanCode(LinkedHashMap<Integer, String> code) {
		
		Iterator<Entry< Integer, String>> iter = code.entrySet().iterator();
		int sentencNum = 1;
	
		Sentence sentence = new Sentence();
		while(iter.hasNext()) {
			
			Entry<Integer, String> entry = iter.next();
			String line = entry.getValue();
			String [] tokens = line.split(" ");
			
			for (int i = 0; i < tokens.length; ++ i) {
				
				boolean isEnd = false;
				String tk = tokens[i].trim().toUpperCase();
				
				if (tk.trim().isEmpty() || tk.equals(" ")) {
					continue;
				}
				
				if (tk.endsWith(".")) {
					isEnd = true;
					//tk = tk.substring(0, tk.length() - 1);
					//System.out.println(tk);
				}
				int id = GlobalDef.isKeyword(tk);
				Token token = new Token(tk.trim(), id, id == -1 ? false:true);
				sentence.getTokens().add(token);
				
				if (isEnd) {
					
					String expName = sentence.getTokens().get(0).getTokenName();
					if (expName.equalsIgnoreCase("COPY")) {
						expandCopy(sentence, sentencNum);
					}
					else {
						if (!expandInsideCopy(sentence, sentencNum)) {
							sentences.add(sentence);
						}
					}
					sentence = new Sentence();
					++sentencNum;
				}
			}
		}		
	}
	
	private boolean expandInsideCopy(Sentence sentence, int sentencNum) {
		
		Sentence fdSentence = new Sentence();
		boolean hasCopy = false;
		int copyStart = 0;

		for (int i = 0; i < sentence.getTokens().size(); ++i) {
			
			Token token = sentence.getTokens().get(i);
			if (token.getTokenName().trim().toUpperCase().equals("COPY")) {

				hasCopy = true;
				copyStart = i;
				break;
			}
			else {
				fdSentence.getTokens().add(token);
			}
		}
		
		if (hasCopy) {
			//System.out.println(sentence);
			//fdSentence.getTokens().add(new Token(".", GlobalDef.isKeyword("."), false));
			//System.out.println(fdSentence);
			sentences.add(fdSentence);
			ArrayList<Token> tokens = new ArrayList<Token>();
			for (int i = copyStart; i < sentence.getTokens().size(); ++i) {
				tokens.add(sentence.getTokens().get(i));
				//System.out.println(tokens.get(i).getTokenName());
			}
			sentence.setTokens(tokens);

			expandCopy(sentence, sentencNum);
		}
		return hasCopy;
		
	}
	
	private void expandCopy(Sentence sentence, int sentencNum) {
		String copy = "";
		LinkedHashMap<Integer, String> code = new LinkedHashMap<Integer, String>();
		
		if (sentence.getTokens().size() < 2) {
			// �׳��쳣
			System.out.println("COPY EXP ERROR! line:" + sentencNum + "line is:" + sentence.toString());
			return;
		}
		String tk = sentence.getTokens().get(1).getTokenName().trim();
		if (tk.endsWith(".")) {
			
			tk = tk.substring(0, tk.length() - 1);
		}
		//System.out.println(sentence.toString());
		String fileName = GlobalDef.COPYBOOK_PATH + tk.toUpperCase();
		//System.out.println(fileName);
		String tmpFileName = GlobalDef.COPYBOOK_PATH + fileName + ".tmp";
		//System.out.println(tmpFileName);
		copy = PreProccess.preProcess(fileName, tmpFileName).toString();
		int len = sentence.getTokens().size();
		if (len >= 6) {
			
			while (len >= 6) {
				// Ч�ʵ�
				String a = sentence.getTokens().get(len - 3).getTokenName().trim().toUpperCase();
				String b = sentence.getTokens().get(len - 1).getTokenName().trim().toUpperCase();
				copy = copy.replaceAll(trim(a, "==")
							, trim(b, "=="));
				GlobalDef.nameSpaceMapping.put(a, b);
				GlobalDef.nameSpaceMapping.put(b, a);
				len -= 3;
				//System.out.println(sentence.getTokens().get(3).getTokenName() + sentence.getTokens().get(5).getTokenName());
			}
		}
		FileOperation.createFile(tmpFileName, copy);
			
		try {
			Scanner sc = new Scanner(new File(tmpFileName));
			int copylineNum = 1;
			while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.length() > 6) {
				if (line.charAt(6) != '*' && line.charAt(6) != '/') {
					code.put(copylineNum, line);
				}
			}
			++copylineNum;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
		firstScanCode(code);
	}
	
	private String trim(String str, String ind) {
		if (str.endsWith(".")) {
			str = str.substring(0, str.length() - 1);
		}
		return str.replace(ind, "");
	}
}
