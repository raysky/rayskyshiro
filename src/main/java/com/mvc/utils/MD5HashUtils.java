package com.mvc.utils;


import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5HashUtils {
	
	public static String asMD5(Object source, Object salt) {
		return new Md5Hash(source, salt).toBase64();
	}

	private MD5HashUtils() {}
	public static String getRandomGUID() throws Exception{
		Random myRand=new Random(new SecureRandom().nextLong());
	    MessageDigest md5=MessageDigest.getInstance("MD5");
	    StringBuffer sbValueBeforeMD5 = new StringBuffer();
	    sbValueBeforeMD5.append(InetAddress.getLocalHost().toString()).append(":");
	    sbValueBeforeMD5.append(Long.toString(System.currentTimeMillis())).append(":");
	    sbValueBeforeMD5.append(Long.toString(myRand.nextLong()));
	    md5.update(sbValueBeforeMD5.toString().getBytes());
	    byte[] array=md5.digest();
	    StringBuffer sb=new StringBuffer();
	    for (int j=0;j<array.length;++j){
	        int b=array[j] & 0xFF;
	        if (b<0x10) sb.append('0');
	        sb.append(Integer.toHexString(b));
	    }
	    return sb.toString();
	}
}
