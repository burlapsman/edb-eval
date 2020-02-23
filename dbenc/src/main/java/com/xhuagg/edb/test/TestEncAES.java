package com.xhuagg.edb.test;

import java.util.List;

import com.xhuagg.edb.aes.AESCBCR;

public class TestEncAES {
	
	public static void test(List<List<String>> datasets) {
		AESCBCR aescbcr = new AESCBCR();
		long start = 0L;
		long end = 0L;
		
		// init
		aescbcr.encrypt(AESCBCR.genRandom16Byte(), AESCBCR.TEST_KEY, AESCBCR.TEST_IV);
		
		System.out.println("====================EVAL.AES-CBC-R====================");
		for(List<String> dataset: datasets) {
			// start
			start = System.currentTimeMillis();
			
			// encrypt every record.
			long sz = 0;
			for(String rec: dataset) {
				String[] r = rec.split(",");
				for(int i = 0; i < r.length; i++) {
					byte[] mBytes = r[i].getBytes();
					byte[] cBytes = aescbcr.encrypt(mBytes, AESCBCR.TEST_KEY, AESCBCR.TEST_IV);
					sz += cBytes.length;
				}
			}
			
			// end
			end = System.currentTimeMillis();
			
			// time cost
			System.out.println("total: " + dataset.size() + "rec"
			+ "\t time:" + (end-start) + "ms"
			+ "\t size:" + (1.0*sz/1024/1024) + "MB");
		}
	}
	
	
	
	
}
