package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.utils.DBUtils;

//�ϴ�ͷ�����ݿ������
public class UploadHeadIconManger {
	//�������ݿ��е�imageURL�ֶ�
	public static boolean updateImgURL(String imageURL, String shopName) {
		System.out.println(imageURL);//��ӡ·	
		//�����1��2 
		//1.���ݿ��д洢��·��б�ܲ����ˣ����ѽ�����Լ�ƴ��·�����������е���
		//2.ͬһ���û�ֻ����һ��ͷ�����ϴ�ͷ��֮ǰӦ�ý�ԭ����ͷ��ɾ���������ݿ��ж���ԭʼ·����ɾ����
		
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		//System.out.println(wantTransfer_points);
		try {
			stmt = conn.createStatement();
			String sql="update shop set imageURL='"+imageURL+"' where shopName='"+shopName+"'";
			//System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			if(res != 0) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return false;
	}
	
	
	//��ѯ���ݿ��е�imageURL�ֶ�
	public static String queryImgURL(String shopName) {
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		String imageURL = null;
		//System.out.println(shopName);
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select imageURL from shop where shopName='"+shopName+"'");
			if(rs.next()) {
				imageURL = rs.getString("imageURL");
				rs.close();
				return imageURL;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return imageURL;
	}
}
