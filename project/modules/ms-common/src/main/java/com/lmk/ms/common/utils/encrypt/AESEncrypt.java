package com.lmk.ms.common.utils.encrypt;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密与解密工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/04
 */
public class AESEncrypt {

	/** 密钥算法 */
	private static final String KEY_ALGORITHM = "AES";
	
	/** 密钥长度，可以为：128、192、256。当大于128时需要替换jre文件 */
	private static final int KEY_SIZE = 128;

	/** 密算法/工作模式/填充方式 */
	public static final String CIPHER_ALGORITHM_PKCS5 = "AES/ECB/PKCS5Padding";
	
	/** 密算法/工作模式/填充方式 */
	public static final String CIPHER_ALGORITHM_PKCS7 = "AES/ECB/PKCS5Padding";
	
	
	/** 字符集 */
	public static final String CHAR_SET = "UTF-8";
	
	/**
	 * 加密消息，指定填充方式
	 * @param message
	 * 			明文消息
	 * @param key
	 * 			密钥
	 * @param cipherAlgorithm
	 * 			填充方式
	 * @return
	 * 			加密消息字节数组
	 * @throws Exception
	 */
	public static byte[] encrypt(String message, String key, String cipherAlgorithm) throws Exception {
		byte[] data = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(KEY_SIZE, new SecureRandom(key.getBytes())); //SecureRandom 只要seed相同，随机数列就相同
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			data = cipher.doFinal(message.getBytes(CHAR_SET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 加密消息
	 * @param message
	 * 			明文消息
	 * @param key
	 * 			密钥
	 * @return
	 * 			加密消息字节数组
	 * @throws Exception
	 */
	public static byte[] encrypt(String message, String key) throws Exception {
		return encrypt(message, key, CIPHER_ALGORITHM_PKCS5);
	}
	
	/**
	 * 加密消息，使用默认的填充方式
	 * @param message
	 * 			明文消息
	 * @param key
	 * 			密钥
	 * @return
	 * 			密文（BASE64编码）
	 * @throws Exception 
	 */
	public static String encryptToBase64(String message, String key) throws Exception{
		byte[] data = encrypt(message, key, CIPHER_ALGORITHM_PKCS5);
		return Base64.encodeBase64String(data);
	}
	
	/**
	 * 加密消息，指定填充方式
	 * @param message
	 * 			明文消息
	 * @param key
	 * 			密钥
	 * @param cipherAlgorithm
	 * 			填充方式
	 * @return
	 * 			密文（BASE64编码）
	 * @throws Exception
	 */
	public static String encryptToBase64(String message, String key, String cipherAlgorithm) throws Exception{
		byte[] data = encrypt(message, key, cipherAlgorithm);
		return Base64.encodeBase64String(data);
	}
	
	/**
	 * 解密，指定填充方式
	 * @param message
	 * 			待解密消息字节数组
	 * @param key
	 * 			密钥
	 * @param cipherAlgorithm
	 * 			填充方式
	 * @return
	 * 			解密符串
	 * @throws Exception
	 */
	public static String decrypt(byte[] message, String key, String cipherAlgorithm) throws Exception {
		String result = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			kgen.init(KEY_SIZE, new SecureRandom(key.getBytes())); //SecureRandom 只要seed相同，随机数列就相同
			SecretKey secretKey = kgen.generateKey();
			
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] data = cipher.doFinal(message);
			result = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 解密
	 * @param encryptData
	 * 			待解密消息字节数组
	 * @param key
	 * 			密钥
	 * @return
	 * 			解密符串
	 * @throws Exception
	 */
	public static String decrypt(byte[] encryptData, String key) throws Exception {
		return decrypt(encryptData, key, CIPHER_ALGORITHM_PKCS5);
	}
	
	/**
	 * 解密
	 * @param message
	 * 			待解密消息（Base64编码）
	 * @param key
	 * 			密钥
	 * @return
	 * 			解密符串
	 * @throws Exception
	 */
	public static String decrypt(String message, String key) throws Exception {
		return decrypt(Base64.decodeBase64(message), key, CIPHER_ALGORITHM_PKCS5);
	}

}
