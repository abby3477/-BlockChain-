package com.bit.bonusPointsExchange.utils;

import java.text.SimpleDateFormat; 
import java.util.Date;  
import java.util.Random;
/**
 * ����IP��ʱ�����λ����������ļ�ǰ׺����ֹ�ļ����ظ�
 */
public class IpTimeStamp {
	private SimpleDateFormat sdf = null;//��ʽ��ʱ�������  
    private String ip = null;//����һ��ip��ַ  
    public IpTimeStamp(){}//JavaBean�������޲ι���  
    public IpTimeStamp(String ip){  
        this.ip = ip;  
    }  
    public String getIPTimeRand(){  
        StringBuffer buf = new StringBuffer();  
        if(this.ip!=null){//ip��ַ��Ϊ�գ�����.���в��  
            String str[] = this.ip.split("\\.");  
            for(int i=0;i<str.length;i++){  
                buf.append(this.addZero(str[i],3));  
            }  
        }  
        buf.append(this.getTimeStamp());//û��IP��ַ����ֱ��׷��ʱ���  
        Random r = new Random();  
        for(int x=0;x<3;x++){//ѭ��ȡ������������10���������  
            buf.append(r.nextInt(10));//  
        }  
        return buf.toString();  
    }  
    public String getDate(){//ȡ�ø�ʽ���������ʱ��,��ȷ������  
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
        return this.sdf.format(new Date());  
    }  
    public String getTimeStamp(){//ȡ��ʱ���  
        this.sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
        return this.sdf.format(new Date());  
    }  
    public String addZero(String str,int len){//����λ������0����  
        StringBuffer buf = new StringBuffer();  
        buf.append(str);  
        while(buf.length()<len){  
            buf.insert(0,"0");  
        }  
        return buf.toString();  
    }  
}
