package com.cricetulu.core.paser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.cricetulu.core.utils.FileOperation;

public class PreProccess {
	
	public static StringBuffer preProcess(String fileName) {
		
		StringBuffer sb = new StringBuffer();

		try {
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() > 6) {
					if (line.charAt(6) != '*' && line.charAt(6) != '/') {
						if (line.length() > 72) {
							line = line.substring(7, 72);
						}
						else {
							line = line.substring(7);
						}
						sb.append(line);
						sb.append("\n");
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
		FileOperation.createFile("D:\\CPD110_PREPROCESS", sb.toString());
		return sb;
	}
	
	

}
