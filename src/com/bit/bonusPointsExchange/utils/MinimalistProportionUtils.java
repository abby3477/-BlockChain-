package com.bit.bonusPointsExchange.utils;
//�������������������㷨
public class MinimalistProportionUtils {
	public static String minimalistProportion(int m,int n) {
		
		String res = null;
		int tmp = m;
	    if(m > n) {
	        tmp = n;
	    }
	    for(int i = tmp; i > 0; i--) {
	        if(m % i == 0 && n % i ==0) {
	        	res =  (m / i) + ":" + (n / i);
	            break;
	        }
	    }
		return res;
	}
}
