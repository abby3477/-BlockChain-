package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;

//�̼�ע�����ݿ���ز�����
public class RegistShopManger {
	//��ѯ�̼��û����Ƿ����
	public boolean isShopNameExit(Shop shop){
		String shopName = shop.getShopName();//��ȡ�̼ҵ�����
		System.out.println(shopName);
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select *from shop where shopName='"+shopName+"'");
			if(rs.next()) {
				//System.out.println(rs.getString("userPoint"));
				rs.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return true;
	}
	//��ע���û��������ݵ����ݿ�
	public int insertShop(Shop shop){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		int count =0;//ע���Ƿ�ɹ���־�� 0Ϊʧ��
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			String sql="insert into shop(shopName,imageURL,password,email,number,shopProperty,shopDec,telephone) values('"+shop.getShopName()+"','"+shop.getImgUrl()+"','"+shop.getPassword()+"','"+shop.getEmail()+"','"+shop.getNumber()+"','"+shop.getShopProperty()+"','"+shop.getShopDec()+"','"+shop.getTelephone()+"')";
			count=stmt.executeUpdate(sql);//ִ�в�����䣬�����ز������ݵĸ���	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return count;		
	}
}
