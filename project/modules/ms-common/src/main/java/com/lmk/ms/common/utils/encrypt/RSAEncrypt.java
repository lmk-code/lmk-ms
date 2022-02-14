package com.lmk.ms.common.utils.encrypt;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * RSA加密与解密工具类
 * <br> 默认密钥类型：PKCS#8
 * <br> 默认密钥长度：2048
 * <br> 默认签名算法：SHA256WithRSA
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/04
 *
 */
public class RSAEncrypt {
	
	/** 加密算法RSA */
	public static final String KEY_ALGORITHM = "RSA";
	
	/** 默认的密钥长度 */
	public static int KEY_SIZE_DEFAULT = 2048;

	/** 密钥类型：CS1 */
	public static String KEY_TYPE_CS1 = "PKCS#1";

	/** 密钥类型：CS8 */
	public static String KEY_TYPE_CS8 = "PKCS#8";

	/** 默认的密钥类型：CS8 */
	public static String KEY_TYPE_DEFAULT = KEY_TYPE_CS8;

	/** 签名算法：SHA1 */
	public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";

	/** 签名算法：SHA1 */
	public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256WithRSA";

	/** 签名算法：SHA256 */
	public static final String DEFAULT_SIGNATURE_ALGORITHM = SIGNATURE_ALGORITHM_SHA256;

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * 初始化密钥对
	 * <br >默认密钥长度：2048
	 * <br >默认密钥类型：PKCS#8
	 * @return
	 */
	public static KeyPair initKeyPair() {
		return initKeyPair(KEY_SIZE_DEFAULT);
	}

	/**
	 * 解析密钥对
	 * @param publicKey
	 * @param privateKey
	 * @return
	 */
	public static KeyPair initKeyPair(String publicKey, String privateKey) {
		PublicKey pubKey = getPublicKey(publicKey);
		PrivateKey priKey = getPrivateKey(privateKey);
		return new KeyPair(pubKey, priKey);
	}

	/**
	 * 初始化密钥对，指定密钥的长度
	 * @param keySize	密钥长度：512、1024、2048、4096
	 *                  <br>手动指定密钥长度，且不等于2048时，切记在解密时也需要传入密钥长度，否则会报错
	 * @return
	 */
	public static KeyPair initKeyPair(int keySize) {
		KeyPair keyPair = null;
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(keySize);
			keyPair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyPair;
	}

	/**
	 * 生成密钥对
	 * <br >默认密钥长度：2048
	 * <br >默认密钥类型：PKCS#8
	 * @return
	 */
	public static RSAKey initRsaKey() {
		return initRsaKey(KEY_SIZE_DEFAULT);
	}

	/**
	 * 生成密钥对
	 * @param keySize 密钥长度：512、1024、2048、4096
	 *                <br>手动指定密钥长度，且不等于2048时，切记在解密时也需要传入密钥长度，否则会报错
	 * @return
	 */
	public static RSAKey initRsaKey(int keySize) {
		KeyPair keyPair = initKeyPair(keySize);
		return new RSAKey(keyPair.getPublic(), keyPair.getPrivate());
	}

	/**
	 * 将Base64编码的字符串解析为公钥
	 * @param publicKey
	 * @return
	 */
	public static PublicKey getPublicKey(String publicKey) {
		byte[] bytes = TextEncrypt.getBytesBase64(publicKey);
		return getPublicKey(bytes);
	}

	/**
	 * 解析公钥
	 * @param publicKey
	 * @return
	 */
	public static PublicKey getPublicKey(byte[] publicKey) {
		PublicKey pubKey = null;
		try {
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			pubKey = keyFactory.generatePublic(x509KeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return pubKey;
	}

	/**
	 * 将Base64编码的字符串解析为私钥
	 * @param privateKey
	 * @return
	 */
	public static PrivateKey getPrivateKey(String privateKey) {
		byte[] bytes = TextEncrypt.getBytesBase64(privateKey);
		return getPrivateKey(bytes);
	}

	/**
	 * 解析私钥
	 * @param privateKey
	 * @return
	 */
	public static PrivateKey getPrivateKey(byte[] privateKey) {
		PrivateKey priKey = null;
		try {
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return priKey;
	}
	
	/**
	 * 快捷公钥加密
	 * @param message
	 * 			待加密的字符串（UTF-8编码）
	 * @param publicKey
	 * 			公钥（Base64编码）
	 * @return
	 * 			密文（Base64编码）
	 */
	public static String encryptByPublicKey(String message, String publicKey) {
		byte[] result = encryptByPublicKey(TextEncrypt.getBytesUtf8(message), TextEncrypt.getBytesBase64(publicKey));
		return TextEncrypt.getStringBase64(result);
	}

	/**
	 * 公钥加密
	 * @param data
	 *			待加密的字节数组
	 * @param publicKey
	 *			公钥
	 * @return
	 * 			加密消息的字节数组
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) {
		byte[] encryptData = null;

		try {
			PublicKey publicK = getPublicKey(publicKey);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对消息分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encryptData;
	}
	
	/**
	 * 公钥快捷解密
	 * <br>注意：如果手动指定密钥长度，且不等于2048时，请调用 decryptByPublicKey(String message, String publicKey, int keySize)方法
	 * @param message
	 * 			待解密的字符串（Base64编码）
	 * @param publicKey
	 * 			公钥（Base64编码）
	 * @return
	 * 			明文（UTF-8编码）
	 */
	public static String decryptByPublicKey(String message, String publicKey) {
		return decryptByPublicKey(message, publicKey, KEY_SIZE_DEFAULT);
	}

	/**
	 * 公钥快捷解密
	 * @param message
	 * 			待解密的字符串（Base64编码）
	 * @param publicKey
	 * 			公钥（Base64编码）
	 * @return
	 * 			明文（UTF-8编码）
	 * @param keySize
	 * 			密钥长度
	 * @return
	 */
	public static String decryptByPublicKey(String message, String publicKey, int keySize) {
		byte[] result = decryptByPublicKey(TextEncrypt.getBytesBase64(message), TextEncrypt.getBytesBase64(publicKey), keySize);
		return TextEncrypt.getStringUtf8(result);
	}

	/**
	 * 公钥解密
	 * @param encryptData
	 *			待解密消息的字节数组
	 * @param publicKey
	 *			公钥
	 * @param keySize
	 * 			密钥长度
	 * @return
	 */
	public static byte[] decryptByPublicKey(byte[] encryptData, byte[] publicKey, int keySize) {
		byte[] decryptedData = null;
		int maxDecryptBlock = keySize / 8;
		try {
			PublicKey publicK = getPublicKey(publicKey);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对消息分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxDecryptBlock) {
					cache = cipher.doFinal(encryptData, offSet, maxDecryptBlock);
				} else {
					cache = cipher.doFinal(encryptData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxDecryptBlock;
			}
			decryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return decryptedData;
	}
	
	/**
	 * 快捷私钥加密数据
	 * @param message
	 * 			待加密的字符串（UTF-8编码）
	 * @param privateKey
	 * 			私钥（Base64编码）
	 * @return
	 * 			密文（Base64编码）
	 */
	public static String encryptByPrivateKey(String message, String privateKey) {
		byte[] result = encryptByPrivateKey(TextEncrypt.getBytesUtf8(message), TextEncrypt.getBytesBase64(privateKey));
		return TextEncrypt.getStringBase64(result);
	}
	
	/**
	 * 私钥加密
	 * @param data
	 *			待加密的字节数组
	 * @param privateKey
	 *			私钥
	 * @return
	 * 			加密消息的字节数组
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) {
		byte[] encryptData = null;
		try {
			PrivateKey privateK = getPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对消息分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encryptData;
	}

	/**
	 * 快捷的私钥解密
	 * @param message
	 * 			待解密的字符串（Base64编码）
	 * @param privateKey
	 * 			私钥（Base64编码）
	 * @return
	 */
	public static String decryptByPrivateKey(String message, String privateKey) {
		return decryptByPrivateKey(message, privateKey, KEY_SIZE_DEFAULT);
	}

	/**
	 * 快捷的私钥解密
	 * @param message
	 * 			待解密的字符串（Base64编码）
	 * @param privateKey
	 * 			私钥（Base64编码）
	 * @param keySize
	 * 			密钥长度
	 * @return
	 * 			明文（UTF-8编码）
	 */
	public static String decryptByPrivateKey(String message, String privateKey, int keySize) {
		byte[] result = decryptByPrivateKey(TextEncrypt.getBytesBase64(message), TextEncrypt.getBytesBase64(privateKey), keySize);
		return TextEncrypt.getStringUtf8(result);
	}

	/**
	 * 私钥解密
	 * @param encryptData
	 *			待解密消息的字节数组
	 * @param privateKey
	 * 			私钥
	 * @param keySize
	 * 			密钥长度
	 * @return
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptData, byte[] privateKey, int keySize) {
		byte[] decryptedData = null;
		int maxDecryptBlock = keySize / 8;
		try {
			PrivateKey privateK = getPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对消息分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxDecryptBlock) {
					cache = cipher.doFinal(encryptData, offSet, maxDecryptBlock);
				} else {
					cache = cipher.doFinal(encryptData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxDecryptBlock;
			}
			decryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return decryptedData;
	}
	
	/**
	 * 快捷用私钥对信息生成数字签名
	 * @param message
	 * 			待签名的字符串（UTF-8编码）
	 * @param privateKey
	 * 			私钥（Base64编码）
	 * @return
	 * 			签名（Base64编码）
	 */
	public static String signByPrivateKey(String message, String privateKey){
		return signByPrivateKey(message, privateKey, DEFAULT_SIGNATURE_ALGORITHM);
	}
	
	/**
	 * 快捷用私钥对信息生成数字签名
	 * @param message
	 * 			待签名的字符串（UTF-8编码）
	 * @param privateKey
	 * 			私钥（Base64编码）
	 * @param algorithm
	 * 			签名算法
	 * @return
	 * 			签名（Base64编码）
	 */
	public static String signByPrivateKey(String message, String privateKey, String algorithm){
		byte[] result = signByPrivateKey(TextEncrypt.getBytesUtf8(message), TextEncrypt.getBytesBase64(privateKey), algorithm);
		return TextEncrypt.getStringBase64(result);
	}

	/**
	 * 用私钥对信息生成数字签名（默认使用SHA1WithRSA算法）
	 * @param message
	 *            待签名消息的字节数组
	 * @param privateKey
	 *            私钥
	 * @return 签名的字节数组
	 */
	public static byte[] signByPrivateKey(byte[] message, byte[] privateKey) {
		return signByPrivateKey(message, privateKey, DEFAULT_SIGNATURE_ALGORITHM);
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * @param message
	 *            待签名消息的字节数组
	 * @param privateKey
	 *            私钥
	 * @param algorithm
	 * 				签名算法
	 * @return 签名的字节数组
	 */
	public static byte[] signByPrivateKey(byte[] message, byte[] privateKey, String algorithm) {
		byte[] sign = null;
		try {
			PrivateKey privateK = getPrivateKey(privateKey);
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(privateK);
			signature.update(message);
			sign = signature.sign();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sign;
	}

	/**
	 * 公钥验证私钥的签名
	 * @param message
	 * 			待校验的字符串（Base64编码）
	 * @param sign
	 * 			签名（Base64编码）
	 * @param publicKey
	 * 			公钥（Base64编码）

	 * @return
	 */
	public static Boolean verifyByPublicKey(String message, String sign, String publicKey) {
		return verifyByPublicKey(message, sign, publicKey, DEFAULT_SIGNATURE_ALGORITHM);
	}

	/**
	 * 公钥验证私钥的签名
	 * @param message
	 * 			待校验的字符串（Base64编码）
	 * @param sign
	 * 			签名（Base64编码）
	 * @param publicKey
	 * 			公钥（Base64编码）
	 * @param algorithm
	 * 			签名算法
	 * @return
	 */
	public static Boolean verifyByPublicKey(String message, String sign, String publicKey, String algorithm) {
		byte[] messageData = TextEncrypt.getBytesUtf8(message);
		byte[] keyData = TextEncrypt.getBytesBase64(publicKey);
		byte[] signData = TextEncrypt.getBytesBase64(sign);
		return RSAEncrypt.verifyByPublicKey(messageData, signData, keyData, algorithm);
	}

	/**
	 * 公钥验证私钥的签名（默认使用SHA1WithRSA算法）
	 * @param message
	 *            待校验消息的字节数组
	 * @param sign
	 *            数字签名的字节数组
	 * @param publicKey
	 *            公钥
	 * @return 是否成功
	 */
	public static Boolean verifyByPublicKey(byte[] message, byte[] sign, byte[] publicKey) {
		return verifyByPublicKey(message, sign, publicKey, DEFAULT_SIGNATURE_ALGORITHM);
	}

	/**
	 * 公钥验证私钥的签名
	 * @param message
	 *            待校验消息的字节数组
	 * @param sign
	 *            数字签名的字节数组
	 * @param publicKey
	 *            公钥
	 * @param algorithm
	 *            签名算法
	 * @return 是否成功
	 */
	public static Boolean verifyByPublicKey(byte[] message, byte[] sign, byte[] publicKey, String algorithm) {
		Boolean pass = false;

		try {
			PublicKey publicK = getPublicKey(publicKey);
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(publicK);
			signature.update(message);
			pass = signature.verify(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pass;
	}
}
