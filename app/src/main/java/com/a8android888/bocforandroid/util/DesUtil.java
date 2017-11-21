package com.a8android888.bocforandroid.util;

import android.util.Base64;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加解密
 * 
 * @author Administrator
 * 
 */
public class DesUtil {

	private final static String DES = "DES";



	public static void main(String[] args) throws Exception {

//		/** 固定的不变key，用来验证uid **/
		String shareKey = "x@88Client.$";
//
//		/** 数据库未加密 **/
//		String webUid = "xinbo.com";// uid_id
//		String webKey = "!df378sa";
//
//		/** 客户端uid **/
//		String udd = Cryptogram.encryptByMD5(encrypt(webUid, shareKey));
//		System.out.println("udd:" + udd);
//		//System.out.println(decrypt(udd, shareKey));
//
//		/** 数据库与客户端des **/
//		String des = Cryptogram.encryptByMD5(encrypt(webUid, webKey));
//		System.out.println("des:" + des);
		
		//客户端传uid=tyc0088.com, udd=,des=
		
		String str = "a1|wap";
		String val = encrypt(str,shareKey);
		System.out.println(val);
		
	 

	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs =new String(Base64.encode(bt,Base64.DEFAULT)) ;
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
//	public static String decrypt(String data, String key) throws IOException, Exception {
//		if (data == null)
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		byte[] buf = decoder.decodeBuffer(data);
//		byte[] bt = decrypt(buf, key.getBytes());
//		return new String(bt);
//	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
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
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
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
