package com.blueshit.neweast.utils;

import com.blueshit.neweast.mvpicture.bean.PictureRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @author JackJun
 * @version 1.0
 * @email jackjun8652@gmail.com
 * @created 2014-2-26
 * @description DES
 */
public class DesUtil {
    private static byte[] iv = {-116, -90, 77, -23, -63, -79, 35, -89};


    /**
     * URLDecoder(Resin自动解)+Base64+DES解密、解码
     * @param decryptString 待解密字符串
     * @param key           密钥
     * @return
     */
    public static String decrypt(String decryptString, String key) throws Exception{
        byte[] byteMi = Base64.decode(decryptString);//BASE64解码
        return decryptDES(byteMi, key);
    }

    /**
     * DES解密
     *
     * @param decryptBytes 待解密的字节数组
     * @param decryptKey   解密密钥
     * @return
     * @throws Exception
     */
    public static String decryptDES(byte[] decryptBytes, String decryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = decryptCipher.doFinal(decryptBytes);
        return new String(decryptedData, Constant.Common.URLENC);
    }

    /**
     * DES+Base64加密、加码
     *
     * @param encryptString 待加密字符串
     * @param encryptKey    加密密钥
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = encryptCipher.doFinal(encryptString.getBytes(Constant.Common.URLENC));
        return Base64.encodeToString(encryptedData, true);
    }

    public static void main(String[] args) throws Exception {
        PictureRequest pictureRequest = new PictureRequest();
        pictureRequest.setPtype("1");
        pictureRequest.setPage(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(pictureRequest);
        String pwd = DesUtil.encryptDES(s,Constant.DECRYPTKEY);
        System.out.println(URLEncoder.encode(pwd,"UTF-8"));
    }
}
