package com.lgfei.tool.spider.common.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加解密Util
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2018年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class DesUtil
{
    /**
     * 加密方式
     */
    private static final String DES = "DES";
    
    private static final String FIRST_KEY = "d2luQDEyKg==";
    
    private static final byte[] FIRST_KEY_BYTE = Base64.getDecoder().decode(FIRST_KEY);
    
    /**
     * 默认编码
     */
    private static final String DEF_ENCODING = "UTF-8";
    
    /**
     * Description 根据键值进行加密
     * @param data 要加密的数据
     * @param key  加密键byte数组
     * @return 加密后的数据
     * @throws Exception 加密异常
     */
    public static String encrypt(String data, String key)
        throws Exception
    {
        String tempKey =
            new String(FIRST_KEY_BYTE, DEF_ENCODING) + new String(Base64.getDecoder().decode(key), DEF_ENCODING);
        byte[] bt = encrypt(data.getBytes(DEF_ENCODING), tempKey.getBytes(DEF_ENCODING));
        String strs = Base64.getEncoder().encodeToString(bt);
        return strs;
    }
    
    /**
     * Description 根据键值进行解密
     * @param data 要解密的数据
     * @param key  加密键byte数组
     * @return 解密后的数据
     * @throws IOException IO异常
     * @throws Exception 加密异常
     */
    public static String decrypt(String data, String key)
        throws IOException, Exception
    {
        if (data == null)
        {
            return null;
        }
        String tempKey =
            new String(FIRST_KEY_BYTE, DEF_ENCODING) + new String(Base64.getDecoder().decode(key), DEF_ENCODING);
        byte[] buf = Base64.getDecoder().decode(data);
        byte[] bt = decrypt(buf, tempKey.getBytes(DEF_ENCODING));
        return new String(bt, DEF_ENCODING);
    }
    
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key)
        throws Exception
    {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        
        return cipher.doFinal(data);
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key)
        throws Exception
    {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        
        return cipher.doFinal(data);
    }
    
}
