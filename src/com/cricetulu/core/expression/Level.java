package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.DataStorage;
import com.cricetulu.core.module.FileC;

public class Level extends Expression{

	public void execute (DataModule dataModule, Sentence sentence) {
		
		DataStorage ds = (DataStorage)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		if (tokens.size() < 1 || dsDefCheck(tokens.get(0).getTokenName())) {
			return; // error log
		}
		// 01 02 03 04 05... hierarchy name 
		// PIC|PICTURE dataType
		// USAGE {BINARY, COMPUTATIONAL, COMPUTATIONAL,COMP, COMP-3, COMPUTATIONAL-3,DISPLAY, PACKED-DECIMAL}
		// OCCURS num TIMES 
		// INDEXED BY index
		// REDEFINES
		// VALUE|VALUES
		// DENPENDING ON denpences
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			switch (tokenName) {
//			case "FD" : if (!file.getFilenName().equals(tokens.get(i + 1).getTokenName())) { }; break; // error log
//			case "BLOCK" : file.setRecNum(tokens.get(i + 2).getTokenName()); break;
//			case "RECORD" : file.setCharNum(tokens.get(i + 2).getTokenName()); break;
//			case "LABEL" : file.setLabelRec(tokens.get(i + 3).getTokenName()); break;
//			case "RECORDING" : file.setRecMode(tokens.get(i + 3).getTokenName()); break;
//			default : break;
			}
		}
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
