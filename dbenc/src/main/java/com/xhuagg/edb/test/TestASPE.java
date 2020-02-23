package com.xhuagg.edb.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.xhuagg.edb.aspe.ASPE;
import com.xhuagg.edb.aspe.ASPECiphertext;
import com.xhuagg.edb.aspe.ASPEKeyGenerator;

import Jama.Matrix;

public class TestASPE {
	public static final int N = 6;

	public static Matrix m1 = ASPEKeyGenerator.genMatrix(N+1);
	public static Matrix m2 = ASPEKeyGenerator.genMatrix(N+1);
	public static String s = ASPEKeyGenerator.genS(N+1);

	private static Random random = new Random();



	public static List<List<ASPECiphertext>> testED(List<List<String>> datasets) {
		long start = 0L;
		long end = 0L;

		// init
		List<List<ASPECiphertext>> cdatasets = new LinkedList<List<ASPECiphertext>>();

		System.out.println("====================EVAL.ASPE.ED====================");
		for(List<String> dataset: datasets) {
			List<ASPECiphertext> cdataset = new LinkedList<ASPECiphertext>();
			// start
			start = System.currentTimeMillis();

			// encrypt every record.
			long sz = 0;
			for(String rec: dataset) {
				// convert rec to a vecotr v
				String[] r = rec.split(",");
				double[] p = new double[r.length];
				p[0] = Double.parseDouble(r[0]);
				p[1] = convertStr(r[1]);
				p[2] = Double.parseDouble(r[2]);
				p[3] = convertDate(r[3]);
				p[4] = Double.parseDouble(r[4]);
				p[5] = random.nextDouble();

				// enc v
				ASPECiphertext pp = ASPE.Ed(p, s, m1, m2);
				sz += 2*(N+1)*8;
				
				cdataset.add(pp);
			}

			// end
			end = System.currentTimeMillis();
			
			cdatasets.add(cdataset);

			// time cost
			System.out.println("total: " + dataset.size() + "rec"
					+ "\t time:" + (end-start) + "ms"
					+ "\t size:" + (1.0*sz/1024/1024) + "MB");
		}
		
		return cdatasets;
	}
	
	public static void testEQ(List<List<String>> datasets) {
		long start = 0L;
		long end = 0L;

		System.out.println("====================EVAL.ASPE.EQ====================");
		for(List<String> dataset: datasets) {
			// start
			start = System.currentTimeMillis();

			// encrypt every record.
			long sz = 0;
			for(String rec: dataset) {
				// convert rec to a vecotr v
				String[] r = rec.split(",");
				double[] qa = new double[r.length];
				double[] qb = new double[r.length];
				
				double h = random.nextDouble();
				
				qa[4] = Double.parseDouble(r[4]) + h;//salary
				qb[4] = Double.parseDouble(r[4]) - h;//salary
				
				
				for(int i = 0; i < r.length; i++) {
					if(i != 4) {
						qa[i] = random.nextDouble();
						qb[i] = qa[i];
					}
				}
				
				

				// enc v
				ASPECiphertext cqa = ASPE.Eq(qa, s, m1, m2);
				ASPECiphertext cqb = ASPE.Eq(qb, s, m1, m2);
				sz += 4*(N+1)*8*2;
				
			}

			// end
			end = System.currentTimeMillis();
			
			// time cost
			System.out.println("total: " + dataset.size() + "rec"
					+ "\t time:" + (end-start) + "ms"
					+ "\t size:" + (1.0*sz/1024/1024) + "MB");
		}
	}

	private static double[] charTab = null;
	static {
		charTab = new double[256];
		int count = 1;
		for(char i = '0'; i <= '9'; i++)
			charTab[i] = count++;
		for(char i = 'a'; i <= 'z'; i++) {
			charTab[i] = count++;
			charTab[i-'a'+'A'] = charTab[i];
		}
	}

	private static double convertStr(String str) {
		byte[] bytes = str.getBytes();
		if(bytes.length > 20) {
			// error
			return 0.0;
		} else {
			double res = 1.0;
			double interval = 40;
			for(int i = 0; i < 20; i++) {
				res = res*interval;
				if(i < bytes.length)
					res += charTab[bytes[i]];
			}
			return res;
		}
	}

	private static double convertDate(String strDate) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();
		res = String.valueOf(ts);
		return Double.parseDouble(res);
	}

}
