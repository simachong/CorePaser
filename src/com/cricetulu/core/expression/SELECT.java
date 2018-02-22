package com.cricetulu.core.expression;

import java.util.ArrayList;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.FileC;

public class SELECT extends Expression{

	public void execute(DataModule dataModule, Sentence sentence) {
		
		FileC file = (FileC)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		if (tokens.size() < 1 || tokens.get(0).equals("SELECT")) {
			return; // error log
		}
		// SELECT jclFilename 
		// ASSIGN TO fileName
		// RSERVE xxx
		// ORGNIZATION IS organization
		// ACCESS MOD IS accessMethod
		// FILE STATUS IS status
		// RECORD KEY IS recKey
		for (int i = 0; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			switch (tokenName) {
			case "SELECT" : file.setFilenName(tokens.get(i + 1).getTokenName());break;
			case "ASSIGN" : file.setJclFilename(tokens.get(i + 2).getTokenName());break;
			case "ORGNIZATION" : file.setOrganization(tokens.get(i + 2).getTokenName());break;
			case "ACCESS" : file.setAccessMethod(tokens.get(i + 3).getTokenName());break;
			case "FILE" : file.setStatus(tokens.get(i + 2).getTokenName());break;
			case "RECORD" : file.setRecKey(tokens.get(i + 2).getTokenName());break;
			default : break;
			}
		}
	}
}
