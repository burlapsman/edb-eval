package com.xhuagg.edb.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataReader {
	
	public static List<List<String>> read(List<String> names) {
		List<List<String>> list = new ArrayList<List<String>>();
		for(String name: names) {
			List<String> dataset = read(name);
			list.add(dataset);
		}
		
		return list;
	}
	
	private static List<String> read(String name) {
		
		try {
			FileReader fr = new FileReader(name);
			BufferedReader br = new BufferedReader(fr);
			String rec = null;
			List<String> list = new LinkedList<String>();
			while((rec = br.readLine()) != null) {
				list.add(rec);
			}
			br.close();
			fr.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
