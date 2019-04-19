package com.ztsc.commonutils.wechart;

import java.security.MessageDigest;

/**
 * Created by benchengzhou on 2018/6/1  14:04 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 微信提供的md5
 * 类    名： MD5
 * 备    注：
 */

public class MD5 {

	private MD5() {}

	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
