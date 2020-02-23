package com.xhuagg.edb.test;

import java.util.List;
import java.util.Random;

import com.xhuagg.edb.aspe.ASPE;
import com.xhuagg.edb.aspe.ASPECiphertext;

public class TestQuery {
	
	public static Random random = new Random();
	
	public static void testQueryNonindexed(List<List<ASPECiphertext>> cdatasets) {
		long start = 0L;
		long end = 0L;
		
		// init
		double[] qa = new double[cdatasets.get(0).get(0).getPa().getColumnDimension()-1];
		double[] qb = new double[qa.length];
		double v = random.nextDouble();
		double h = random.nextDouble();
		qa[4] = v + h;//salary
		qb[4] = v - h;
		for(int i = 0; i < qa.length; i++) {
			if(i != 4) {
				qa[i] = random.nextDouble();
				qb[i] = qa[i];
			}
		}
		// enc v
		ASPECiphertext cqa = ASPE.Eq(qa, TestASPE.s, TestASPE.m1, TestASPE.m2);
		ASPECiphertext cqb = ASPE.Eq(qb, TestASPE.s, TestASPE.m1, TestASPE.m2);

		System.out.println("====================EVAL.QUERY.NONINDEXED====================");
		for(List<ASPECiphertext> dataset: cdatasets) {
			// start
			start = System.currentTimeMillis();

			// encrypt every record.
			long sz = 0;
			for(ASPECiphertext cp: dataset) {
				ASPE.compare(cqa, cqb, cp);
			}

			// end
			end = System.currentTimeMillis();
			
			// time cost
			System.out.println("total: " + dataset.size() + "rec"
					+ "\t time:" + (end-start) + "ms");
		}
	}
	
	
	
	public static void testQueryIndexed(List<List<ASPECiphertext>> cdatasets) {
		long start = 0L;
		long end = 0L;
		
		// init
		double[] qa = new double[cdatasets.get(0).get(0).getPa().getColumnDimension()-1];
		double[] qb = new double[qa.length];
		double v = random.nextDouble();
		double h = random.nextDouble();
		qa[4] = v + h;//salary
		qb[4] = v - h;
		for(int i = 0; i < qa.length; i++) {
			if(i != 4) {
				qa[i] = random.nextDouble();
				qb[i] = qa[i];
			}
		}
		// enc v
		ASPECiphertext cqa = ASPE.Eq(qa, TestASPE.s, TestASPE.m1, TestASPE.m2);
		ASPECiphertext cqb = ASPE.Eq(qb, TestASPE.s, TestASPE.m1, TestASPE.m2);

		System.out.println("====================EVAL.QUERY.INDEXED====================");
		for(List<ASPECiphertext> dataset: cdatasets) {
			// start
			start = System.currentTimeMillis();

			for(int i = 0; i < 10000; i++) {
				// encrypt every record.
				// depth of balanced search binary tree, 
				double x = Math.log(dataset.size())/Math.log(2) + 1;
				int height = (int) (x + 0.5);
				int count = 0;
				for(ASPECiphertext cp: dataset) {
					ASPE.compare(cqa, cqb, cp);
					count++;
					if(count > height)
						break;
				}
			}
			
			// end
			end = System.currentTimeMillis();

			
			// time cost
			System.out.println("total: " + dataset.size() + "rec"
					+ "\t time:" + ((end-start)/10000.0) + "ms");
		}
	}

}
