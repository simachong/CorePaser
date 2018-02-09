package com.cricetulu.core.main;

import java.util.HashMap;

import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.paser.Lexer;
import com.cricetulu.core.paser.PreProccess;

public class Parse {
	
	
	public static void main(String args[]) {
		
		PreProccess.preProcess("D:\\CPD110", "D:\\CPD110_PREPROCESS");
		//HashMap<String, String> hm = new HashMap<String, String>();
		//hm.put(")", "(");
		GlobalDef.init();
		Lexer lex = new Lexer();
		lex.firstScan("D:\\CPD110_PREPROCESS");
		System.out.println("Over!");
	}

}
