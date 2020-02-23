package com.xhuagg.edb.aspe;

import java.math.BigInteger;
import java.util.Random;

import Jama.Matrix;

public class ASPEKeyGenerator {
	
	private static final double UP_LIMIT = 1E6;
	
	public static String genS(int n) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++) {
			if(random.nextBoolean())
				sb.append('1');
			else
				sb.append('0');
		}
		return sb.toString();
	}
	
	
	public static Matrix genMatrix(int n) {
		double[][] a = new double[n][n];
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				a[i][j] = random();
			}
		}
		
		return new Matrix(a);
	}
	
	public static double random() {
		return (Math.random()-0.5)*UP_LIMIT;
	}
	
	public static double randomPositive() {
		return Math.random()*UP_LIMIT;
	}

}
