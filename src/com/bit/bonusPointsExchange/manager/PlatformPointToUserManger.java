package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Transfer;
import com.bit.bonusPointsExchange.utils.DBUtils;

public class PlatformPointToUserManger {
	//����ƽ̨�����ݿ�
	public boolean updatePointsPlatform(String userName, String shopName, int wantTransfer_points ){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update point set platformPoint=platformPoint-'"+wantTransfer_points+"' where userName='"+userName+"' and shopName='"+shopName+"'";
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
	//�����̼ҵ����ݿ⣨ģ�⣩
	public boolean updatePointsShop(String userName, String shopName,int wantTransfer_points){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update userpoint set userPoint=userPoint+'"+wantTransfer_points+"' where userName='"+userName+"' and shopName='"+shopName+"'";
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
	
	//��transfer���м�¼����
	public int insertTransfer(Transfer transfer){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		int count =0;
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			String sql="insert into transfer(pointID,status,point,transferTime,shopName) values('"+transfer.getPointID()+"','"+transfer.getStatus()+"','"+transfer.getPoint()+"','"+transfer.getTransferTime()+"','"+transfer.getShopName()+"')";
			count=stmt.executeUpdate(sql);//ִ�в�����䣬�����ز������ݵĸ���	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return count;		
	}
	
	//��ѯ���׵�pointID
	//��ѯ�û���ƽ̨���ݿ��еĻ���
		public int queryPointID(String userName, String shopName){
			Connection conn=DBUtils.getConnection();
			Statement stmt=null;
			int pointID = -1;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select pointID from point where userName='"+userName+"'and shopName='"+shopName+"'");
				if(rs.next()) {
					//System.out.println(rs.getString("userPoint"));
					pointID = rs.getInt("pointID");
					rs.close();
					return pointID;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pointID;
		}
}
