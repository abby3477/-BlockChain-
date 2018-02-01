package com.bit.bonusPointsExchange.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;

/**
 * ���͵�����
 * @author gmx
 */
public class EmailUtils {
	
	private static String propFileName="/com/bit/bonusPointsExchange/EmailUtils.properties";//ָ����Դ�ļ������λ��
	private static Properties prop = new Properties(); // ������ʵ����Properties�����ʵ��

	private static String emailFrom = null;
	private static String emailFromPasswd = null;
	 
	/*��̬����飬���ʼ��ʱ�������ݿ����� */ 
	static{ 
		try {
			InputStream in = EmailUtils.class.getClassLoader().getResourceAsStream(propFileName);
			prop.load(in);
			emailFrom = prop.getProperty("EMAIL_FROM");
			emailFromPasswd = prop.getProperty("EMAIL_FROM_PASSWD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ͨ���������������Properties�ļ�
		
	}
	  
	  
    /** 
     * ע��ɹ���,���û������ʻ��������ӵ��ʼ� 
     * @param user δ������û� 
     */  
    public static void sendAccountActivateEmail(User user) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("�ʻ������ʼ�");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  
            message.setContent("<a href='" + GenerateLinkUtils.generateActivateLink(user)+"'>��������ʻ�</a>","text/html;charset=utf-8");  
            // �����ʼ�  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * ���û����������������ӵ��ʼ� 
     */  
    public static void sendResetPasswordEmail(User user) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("�һ������ʻ�������");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  
            message.setContent("Ҫʹ���µ�����, ��ʹ������������������:<br/><a href='" + GenerateLinkUtils.generateResetPwdLink(user) +"'>���������������</a>","text/html;charset=utf-8");  
            // �����ʼ�  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * ���̼ҷ��������������ӵ��ʼ� 
     */  
    public static void sendResetPasswordEmail(Shop shop) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("�һ������ʻ�������");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(shop.getEmail()));  
            message.setContent("Ҫʹ���µ�����, ��ʹ������������������:<br/><a href='" + GenerateLinkUtils.generateResetPwdLink(shop) +"'>���������������</a>","text/html;charset=utf-8");  
            // �����ʼ�  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static Session getSession() {  
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.smtp.host", "smtp.qq.com");  
        props.setProperty("mail.smtp.port", "25");  
        props.setProperty("mail.smtp.auth", "true");  
        Session session = Session.getInstance(props, new Authenticator() {  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
        	/*    String password = null;  
               InputStream is = EmailUtils.class.getResourceAsStream("password.dat");  
                byte[] b = new byte[1024];  
                try {  
                    int len = is.read(b);  
                    password = new String(b,0,len);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
               */ 
            
                return new PasswordAuthentication(emailFrom, emailFromPasswd);  
            }  
              
        });  
        return session;  
    }  
}
