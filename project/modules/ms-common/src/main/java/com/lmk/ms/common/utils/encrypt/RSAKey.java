package com.lmk.ms.common.utils.encrypt;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.binary.Base64;

/**
 * 字符串形式的RSA密钥对
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/04
 *
 */
public class RSAKey {

	/** 公钥 */
	private String publicKey;
	
	/** 私钥 */
	private String privateKey;

	/** 密钥长度 */
	private Integer keySize;

	public RSAKey() {
		super();
	}
	
	public RSAKey(String publicKey, String privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public RSAKey(PublicKey publicKey, PrivateKey privateKey) {
		super();
		this.publicKey = Base64.encodeBase64String(publicKey.getEncoded());
		this.privateKey = Base64.encodeBase64String(privateKey.getEncoded());
	}

	public String getPublicKey() {
		return publicKey;
	}

	@JsonIgnore
	public byte[] getPublicKeyBytes() {
		return Base64.decodeBase64(publicKey);
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	@JsonIgnore
	public byte[] getPrivateKeyBytes() {
		return Base64.decodeBase64(privateKey);
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Integer getKeySize() {
		return keySize;
	}

	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}

	@Override
	public String toString() {
		return "{" +
				"\n\tpublicKey='" + publicKey + "'," +
				"\n\tprivateKey='" + privateKey + "'," +
				"\n}";
	}
}
