package com.ztsc.commonutils.bitmap;

import android.util.Base64;
import android.util.Log;

import com.ztsc.commonutils.logcat.LogUtil;

import java.io.UnsupportedEncodingException;


/**
 * 对密码进行加密 解密传输
 * @author yangming
 *
 */
public class Base64Util {
	
	  public final static String ENCODING = "UTF-8";  
	  
	    // 加密  
	    public static String encoded(String data) throws UnsupportedEncodingException {
		//byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
			byte[] b =Base64.encode(data.getBytes(ENCODING), Base64.DEFAULT);
	        return new String(b, ENCODING);  
	    }  
	  
	    // 加密,遵循RFC标准 ,一般不使用
	     public static String encodedSafe(String data) throws UnsupportedEncodingException {
		//byte[] b = Base64.encodeBase64(data.getBytes(ENCODING),true);
			 byte[] b =Base64.encode(data.getBytes(ENCODING), Base64.URL_SAFE);
	        return new String(b, ENCODING);  
	    }
	  
	    // 解密
	    public static String decode(String data) throws UnsupportedEncodingException {
	        byte[] b = Base64.decode(data.getBytes(ENCODING), Base64.DEFAULT);
	        return new String(b, ENCODING);
	    }
	
	public static void test() {
		try {
			String s="我是测试文本";
			String temp= "";
			LogUtil.e("解码前数据-----:"+s);
			temp= encoded(s);
			LogUtil.e("编码后的数据"+temp);
			temp= decode(temp);
			LogUtil.e("解码还原最原始的数据------:"+temp);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
