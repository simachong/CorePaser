package com.cricetulu.core.expression;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.circetulu.core.block.Sentence;
import com.circetulu.core.block.Token;
import com.cricetulu.core.module.DataModule;
import com.cricetulu.core.module.DataStorage;
import com.cricetulu.core.module.Item88;

public class Item88Exp extends Expression {

public void execute (DataModule dataModule, Sentence sentence) {
		
		DataStorage ds = (DataStorage)dataModule;
		ArrayList<Token> tokens = sentence.getTokens();
		
		String hir = tokens.get(0).getTokenName();
		String name = tokens.get(1).getTokenName();
		
		if (tokens.size() < 1 || !dsDefCheck(hir)) {
			return; // error log
		}
		ds.setItem88(true);
		LinkedHashMap<String, Item88> item88Map = ds.getItem88s();
		
		Item88 item88 = new Item88();
		ArrayList<String> values = item88.getValues();
		item88Map.put(name, item88);

		for (int i = 3; i < tokens.size(); ++i) {
			
			String tokenName = tokens.get(i).getTokenName();
			
			if (tokenName.equals("TRUE")) {
				
				expand(values, tokens.get(i - 1).getTokenName(), tokens.get(i + 1).getTokenName());
			}
			else {
				if (tokenName.contains(",")) {
					
					String [] vs = tokenName.split(",");
					for (int j = 0; j < vs.length; ++j) {
						values.add(vs[j]);
					}
				}
				else {
					
					values.add(tokenName);
				}
			}
		}
	}
	
	private void expand (ArrayList<String> values, String from, String to) {
		// TO DO
	}
	
	private boolean dsDefCheck(String str) {
		
		if (str.length() != 2) {
			return false;
		}
		else {
			if (!str.equals("88")) {
				
				return false;
			}
			else {
				
				return true;
			}
		}
	}
}
