package com.yanlei.springboot;


import com.yanlei.springboot.util.Base64Util;
import com.yanlei.springboot.util.MD5;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * @Author: x
 * @Date: Created in 11:03 2018/12/5
 */
public class RSACrypt {
    /**
     * 生成RAS公钥与私钥字符串，直接返回
     *
     * @return
     */
    public static HashMap<String, String> getKeys() {
        HashMap<String, String> map = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥字符串
        String publicKey = base64ToStr(keyPair.getPublic().getEncoded());
        // 得到私钥字符串
        String privateKey = base64ToStr(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 根据公钥字符串加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空", e);
        }
    }

    /**
     * 根据私钥字符串加载私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return base64ToStr(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("密文数据已损坏");
        }
    }

    public static String base64ToStr(byte[] b) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(b);
    }

    public static byte[] strToBase64(String str) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
    }

    public static void main(String[] args) throws Exception {
        //初始化阶段，初始化后生成秘钥对
        //公钥发送给消息发送方用于加密传输数据；私钥严格保存于消息接收方，收到加密的消息之后进行解密
        HashMap<String, String> map = RSACrypt.getKeys();
        String privateKeyStr=map.get("privateKey");
        String publicKeyStr="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMbfbeY+gHYmOLC2WVAvCg1R2yOsjVhEK/tC118eYwKl4OJCK6UcIOilpJ6txGZdR7VHQxviA/Mr4hJx/KsHVmECAwEAAQ==";
        System.out.println("初始化私钥为："+privateKeyStr);
        System.out.println("初始化共钥为："+publicKeyStr);

        //消息发送方
        String originData="LuXi_666";
        System.out.println("信息原文："+originData);
        String encryptData=RSACrypt.encrypt(RSACrypt.loadPublicKey(publicKeyStr),originData.getBytes());
        System.out.println("加密后："+encryptData);
        String sha1 = Base64Util.getSha1(encryptData);
        System.out.println(sha1);
        String s = "TRS_Sign_LX_666";
        long timeStampSec = System.currentTimeMillis()/1000;
        String timestamp = String.format("%010d", timeStampSec);
        System.out.println("tm参数:"+timestamp);
        String s1 = null;
        s1 = sha1+timestamp+s;
        System.out.println("sn拼接参数"+s1);
        String s2 = MD5.MD5Encode(s1);
        System.out.println("sn参数加密后:"+s2);

        //消息接收方
        //String decryptData=RSACrypt.decrypt(RSACrypt.loadPrivateKey(privateKeyStr),RSACrypt.strToBase64(encryptData));
        //System.out.println("解密后："+decryptData);
    }
}
