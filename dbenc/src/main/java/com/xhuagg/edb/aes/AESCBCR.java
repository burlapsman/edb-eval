package com.xhuagg.edb.aes;

import java.util.Arrays;

public class AESCBCR extends AES {
	
	public static byte[] TEST_IV = {72, -78, -101, 64, -7, -39, 67, 104, 1, 39, 17, 51, 25, -41, 64, -30};
	public static byte[] TEST_KEY = {61, -78, 106, 105, 7, 92, 43, -33, -22, 90, -32, -52, -110, 9, 121, 5};
	
	public byte[] encrypt(byte[] originalContent, byte[] encryptKey, byte[] ivByte) {
		byte[] content = new byte[originalContent.length + 128/8];
		byte[] r = AES.genRandom16Byte();
		for(int i = 0; i < r.length; i++)
			content[i] = r[i];
		for(int i = 0; i < originalContent.length; i++) {
			content[i+r.length] = originalContent[i];
		}
		
		return super.encrypt(content, encryptKey, ivByte);
	}
	
	
	public byte[] decrypt(byte[] content, byte[] aesKey, byte[] ivByte) {
		byte[] m = super.decrypt(content, aesKey, ivByte);
		return Arrays.copyOfRange(m, 16, m.length);
	}

}
