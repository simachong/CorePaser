package com.cricetulu.core.main;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.paser.Lexer;
import com.cricetulu.core.paser.Parser;
import com.cricetulu.core.paser.PreProccess;

public class Parse {
	
	
	public static void main(String args[]) {
		
//		//PreProccess.preProcess("Users\\simachong\\git\\SRCLIB\\CPD110", "Users\\simachong\\git\\CPD110_PREPROCESS");
//		PreProccess.preProcess(".." + File.separator + "SRCLIB" + File.separator + "CPD110"
//				, ".." + File.separator + "CPD110_PREPROCESS");
//		//HashMap<String, String> hm = new HashMap<String, String>();
//		//hm.put(")", "(");
//		//System.out.println(System.getProperty("user.dir"));
//		GlobalDef.init();
//		Lexer lex = new Lexer();
//		LinkedHashMap<Integer, String> code = new LinkedHashMap<Integer, String>();
//		lex.scan(".." + File.separator + "CPD110_PREPROCESS", code);
//		lex.printSentences(".." + File.separator + "CPD110_SENTENCES");
//		lex.printTokens(".." + File.separator + "CPD110_TOKENS");
		Parser ps = new Parser();
		ps.init();
		ps.lex();
		
//		ps.printFiles();
		ps.printFD();
//		ps.printDs();
		//ps.printRoutine();
		ps.analyseHandleStack();
		System.out.println("Over!");
	}

}
