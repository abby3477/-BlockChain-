package com.bit.bonusPointsExchange.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * �����㷨
 */
public class Encode {

	/*����һ���ַ������������MD5���ܴ�*/
	public static String MD5Encode(String str) {
		if (null == str) {
			return null;
		}
		StringBuilder sB = new StringBuilder();//�����ɱ��ַ����еĶ���
		try {
			MessageDigest code = MessageDigest.getInstance("MD5");//��������ָ���㷨���Ƶ���ϢժҪ
			code.update(str.getBytes());
			byte[] bs = code.digest();
			for(int i = 0; i < bs.length; i++) {
				int v = bs[i]&0xFF;
				if(v < 16) {
					sB.append(0);
				}
				sB.append(Integer.toHexString(v));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sB.toString().toUpperCase();
	}
}
