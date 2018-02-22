package com.cricetulu.core.paser;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.circetulu.core.block.Division;
import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.expression.FD;
import com.cricetulu.core.expression.SELECT;
import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.module.DataStorage;
import com.cricetulu.core.module.FileC;

public class Paser {

	private LinkedHashMap<Integer, String> code = new LinkedHashMap<Integer, String>();
	private ArrayList<Sentence> sentences;
	private Lexer lexer;
	private LinkedHashMap<String, Division> divsions;
	private FileC currFile;
	private DataStorage currDs;
	private Division currDivision;
	private LinkedHashMap<String, FileC> files = new LinkedHashMap<String, FileC>();
	
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
	
	public void printFiles() {
		System.out.println(files.size());
	}
	
	public void printFD() {
		
		String tokenStr = "";
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
		System.out.println(name);
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
		String currSection = "";
		if (2 == tokens.size()) {
			String isSection = tokens.get(1).getTokenName();
			if (isSection.equals("SECTION")) {
				currSection = tokens.get(0).getTokenName();
				return;
			}
		}
		
		if (currSection.equals("FD")) {
			
			String name = sentence.getTokens().get(0).getTokenName();
			System.out.println(name);
			if (name.equals("FD")) {
				FD sel = new FD();
				String fileName = sentence.getTokens().get(1).getTokenName();
				FileC file = files.get(fileName);
				sel.execute(file, sentence);
				currFile = file;
				return;
			}
		}
		
		if (currFile != null) {
			
			LinkedHashMap<String,DataStorage> dsMap = currFile.getDs();
			
			String name = sentence.getTokens().get(0).getTokenName();
			if (dsDefCheck(name)) {
				DataStorage ds = new DataStorage();
				if (name.equals("01")) {
					
				}
			}	
		}
		else {
			// error log
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
