package com.xhuagg.edb.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.xhuagg.edb.data.DataGenerator;

public class TestGenData {
	
	public static int AMOUNT_1M = 6595;
	
	
	// Generate data for 2MB 4MB 6MB 8MB 10MB 12MB
	public static void testGen(String[] args) {
		
		String prefix = "d:\\edb\\testdata\\";
		String[] filenames = {"02_foo", "02", "04", "06", "08", "10", "12", "14", "16", "18"};
		String suffix = ".txt";
		int[] amount = {2, 2, 4, 6, 8, 10, 12, 14, 16, 18};
		
		
		for(int i = 0; i < amount.length; i++) {
			String name = prefix + filenames[i] + suffix;
			System.out.print("generate " + name + "...");
			save(name, amount[i] * AMOUNT_1M);
			System.out.println("OK!");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void save(String filename, int amount) {
		List<String> list = DataGenerator.generate(amount);
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter pw = new PrintWriter(fos);
			for(String rec: list) {
				pw.println(rec);
			}
			pw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
