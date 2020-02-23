package com.xhuagg.edb.test;

import java.util.ArrayList;
import java.util.List;

import com.xhuagg.edb.aspe.ASPECiphertext;
import com.xhuagg.edb.data.DataReader;

public class TestMain {
	
	public static List<String> names = new ArrayList<String>();
	static {
		String prefix = "d:\\edb\\testdata\\";
		String[] filenames = {"02_foo", "02", "04", "06", "08", "10", "12", "14", "16", "18"};
		String suffix = ".txt";
		for(String nm: filenames) {
			String temp = prefix + nm + suffix;
			names.add(temp);
		}
	}
	
	
	public static void main(String[] args) {
		
		// generate test data sets
		TestGenData.testGen(null);
		
		// all data sets to be evaluated
		List<List<String>> datasets = DataReader.read(names);
		
		TestEncAES.test(datasets);
		List<List<ASPECiphertext>> cdatasets = TestASPE.testED(datasets);
		TestASPE.testEQ(datasets);
		TestQuery.testQueryNonindexed(cdatasets);
		TestQuery.testQueryIndexed(cdatasets);
	}

}
