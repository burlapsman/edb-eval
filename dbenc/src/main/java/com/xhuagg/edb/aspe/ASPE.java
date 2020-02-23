package com.xhuagg.edb.aspe;

import Jama.Matrix;

public class ASPE {
	
	public static ASPECiphertext Ed(double[] p, String s, Matrix m1, Matrix m2) {
		
		if(p == null || s == null || p.length+1 != s.length())
			return null;
		
		double[] pEx = ASPECiphertextEdEx(p);
		double[] pEx1 = new double[pEx.length];
		double[] pEx2 = new double[pEx.length];
		
		
		
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '0') {
				pEx1[i] = pEx[i];
				pEx2[i] = pEx[i];
			} else if(s.charAt(i) == '1') {
//				pEx1[i] = KeyGenerator.random();
				pEx1[i] = Math.random()*pEx[i];
				pEx2[i] = pEx[i] - pEx1[i];
			} else {
				throw new RuntimeException();
			}
		}
		
		
		
		double[][] pEx1Row = new double[1][pEx1.length];
		double[][] pEx2Row = new double[1][pEx2.length];
		for(int i = 0; i < pEx1Row[0].length; i++)
			pEx1Row[0][i] = pEx1[i];
		for(int i = 0; i < pEx2Row[0].length; i++)
			pEx2Row[0][i] = pEx2[i];
		
		Matrix pa = new Matrix(pEx1Row);
		Matrix pb = new Matrix(pEx2Row);
		
		ASPECiphertext result = new ASPECiphertext();
		result.setPa(pa.times(m1));
		result.setPb(pb.times(m2));
		
		return result;
	}
	
	private static double[] ASPECiphertextEdEx(double[] p) {
		double[] pEx = new double[p.length + 1];
		double pp = 0;
		for(int i = 0; i < p.length; i++) {
			pEx[i] = p[i];
			pp += p[i]*p[i];
		}
		pEx[pEx.length-1] = -0.5*pp;
		
		
		return pEx;
	}
	
	
	public static ASPECiphertext Eq(double[] q, String s, Matrix m1, Matrix m2) {
		if(q == null || s == null || q.length+1 != s.length())
			return null;
		
		double[] qEx = ASPECiphertextEqEx(q);
		double[] qEx1 = new double[qEx.length];
		double[] qEx2 = new double[qEx.length];
		
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '1') {
				qEx1[i] = qEx[i];
				qEx2[i] = qEx[i];
			} else if(s.charAt(i) == '0') {
//				qEx1[i] = KeyGenerator.random();
				qEx1[i] = Math.random()*qEx[i];
				qEx2[i] = qEx[i] - qEx1[i];
			} else {
				throw new RuntimeException();
			}
		}
		
		double[][] qEx1Col = new double[qEx1.length][1];
		double[][] qEx2Col = new double[qEx2.length][1];
		for(int i = 0; i < qEx1Col.length; i++)
			qEx1Col[i][0] = qEx1[i];
		for(int i = 0; i < qEx2Col.length; i++)
			qEx2Col[i][0] = qEx2[i];
		
		Matrix qa = new Matrix(qEx1Col);
		Matrix qb = new Matrix(qEx2Col);
		
		Matrix invM1 = m1.inverse();
		Matrix invM2 = m2.inverse();
		
		ASPECiphertext result = new ASPECiphertext();
		result.setPa(invM1.times(qa));
		result.setPb(invM2.times(qb));
		
		return result;
	}
	
	
	private static double[] ASPECiphertextEqEx(double[] q) {
		double[] qEx = new double[q.length + 1];
		double r = ASPEKeyGenerator.randomPositive();
		
		for(int i = 0; i < qEx.length - 1; i++) {
			qEx[i] = q[i]*r;
		}
		qEx[qEx.length-1] = r;
		
		return qEx;
	}
	
	
	
	public static double compare(ASPECiphertext q1, ASPECiphertext q2, ASPECiphertext p) {
		Matrix t1 = q1.getPa().minus(q2.getPa()).times(p.getPa());
		Matrix t2 = q1.getPb().minus(q2.getPb()).times(p.getPb());
		Matrix result = t1.plus(t2);
		
		double[][] array = result.getArray();
		
		return array[0][0];
	}
	

}
