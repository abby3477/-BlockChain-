package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;

//�̼��޸��������ݿ������
public class ShopChangePwdManger {
	//����ƽ̨�����ݿ�
		public boolean updateShopPwd(String shopName, String newPassword){
			Connection conn=DBUtils.getConnection();
			Statement stmt=null;
			//System.out.println(wantTransfer_points);
			//System.out.println(userName);
			//System.out.println(shopName);
			try {
				stmt = conn.createStatement();
				String sql="update shop set password='"+newPassword+"' where shopName='"+shopName+"'";
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
}
