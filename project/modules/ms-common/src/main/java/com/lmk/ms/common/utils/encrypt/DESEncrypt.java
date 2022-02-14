package com.lmk.ms.common.utils.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密与解密工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/04
 */
public class DESEncrypt {

	/** 密钥算法 */
	private static final String KEY_ALGORITHM = "DES";

	/** 密算法/工作模式/填充方式 */
	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	/**
	 * 加密消息
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] message, byte[] key) throws Exception {
		byte[] data = null;
		try {
			SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			data = cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 解密
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] message, byte[] key) throws Exception {
		byte[] result = null;
		try {
			SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			result = cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] decrypt(byte[] message, Key key) throws Exception {
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
