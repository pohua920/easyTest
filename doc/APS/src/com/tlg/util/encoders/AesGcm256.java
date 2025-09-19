package com.tlg.util.encoders;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

/**
 * 
 * @author bi086
 *
 */
public class AesGcm256 {
	
	static{
		if(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		      Security.addProvider(new BouncyCastleProvider());
		}
		if(Security.getProvider(BouncyCastleJsseProvider.PROVIDER_NAME) == null) {
		      Security.addProvider(new BouncyCastleJsseProvider());
		}
	}

    // Pre-configured Encryption Parameters
    private static final int TAG_LENGTH_BIT = 128;

    private AesGcm256() {
    }


    public static String encrypt(String plaintext, String key, String iv) throws Exception {
    	byte[] plainBytes = plaintext.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, Base64.decodeBase64(iv));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        return Base64.encodeBase64String( cipher.doFinal(plainBytes));
    }

	public static String decrypt(String ciphertext, String key, String iv)
			throws Exception {
		byte[] encryptedBytes = Base64.decodeBase64(ciphertext);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		SecretKeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, Base64.decodeBase64(iv));
		cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
		return new String(cipher.doFinal(encryptedBytes), Charset.forName("UTF-8"));
	}
    
	public static void main(String[] args) throws Exception {

		//TEST
		String iv3 = "4O+IZur9B4mdfXGi";
		String key3 = "bmH7gK3czav7vfoY9kxm9JoEpDl6DEWI6rYGM+d+Wl4=";
		String content3 = "保經科技";
		long lStart3 = System.currentTimeMillis();
		String enString3 = AesGcm256.encrypt(content3, key3, iv3);
		System.out.println("加密後的字串是：" + enString3);
		long lUseTime3 = System.currentTimeMillis() - lStart3;
		System.out.println("加密耗時：" + lUseTime3 + "毫秒");
	}

}