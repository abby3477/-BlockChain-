package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit.bonusPointsExchange.bean.ShopPointChange;
import com.bit.bonusPointsExchange.bean.UserAmountAweek;
import com.bit.bonusPointsExchange.utils.DBUtils;

//���ܷ���ģ�����ݿ������
public class IntelligentAnalysisManger {
	//��ѯ�������ÿһ���Ӧ�İ��̼���
	public List<UserAmountAweek> queryBindUserAmount(String shopName){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		List<UserAmountAweek> list = new ArrayList<UserAmountAweek>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select bindtime, count(*) from point where date_sub(curdate(), INTERVAL 7 DAY) <= date(`bindtime`) and shopName = '"+shopName+"' group by bindtime");
			while(rs.next()) {//��װ��list��
				UserAmountAweek amountAweek = new UserAmountAweek();
				amountAweek.setNum(rs.getInt(2));
				amountAweek.setTime(rs.getString("bindtime"));
				list.add(amountAweek);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
	
	//��ѯ��������̼ҳ�����������ı仯
	public List<ShopPointChange> queryShopPointInOut(String shopName){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		List<ShopPointChange> list = new ArrayList<ShopPointChange>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT transferTime, SUM( CASE WHEN status = '1' THEN point ELSE 0 END),SUM( CASE WHEN status = '0' THEN point ELSE 0 END) FROM  transfer where shopName='"+shopName+"' and date_sub(curdate(), INTERVAL 7 DAY) <= date(`transferTime`) group by transferTime");
			while(rs.next()) {//��װ��list��
				ShopPointChange pointChangeAweek = new ShopPointChange();
				pointChangeAweek.setInNum(rs.getInt(2));//�����
				pointChangeAweek.setOutNum(rs.getInt(3));//������
				pointChangeAweek.setTime(rs.getString("transferTime"));
				list.add(pointChangeAweek);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
}
