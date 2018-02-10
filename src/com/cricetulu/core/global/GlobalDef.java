package com.cricetulu.core.global;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import com.cricetulu.core.expression.*;

import com.cricetulu.core.expression.Expression;

public class GlobalDef {
	
	public static final String KEY_WORDS_FILE = "input\\keyword";
	public static final String EXPRESSION_FILE = "input\\expression";
	public static final String COPYBOOK_PATH = "input\\copybook\\";
	public static final String EXPRESSION_PACK = "com.cricetulu.core.expression.";

	public static HashMap<String, String> keywords = new HashMap<String, String>();
	public static HashMap<String, Expression> expressions = new HashMap<String, Expression>();
	
	public static void init() {
		
		loadKeywords();
		loadExpression();
	}
	public static void loadKeywords() {
		
		try {
			Scanner sc = new Scanner(new File(KEY_WORDS_FILE));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				keywords.put(line.trim(), "");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
	}
	
	public static void loadExpression() {
		
		try {
			Scanner sc = new Scanner(new File(EXPRESSION_FILE));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Expression exp = null;
				try {
					
					exp = (Expression) Class.forName(EXPRESSION_PACK + line.trim().split(",")[0]).newInstance();
					exp.setEndInd(line.trim().split(",")[1]);
					expressions.put(line.trim().split(",")[0], exp);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
	}
}
