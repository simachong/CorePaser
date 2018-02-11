package com.cricetulu.core.main;

import java.io.File;
import java.util.HashMap;

import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.paser.Lexer;
import com.cricetulu.core.paser.PreProccess;

public class Parse {
	
	
	public static void main(String args[]) {
		
		//PreProccess.preProcess("Users\\simachong\\git\\SRCLIB\\CPD110", "Users\\simachong\\git\\CPD110_PREPROCESS");
		PreProccess.preProcess(".." + File.separator + "SRCLIB" + File.separator + "CPD110"
				, ".." + File.separator + "CPD110_PREPROCESS");
		//HashMap<String, String> hm = new HashMap<String, String>();
		//hm.put(")", "(");
		//System.out.println(System.getProperty("user.dir"));
		GlobalDef.init();
		Lexer lex = new Lexer();
		lex.firstScan(".." + File.separator + "CPD110_PREPROCESS");
		System.out.println("Over!");
	}

}
