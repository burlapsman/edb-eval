package com.xhuagg.edb.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
	
	public static Random random = new Random();

	//(id, name, age, employ_date, salary, resume)
	private static int id = 10000;
	private static String[] names = {"Gary","William","Charles","Michael","Karl","John","Thomas","Dean","Paul","Jack","Brooke","Louis","John","George","Henry","Benjamin","Carl","Scott","Tom","Eddy","Kris","Peter","Bruce","Robert","Peter","Bill","Joseph","John","Charlie","Elliot","George","Johnson","James","Charles","Bruce","David","Walt","John","Mark","Sam","Davis","Neil","Carl","Lewis","Billy","HowardAllen","Johnny","Robert","Martin","Jeff","Sam","Francis","Lewis","Stephen","Andy","Scott","Kevin","Michael","Taylor","Jackson","Jack","Jimmy","Allen","Martin","Vincent"};
	private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7',
								 '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	public static void reset() {
		id = 10000;
	}
	
	private static int genInt(int low, int high) {
		return random.nextInt(high-low+1) + low;
	}

	private static String genId() {
		return Integer.toString(id++);
	}
	private static String genName() {
		return names[genInt(0, names.length-1)] + genInt(1000, 9999);
	}
	private static String genAge() {
		return Integer.toString(genInt(20, 60));
	}
	private static String genDate() {
		String s = "" + genInt(2010, 2020) + "-" + genInt(1, 12) + "-" + genInt(1, 28);
		return s;
	}
	private static String genSalary() {
		return Integer.toString(genInt(2000, 40000));
	}
	private static String genResume() {
		StringBuilder builder = new StringBuilder();
		builder.append("0x");
		for(int i = 0; i < 120; i++) {
			builder.append(hex[genInt(0, 15)]);
		}
		return builder.toString();
	}
	

	public static String generateOne() {
		StringBuilder builder = new StringBuilder();
		builder.append(genId());
		builder.append(",");
		builder.append(genName());
		builder.append(",");
		builder.append(genAge());
		builder.append(",");
		builder.append(genDate());
		builder.append(",");
		builder.append(genSalary());
		builder.append(",");
		builder.append(genResume());
		
		return builder.toString();
	}
	
	public static List<String> generate(int amount) {
		List<String> list = new ArrayList<String>(amount);
		for(int i = 0; i < amount; i++)
			list.add(generateOne());
		return list;
	}

}
