package com.lmk.ms.common.utils.encrypt;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.lmk.ms.common.utils.IdUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 加密/解密测试
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/04
 */
public class EncryptTest {

    @Test
    public void testGetId(){
        for (int i = 0; i < 10; i++) {
            System.out.println(IdUtils.snowflakeId());
        }
    }

    /**
     * 测试：私钥加密、签名，公钥解密、验签
     */
    // @Test
    public void testRsaEncrypt(){
        System.out.println("测试：私钥加密、签名，公钥解密、验签");

        RSAKey rsaKey = RSAEncrypt.initRsaKey();
        System.out.println(rsaKey);

        String plainText = "早在 2016 年，微软就以解决方案成员的身份加入 Eclipse 基金会，并在当时提供了一套开发工具和服务。现在，为了与 Eclipse 社区进行更密切的合作，微软宣布将其参与范围扩大到战略成员，而首席技术官 Azure 办公室首席项目经理 Stephen Walli 则将加入基金会的董事会，并在文中解释了此举的原因。";
        System.out.println("明文：" + plainText);

        String encryptText = RSAEncrypt.encryptByPrivateKey(plainText, rsaKey.getPrivateKey());
        System.out.println("加密：" + encryptText);

        String sign = RSAEncrypt.signByPrivateKey(encryptText, rsaKey.getPrivateKey());
        System.out.println("签名：" + sign);

        Boolean verify = RSAEncrypt.verifyByPublicKey(encryptText, sign, rsaKey.getPublicKey());
        System.out.println("验签：" + verify);

        String decryptText = RSAEncrypt.decryptByPublicKey(encryptText, rsaKey.getPublicKey());
        System.out.println("解密：" + decryptText);
    }

    /**
     * 测试：公钥加密、私钥解密
     * 注意：公钥是公开的，不能用公钥签名
     */
    // @Test
    public void testRsaEncryptReverse(){
        System.out.println("测试：公钥加密，私钥解密");

        RSAKey rsaKey = RSAEncrypt.initRsaKey();
        System.out.println(rsaKey);

        String plainText = "早在 2016 年，微软就以解决方案成员的身份加入 Eclipse 基金会，并在当时提供了一套开发工具和服务。现在，为了与 Eclipse 社区进行更密切的合作，微软宣布将其参与范围扩大到战略成员，而首席技术官 Azure 办公室首席项目经理 Stephen Walli 则将加入基金会的董事会，并在文中解释了此举的原因。";
        System.out.println("明文：" + plainText);

        String encryptText = RSAEncrypt.encryptByPublicKey(plainText, rsaKey.getPublicKey());
        System.out.println("加密：" + encryptText);

        String decryptText = RSAEncrypt.decryptByPrivateKey(encryptText, rsaKey.getPrivateKey());
        System.out.println("解密：" + decryptText);
    }

    /**
     * 测试解析密钥
     */
    // @Test
    public void testGetKeys(){
        /** 公钥 */
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKW/x6gznhzj4Ym1ZzeU+F12Qn/ttWw+ACXzEMxdUO0NgAHUCgBf/Olv7Fp1FepfnCnkCHvdJ5LR6j3DAh8j7CUCAwEAAQ==";

        /** 私钥 */
        String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEApb/HqDOeHOPhibVnN5T4XXZCf+21bD4AJfMQzF1Q7Q2AAdQKAF/86W/sWnUV6l+cKeQIe90nktHqPcMCHyPsJQIDAQABAkEAk301H9JWtuR8lDqMj2QLIbeS8x1GG/yLyzkgUIoU8eprx2W06oSNYSIlvQ0j3gkGvO/Iztp7b7SGniv4qE77wQIhANBXvK43CRJ8vuTPnARtABEPstk0O+7xKQvZsepbAnD1AiEAy6nSQv8NEimVE2X6gFuW+7AMgS+w37y5Ee7QByiF0HECIGd1+nPFTtMIcb+svXyRKIRQeG9WeiDJT6nb3HdV5Jt1AiAJAxa+yDJ330cteJHBJExmDsA+5zFYdS1rUbn61xw0wQIhALi3NxQc6TjMjzFBwOflNUR15h72WEKH5iltt/j5dFrP";

        PublicKey pubKey = RSAEncrypt.getPublicKey(publicKey);
        Assertions.assertNotNull(pubKey, "公钥解析失败");

        PrivateKey priKey = RSAEncrypt.getPrivateKey(privateKey);
        Assertions.assertNotNull(priKey, "私钥解析失败");

        String plainText = "你好啊，这是测试消息";
        System.out.println("明文：" + plainText);

        String encryptText = RSAEncrypt.encryptByPrivateKey(plainText, privateKey);
        System.out.println("私钥加密：" + encryptText);

        String decryptText = RSAEncrypt.decryptByPublicKey(encryptText, publicKey);
        System.out.println("公钥解密：" + decryptText);
    }
}
