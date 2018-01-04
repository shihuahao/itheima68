package com.itheima.test;

public class Test {

	public static void main(String[] args) {
		String str = "1234567890";
		
		int index1 = str.indexOf("5");
		int index2 = str.indexOf("6");
		
		String s = str.substring(index1, index2+1);
		System.out.println(s);
	}

}
