package com.xhuagg.edb.aspe;

import Jama.Matrix;

public class MatrixPrinter {
	
	public static void print(Matrix m) {
		double[][] val = m.getArray();
		System.out.println(m + ":");
		for(int i = 0; i < val.length; i++) {
			for(int j = 0; j < val[i].length; j++) {
				System.out.printf("%.2f\t", val[i][j]);
			}
			System.out.println();
		}
	}

}
