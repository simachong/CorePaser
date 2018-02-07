package com.cricetulu.core.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author linying5
 * @since 2013.08.26
 * 
 */
public class StringOperation {
	/**
	 * ��ȡ�ַ����е����֡�����java��������ʽ��Pattern���Matcher��
	 * 
	 * @param fileName
	 * @return
	 */
	public static ArrayList<Integer> readInteger(String text) {
		// �û����滺������
		ArrayList<Integer> intList = new ArrayList<Integer>();
		//һ����������
		Pattern p=Pattern.compile("[0-9]+"); 		
		Matcher m=p.matcher(text);
		while(m.find())
			{
			intList.add(Integer.parseInt(m.group()));
			}
		return intList;
	}
	/**
	 * �����ַ���������λ��
	 * @param str
	 * @param i
	 * @param j
	 * @return
	 */
	public static String swap(String str, int i,int j)
	{
		if(i==j)
			return str;
		else 
		{
			//System.out.println("before swap:"+str);
			StringBuffer sb=new StringBuffer(str);
			sb.setCharAt(i, str.charAt(j));
			sb.setCharAt(j, str.charAt(i));
			String result=new String(sb);
			//System.out.println("afer swap:"+result);
			return result;
			
		}
		
	}
	/**
	 * ȥ���ظ����ַ�
	 * @param text
	 * @return
	 */
	public static String getDistinctChars(String text){
		//sb����ȥ�غ�Ļ���
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<text.length();i++)
		{
			//��δ���ֹ��������
			if(sb.indexOf(text.charAt(i)+"")<0)
				sb.append(text.charAt(i));
		}
		return new String(sb);
		
	}
}
