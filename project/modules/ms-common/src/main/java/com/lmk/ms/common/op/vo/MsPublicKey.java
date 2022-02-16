package com.lmk.ms.common.op.vo;

/**
 * 用于前端加密的密钥对
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2022/02/15
 */
public class MsPublicKey {

    /** 密钥ID */
    private String id;

    /** 公钥 */
    private String publicKey;

    public MsPublicKey() {
    }

    public MsPublicKey(String id, String publicKey) {
        this.id = id;
        this.publicKey = publicKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
