package com.cricetulu.core.expression;

import com.cricetulu.core.global.GlobalDef;
import com.cricetulu.core.paser.PreProccess;

public class COPY extends Expression{
	
	// copy file replace a to b 
	public String execute(int line) {
		
		String copy = "";
		exp = exp.replace("\n", " ");
		exp = exp.replace(".", "");
		String [] token = exp.split(" ");
		for (int i = 0; i < token.length; ++i) {
			
			if (token.length < 2) {
				// 抛出异常
				System.out.println("COPY EXP ERROR! line:" + line);
				break;
			}
			String fileName = token[1].trim();
			copy = PreProccess.preProcess(GlobalDef.COPYBOOK_PATH + fileName, GlobalDef.COPYBOOK_PATH + fileName + "tmp").toString();
			if (token.length == 4) {
				// 效率低
				copy = copy.toString().replaceAll(token[3].trim(), token[4].trim());
			}
		}
	
		return copy;
	}
}
