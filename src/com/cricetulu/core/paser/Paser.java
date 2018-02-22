package com.cricetulu.core.paser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.circetulu.core.block.Division;
import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.expression.FD;
import com.cricetulu.core.expression.Item88Exp;
import com.cricetulu.core.expression.Level;
import com.cricetulu.core.expression.SELECT;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.module.DataStorage;
import com.cricetulu.core.module.FileC;
import com.cricetulu.core.module.Item88;
import com.cricetulu.core.utils.FileOperation;

public class Paser {

	private LinkedHashMap<Integer, String> code = new LinkedHashMap<Integer, String>();
	private ArrayList<Sentence> sentences;
	private Lexer lexer;
	//private LinkedHashMap<String, Division> divsions;
	private FileC currFile;
	private DataStorage currDs;
	private Division currDivision;
	private String currSection = "";
	private LinkedHashMap<String, FileC> files = new LinkedHashMap<String, FileC>();
	private LinkedHashMap<String, DataStorage> dictDsMap = new LinkedHashMap<String, DataStorage>();
	public void init() {
		
		PreProccess.preProcess(".." + File.separator + "SRCLIB" + File.separator + "CPD110"
				, ".." + File.separator + "CPD110_PREPROCESS");
		GlobalDef.init();	
		lexer = new Lexer();
		lexer.scan(".." + File.separator + "CPD110_PREPROCESS", code);
		lexer.printSentences(".." + File.separator + "CPD110_SENTENCES");
		lexer.printTokens(".." + File.separator + "CPD110_TOKENS");
		sentences = lexer.getSentences();
		currDivision = new Division();
	}
	
	public void lex() {
		
		for (Sentence sentence : sentences) {
			
			parse(sentence);
		}
	}
	
	public void printDs() {
		String text = "";
		Iterator<Entry<String, DataStorage>> iter = dictDsMap.entrySet().iterator();
		while(iter.hasNext()) {
			
			Entry<String, DataStorage> entry = iter.next();
			text += entry.getValue().getHierarchy() 
					+ " " + entry.getValue().getName() 
					+ " " + entry.getValue().getDataType() + "\n";
			System.out.println(entry.getValue().getHierarchy() 
					+ " " + entry.getValue().getName() 
					+ " " + entry.getValue().getDataType());
			LinkedHashMap<String, Item88> item88s = entry.getValue().getItem88s();
			if (item88s != null) {
				text += print88(item88s);
			}
		}
		FileOperation.createFile(".." + File.separator + "CPD110_DS", text);
	}
	
	private String print88(LinkedHashMap<String, Item88> item88s) {
		
		Iterator<Entry<String, Item88>> iter = item88s.entrySet().iterator();
		String text = "";
		while(iter.hasNext()) {
			
			Entry<String, Item88> entry = iter.next();
			String val = "";
			val += "88" + entry.getKey();
			for (int i = 0; i < entry.getValue().getValues().size(); ++i) {
				val += " " + entry.getValue().getValues().get(i);
			}
			
			System.out.println(val);
			text += val + "\n";
		}
		return text;
	}
	
	public void printFiles() {
		System.out.println(files.size());
	}
	
	public void printFD() {
		
		boolean isFD = false;
		for (Sentence sent : sentences) {
			isFD = false;
			for (Token token : sent.getTokens()) {
				if (token.getTokenName().equals("FD")) {
					isFD = true;
					break;
				}
			}
			if (isFD) {
				System.out.println(sent.toString());
			}
		}
	}
	private void parse(Sentence sentence) {
		
		ArrayList<Token> tokens = sentence.getTokens();
		
		String divisionName = currDivision.getDivisionName();
		if (tokens.size() < 1) {
			// TOOD error log
			return;
		}
		else {
			
			divisionName = tokens.get(0).getTokenName();
			if (GlobalDef.isDivision(divisionName)) {
				currDivision.setDivisionName(divisionName);
			}
		}		
		//System.out.println(currDivision);
		switch (currDivision.getDivisionName()) {
		case "IDENTIFICATION": indDivisionParse(); break;
		case "ENVIRONMENT": envDivsionParse(sentence); break;
		case "DATA": dataDivisionParse(sentence); break;
		case "PROCEDURE": procDivisionParse(); break;
		default: // error log
		}
	}
	
	private void envDivsionParse(Sentence sentence) {
		
		// section handle
		// routine handle
		String name = sentence.getTokens().get(0).getTokenName();
		//System.out.println(name);
		if (name.equals("SELECT")) {
			SELECT sel = new SELECT();
			FileC file = new FileC();
			sel.execute(file, sentence);
			files.put(file.getFilenName(), file);
		}
	}
	
	private void indDivisionParse() {
		
	}
	
	private void dataDivisionParse(Sentence sentence) {
		
		ArrayList<Token> tokens = sentence.getTokens();
		
		if (2 == tokens.size()) {
			String isSection = tokens.get(1).getTokenName();
			if (isSection.equals("SECTION")) {
				currSection = tokens.get(0).getTokenName();
				return;
			}
		}
		
		String hir = sentence.getTokens().get(0).getTokenName();
		String name = sentence.getTokens().get(1).getTokenName();
		//System.out.println(name);
		if (currSection.equals("WORKING-STORAGE")) {
			
			if (dsDefCheck(hir)) {
				DataStorage ds = new DataStorage();
				Level lv = new Level();
				lv.execute(ds, sentence);
				if (!name.toUpperCase().equals("FILLER")) {
					dictDsMap.put(name, ds);
				}
				currDs = ds;
			}
			else if (hir.equals("88")){
				Item88Exp item88Exp = new Item88Exp();
				if (currDs != null) {
			
					item88Exp.execute(currDs, sentence);
				}
			}
		}
		
		if (currSection.equals("FILE")) {
			
			
			
			if (name.equals("FD") || name.equals("SD")) {
				FD sel = new FD();
				String fileName = sentence.getTokens().get(1).getTokenName();
				FileC file = files.get(fileName);
				sel.execute(file, sentence);
				currFile = file;
				return;
			}
		
			if (currFile != null) {
				
				LinkedHashMap<String,DataStorage> dsMap = currFile.getDs();

				if (dsDefCheck(hir)) {
					DataStorage ds = new DataStorage();
					Level lv = new Level();
					lv.execute(ds, sentence);
					if (!name.toUpperCase().equals("FILLER")) {
						dsMap.put(name, ds);
						dictDsMap.put(name, ds);
					}
					currDs = ds;
				}
				else if (hir.equals("88")){
					Item88Exp item88Exp = new Item88Exp();
					if (currDs != null) {
				
						item88Exp.execute(currDs, sentence);
					}
				}
				else { // do not handle 66 77
					
				}
			}
			else {
				// error log
			}
		}
	}
	
	private void procDivisionParse() {
		
	}
	
	private boolean dsDefCheck(String str) {
		
		if (str.length() != 2) {
			return false;
		}
		else {
			if (str.charAt(0) != '0' && (Integer.parseInt(str) > 49 || Integer.parseInt(str) < 11)) {
				
				return false;
			}
			else {
				
				return true;
			}
		}
	}
}
