package com.cricetulu.core.global;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import com.cricetulu.core.expression.*;

import com.cricetulu.core.expression.Expression;

public class GlobalDef {
	
	public static final String KEY_WORDS_FILE = "input" + File.separator + "keyword";
	public static final String EXPRESSION_FILE = "input" + File.separator + "expression";
	public static final String COPYBOOK_PATH = ".." + File.separator + "COPYLIB" + File.separator;
	public static final String EXPRESSION_PACK = "com.cricetulu.core.expression.";
	public static final String [] divisions = {"IDENTIFICATION", "ENVIRONMENT", "DATA", "PROCEDURE"};

	
	public static HashMap<String, Integer> keywords = new HashMap<String, Integer>();
	public static HashMap<String, Expression> expressions = new HashMap<String, Expression>();
	public static HashMap<String, String> nameSpaceMapping = new HashMap<String, String>();
	
	public static boolean isDivision(String division) {
	
		for (int i = 0; i < divisions.length; ++i) {
			
			if (divisions[i].equals(division)) {
				return true;
			}
		}
		return false;
	}
	
	public static Integer isKeyword(String token) {
		
		if (keywords.containsKey(token)) {
			return keywords.get(token);
		}
		return -1;
	}
	
	public static boolean isExp(String token) {
		
		if (expressions.containsKey(token)) {
			return true;
		}
		return false;
	}
	
	public static void init() {
		
		loadKeywords();
		loadExpression();
	}
	public static void loadKeywords() {
		
		try {
			Scanner sc = new Scanner(new File(KEY_WORDS_FILE));

			int lineNum = 1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				keywords.put(line.trim(), lineNum);
				++lineNum;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
	}
	
	@SuppressWarnings("deprecation")
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
