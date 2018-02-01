package com.bit.bonusPointsExchange.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletRequest;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;

/**
 * �����ʻ��������������������� 
 * @author gmx
 */
public class GenerateLinkUtils {
	   private static final String CHECK_CODE = "checkCode";  
	      
	    /** 
	     * �����ʻ��������� 
	     */  
	    public static String generateActivateLink(User user) {  
	        return "http://localhost:8080/bonusPointsExchange/activateAccountServlet?userName="   
	                + user.getUserName() + "&" + CHECK_CODE + "=" + generateCheckcode(user);  
	    }  
	       	        
	    /** 
	     * �����û�������������� 
	     */  
	    public static String generateResetPwdLink(User user) {  
	    	return "http://localhost:8080/bonusPointsExchange/retrievePassword_2.jsp?userName="   
            + user.getUserName() + "&" + CHECK_CODE + "=" + generateCheckcode(user)+"&method=resetPasswd_user";
	    }  
	     
	    /** 
	     * �����̼�������������� 
	     */  
	    public static String generateResetPwdLink(Shop shop) {  
	    	   System.out.println(shop.getShopName());
	        return "http://localhost:8080/bonusPointsExchange/retrievePassword_2.jsp?userName="   
	                + shop.getShopName()+"&method=resetPasswd_shop";  
	        
	    	
	    }  
	    /** 
	     * ������֤�ʻ���MD5У���� 
	     * @param user  Ҫ������ʻ� 
	     * @return ���û������������Ϻ�ͨ��md5���ܺ��16���Ƹ�ʽ���ַ��� 
	     */  
	    public static String generateCheckcode(User user) {  
	        String userName = user.getUserName();  
	        String randomCode = user.getRandomCode();  
	        return md5(userName + ":" + randomCode);  
	    }  
	    

	      
	      
	    /** 
	     * ��֤У�����Ƿ��ע��ʱ���͵���֤��һ�� 
	     * @param user Ҫ������ʻ� 
	     * @param checkcode ע��ʱ���͵�У���� 
	     * @return ���һ�·���true�����򷵻�false 
	     */  
	    public static boolean verifyCheckcode(User user,ServletRequest request) {  
	        String checkCode = request.getParameter(CHECK_CODE);  
	        return generateCheckcode(user).equals(checkCode);  
	    }  
	  
	    private static String md5(String string) {  
	        MessageDigest md = null;  
	        try {  
	            md = MessageDigest.getInstance("md5");  
	            md.update(string.getBytes());  
	            byte[] md5Bytes = md.digest();  
	            return bytes2Hex(md5Bytes);  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return null;  
	    }  
	      
	    private static String bytes2Hex(byte[] byteArray)  
	    {  
	        StringBuffer strBuf = new StringBuffer();  
	        for (int i = 0; i < byteArray.length; i++)  
	        {  
	            if(byteArray[i] >= 0 && byteArray[i] < 16)  
	            {  
	                strBuf.append("0");  
	            }  
	            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));  
	        }  
	        return strBuf.toString();  
	    } 
}
