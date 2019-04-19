package com.ztsc.commonutils.encryption;

import com.ztsc.commonutils.base64.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准
 * @author stone
 * @date 2014-03-10 06:49:19
 */
public class AESUtils {
	private  static Cipher cipher;
	private static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	/*
     * AES/CBC/NoPadding 要求
     * 密钥必须是16位的；Initialization vector (IV) 必须是16位
     * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
     * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
     *
     *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
     *
     *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
     *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
     *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
     */
	private static SecretKey secretKey;

	public static void main(String[] args) throws Exception {
		AESUtils aes=new AESUtils("1234567890123450");
		String enStr=	aes.ECBencoode("aaa");
		System.out.println(enStr);
		String deStr=aes.ECBdecode(enStr);
		System.out.println(deStr);

	}

	public AESUtils(String key){
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			//KeyGenerator 生成aes算法密钥
			try {
				secretKey=new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//  secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}


	public static byte[] getIV() {
		byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,0xD, 91 };
		return iv;
	}
	/**
	 * 加密
	 * @return
	 */
	public String CBCencoode(String str){
		byte[] encrypt =null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥
			try {
				encrypt = cipher.doFinal(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} //按单部分操作加密或解密数据，或者结束一个多部分操作。
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return Base64Utils.encode(encrypt);

	}


	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	public  String CBCdecode(String str)   {
		byte[] decrypt=null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥
			decrypt = cipher.doFinal(Base64Utils.decode(str));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(decrypt);
	}

	/**
	 * 加密
	 * @return
	 */
	public String ECBencoode(String str){
		byte[] encrypt =null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥
			try {
				encrypt = cipher.doFinal(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} //按单部分操作加密或解密数据，或者结束一个多部分操作。
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return Base64Utils.encode(encrypt);
	}


	/**
	 * 使用AES 算法 加密，默认模式 AES/ECB/PKCS5Padding
	 */
	public  String ECBdecode(String str)   {
		byte[] decrypt=null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥
			decrypt = cipher.doFinal(Base64Utils.decode(str));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(decrypt);
	}

} 
