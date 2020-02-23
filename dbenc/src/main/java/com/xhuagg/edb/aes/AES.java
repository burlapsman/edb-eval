package com.xhuagg.edb.aes;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES {
	
	public static String KEY_ALGORITHM = "AES";
	String algorithmStr = "AES/CBC/PKCS7Padding";
	public static boolean initialized = false;

	public byte[] encrypt(byte[] originalContent, byte[] encryptKey, byte[] ivByte) {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec skeySpec = new SecretKeySpec(encryptKey, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(ivByte));
			byte[] encrypted = cipher.doFinal(originalContent);
			return encrypted;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(byte[] content, byte[] aesKey, byte[] ivByte) {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(aesKey, "AES");
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// ≥ı ºªØ
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void initialize() {
		if (initialized)
			return;
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}
	
	/**
	 * random 128 bit
	 * @return
	 */
	public static Random random = new Random();
	public static byte[] genRandom16Byte() {
		byte[] r = new byte[16];
		random.nextBytes(r);
		return r;
	}
}