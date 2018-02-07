package com.cricetulu.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author student
 * 
 */
public class FileOperation {
	/**
	 * ��ȡ�ļ������ж��롣
	 * 
	 * @param fileName
	 * @return
	 */
	public static StringBuffer readFile(String fileName) {
		// �û����滺������
		StringBuffer sb = new StringBuffer();

		try {
			// ����ɨ������
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());
				sb.append("\n");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("File does not exist");
		}
		return sb;
	}

	/**
	 * �����ļ�����д�����ݡ�
	 * 
	 * @param fileName
	 * @param text
	 */
	public static void createFile(String fileName, String text) {
		// ����ɨ��������ı�
		Scanner sc = new Scanner(text);
		try {
			// ����д���ı���true��ʾ�Զ�ˢ��
			File f = new File(fileName);
			// ������ʱ���������ļ�
			if (!f.exists())
				f.createNewFile();
			PrintWriter pw = new PrintWriter(new FileWriter(f), true);
			while (sc.hasNextLine()) {
				// д������
				pw.println(sc.nextLine());
			}
			// ˢ���ı�����
			pw.flush();
			pw.close();
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ı�׷������
	 * 
	 * @param fileName
	 * @param text
	 */
	public static void appendFile(String fileName, String text) {
		StringBuffer sb = readFile(fileName);
		String str = new String(sb);
		str += text;
		createFile(fileName, str);
	}

	/**
	 * ���ı�׷������
	 * 
	 * @param fileName
	 * @param text
	 */
	public static void appendFile(String Path, Scanner s) {
		File f = new File(Path);
		Scanner sc;
		try {
			sc = new Scanner(f);
			StringBuilder sb = new StringBuilder();
			while (sc.hasNextLine())// �ȶ������ļ�����,���ݴ�sb��;
			{
				sb.append(sc.nextLine());
				sb.append("\r\n");// ���з���Ϊ���,ɨ������������,���Ҫ�Լ����.
			}
			sc.close();
			while (s.hasNextLine())// ����׷������,���ݴ�sb��;
			{
				sb.append(s.nextLine());
				sb.append("\r\n");// ���з���Ϊ���,ɨ������������,���Ҫ�Լ����.
			}
			s.close();

			PrintWriter pw = new PrintWriter(new FileWriter(f), true);
			pw.println(sb.toString());// д������.
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void replaceString(String path, String source, String target) {
		try {
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(path))));

			StringBuffer strBuf = new StringBuffer();
			for (String tmp = null; (tmp = bufReader.readLine()) != null; tmp = null) {
				// ���������滻����
				tmp = tmp.replaceAll(source, target);
				strBuf.append(tmp);
				strBuf.append(System.getProperty("line.separator"));
			}
			bufReader.close();

			PrintWriter printWriter = new PrintWriter(path);
			printWriter.write(strBuf.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void replaceString(String path, String targetPath,
			String source, String target) {
		try {
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(path))));

			StringBuffer strBuf = new StringBuffer();
			for (String tmp = null; (tmp = bufReader.readLine()) != null; tmp = null) {
				// ���������滻����
				tmp = tmp.replaceAll(source, target);
				strBuf.append(tmp);
				strBuf.append(System.getProperty("line.separator"));
			}
			bufReader.close();

			PrintWriter printWriter = new PrintWriter(targetPath);
			printWriter.write(strBuf.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * main function
	 * 
	 * @param path
	 * @param targetPath
	 * @param sources
	 * @param targets
	 */
	public static void replaceStrings(String path, String targetPath,
			String[] sources, String[] targets) {
		//�쳣�ж�
		if (sources.length != targets.length)
			{
			System.out.println("������滻�����С��һ��");
			return;
			}
		int total = sources.length;
		try {
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(path))));

			StringBuffer strBuf = new StringBuffer();
			for (String tmp = null; (tmp = bufReader.readLine()) != null; tmp = null) {
				if (tmp.contains("$"))
					for (int j = 0; j < total; j++) {
						tmp = tmp.replaceAll(sources[j], targets[j]);
					}
				strBuf.append(tmp);
				strBuf.append(System.getProperty("line.separator"));
			}
			bufReader.close();
			PrintWriter printWriter = new PrintWriter(targetPath);
			printWriter.write(strBuf.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
