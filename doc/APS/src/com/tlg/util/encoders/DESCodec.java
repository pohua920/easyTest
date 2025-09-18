package com.tlg.util.encoders;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
  
/** 
 * DES對稱加密算法 
 * @see ================================================================================================================= 
 * @see 對稱加密算法就是能將數據加解密。加密的時候使用密鑰對數據進行加密，解密的時候使用同樣的密鑰對數據進行解密 
 * @see DES是美國國家標準研究所提出的算法。由於加解密的數據安全性和密鑰長度成正比，故DES的56位密鑰已經形成安全隱患 
 * @see 後來針對DES算法進行了改進，有了三重DES算法（也稱DESede或Triple-DES）。全名是TDEA：Triple Data Encryption Algorithm 
 * @see DESede針對DES算法的密鑰長度較短以及迭代次數偏少問題做了相應改進，提高了安全強度 
 * @see 不過DESede算法處理速度較慢，密鑰計算時間較長，加密效率不高等問題使得對稱加密算法的發展不容樂觀 
 * @see ================================================================================================================= 
 * @see Java和BouncyCastle針對DES算法的數據加密支持是不同的，主要體現在密鑰長度、工作模式以及填充方式上 
 * @see Java6只支持56位密鑰，而BouncyCastle支持64位密鑰，它的官網是http://www.bouncycastle.org/ 
 * @see 即便是在DESede算法上，BouncyCastle的密鑰長度也要比Java的密鑰長度長 
 * @see ================================================================================================================= 
 * @see 另外，Java的API中僅僅提供了DES、DESede、PBE三種對稱加密算法密鑰材料實現類 
 * @see ================================================================================================================= 
 * @see 關於Java加解密的更多算法實現，可以參考此爺的博客http://blog.csdn.net/kongqz/article/category/800296 
 * @see ================================================================================================================= 
 */  
public class DESCodec {  
    //算法名稱  
    public static final String KEY_ALGORITHM = "DES";  
    //算法名稱/加密模式/填充方式  
    //DES共有四種工作模式-->>ECB：電子密碼本模式、CBC：加密分組鏈接模式、CFB：加密反饋模式、OFB：輸出反饋模式  
//    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    
      
    /** 
     * 生成密鑰 
     */  
    public static String initkey() throws NoSuchAlgorithmException {  
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); //實例化密鑰生成器 
        kg.init(56);                                               //初始化密鑰生成器  
        SecretKey secretKey = kg.generateKey();                    //生成密鑰  
        return Base64.encodeBase64String(secretKey.getEncoded());  //獲取二進制密鑰編碼形式  
    }  
  
    /** 
     * 轉換密鑰 
     */  
    private static Key toKey(byte[] key) throws Exception {  
        DESKeySpec dks = new DESKeySpec(key);                                      //實例化Des密鑰  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM); //實例化密鑰工廠  
        SecretKey secretKey = keyFactory.generateSecret(dks);                      //生成密鑰  
        return secretKey;  
    }  
      
    /** 
     * 加密數據 
     * @param data 待加密數據 
     * @param key  密鑰 
     * @return 加密後的數據 
     */  
    public static String encrypt(String data, String key, byte[] iv) throws Exception {  
//        Key k = toKey(Base64.decodeBase64(key));                           //還原密鑰  
    	Key k = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);              //實例化Cipher對象，它用於完成實際的加密操作
        IvParameterSpec iv2 = new IvParameterSpec(iv);
//        cipher.init(Cipher.ENCRYPT_MODE, k);                               //初始化Cipher對象，設置為加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, k, iv2);//IV的方式
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes())); //執行加密操作。加密後的結果通常都會用Base64編碼進行傳輸  
    }  
      
    /** 
     * 解密數據 
     * @param data 待解密數據 
     * @param key  密鑰 
     * @return 解密後的數據 
     */  
    public static String decrypt(String data, String key, byte[] iv) throws Exception {  
//        Key k = toKey(Base64.decodeBase64(key));  
    	Key k = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec iv2 = new IvParameterSpec(iv);
//        cipher.init(Cipher.DECRYPT_MODE, k);                           //初始化Cipher對象，設置為解密模式
        cipher.init(Cipher.DECRYPT_MODE, k, iv2);//IV的方式
        return new String(cipher.doFinal(Base64.decodeBase64(data)));  //執行解密操作  
    }  
      
    public static void main(String[] args) throws Exception {  
        String source = "E123456789,ET-1234,20121212,4";
        String key = "2220812A";
        byte[] iv = "24361263".getBytes();
        
        System.out.println("原文: " + source);  
          
//        String key = initkey();  
        System.out.println("密鑰: " + key);  
          
        String encryptData = encrypt(source, key, iv);  
        System.out.println("加密: " + encryptData);  
          
        String decryptData = decrypt(encryptData, key, iv);  
        System.out.println("解密: " + decryptData);  
    }  
} 
