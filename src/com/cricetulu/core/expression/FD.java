package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.FileC;

public class FD extends Expression {

	public void execute (DataModule dataModule, Sentence sentence) {
		
		FileC file = (FileC)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		if (tokens.size() < 1 || tokens.get(0).equals("FD")) {
			return; // error log
		}
		// FD filename
		// BLOCK CONTAINS recNum RECORDS
		// RECORD CONTAINS charNum CHARACTERS
		// LABEL RECORDS ARE labelRec
		// RECORDING MODE IS recMode
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			switch (tokenName) {
			case "FD" : if (!file.getFilenName().equals(tokens.get(i + 1).getTokenName())) { }; break; // error log
			case "BLOCK" : file.setRecNum(tokens.get(i + 2).getTokenName()); break;
			case "RECORD" : file.setCharNum(tokens.get(i + 2).getTokenName()); break;
			case "LABEL" : file.setLabelRec(tokens.get(i + 3).getTokenName()); break;
			case "RECORDING" : file.setRecMode(tokens.get(i + 3).getTokenName()); break;
			default : break;
			}
		}
	}
	
}
