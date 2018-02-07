package com.cricetulu.core.paser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.cricetulu.core.utils.FileOperation;

public class PreProccess {
	
	private ArrayList<String> copybooks; 
	public static StringBuffer firstScanFile(String fileName) {
		
		StringBuffer sb = new StringBuffer();

		try {
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.charAt(6) != '*' && line.charAt(6) != '/') {
					sb.append(line);
					sb.append("\n");
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
		FileOperation.createFile("D:\\CPD110_1", sb.toString());
		return sb;
	}
	
	

}
