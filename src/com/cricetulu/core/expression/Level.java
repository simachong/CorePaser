package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.DataStorage;

public class Level extends Expression{

	public void execute (DataModule dataModule, Sentence sentence) {
		
		DataStorage ds = (DataStorage)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		
		if (tokens.size() < 1 || !dsDefCheck(tokens.get(0).getTokenName())) {
			return; // error log
		}
		ds.setItem88(false);
		ds.setHierarchy(tokens.get(0).getTokenName());
		ds.setName(tokens.get(1).getTokenName());
		// 01 02 03 04 05... hierarchy name 
		// PIC|PICTURE dataType
		// USAGE {BINARY, COMPUTATIONAL, COMP, COMP-3, COMPUTATIONAL-3,DISPLAY, PACKED-DECIMAL}
		// OCCURS num TIMES 
		// INDEXED BY index
		// REDEFINES
		// VALUE|VALUES
		// DENPENDING ON denpences
		// 66 77 88
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			switch (tokenName) {

 			case "PIC" : ds.setDataType(tokens.get(i + 1).getTokenName()); break;
    			case "PICTURE" : ds.setDataType(tokens.get(i + 1).getTokenName()); break;
 			case "OCCURS" : ds.setOccurTime(Integer.parseInt(tokens.get(i + 1).getTokenName())); break;
 			case "TO" : ds.setMinTime(Integer.parseInt(tokens.get(i - 1).getTokenName()));
 			            ds.setMaxTime(Integer.parseInt(tokens.get(i + 1).getTokenName())); break;
 			case "INDEXED" : 
 				if (tokens.get(i + 1).getTokenName().equals("BY")) { 
 					ds.setIndex(tokens.get(i + 2).getTokenName()); 
 				}
 				else {
 					ds.setIndex(tokens.get(i + 1).getTokenName()); 
 				}
 				break;
 			case "VALUE" : ds.setValue(tokens.get(i + 1).getTokenName()); break;
 			//case "VALUES": break; 88 ITEM
 			case "DEPENDING" : ds.setDenpences(tokens.get(i + 2).getTokenName()); break;
      		// depending check must between min and max
 			default : break;
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
