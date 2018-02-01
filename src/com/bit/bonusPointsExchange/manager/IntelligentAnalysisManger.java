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

//智能分析模块数据库操作类
public class IntelligentAnalysisManger {
	//查询最近七天每一天对应的绑定商家量
	public List<UserAmountAweek> queryBindUserAmount(String shopName){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		List<UserAmountAweek> list = new ArrayList<UserAmountAweek>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select bindtime, count(*) from point where date_sub(curdate(), INTERVAL 7 DAY) <= date(`bindtime`) and shopName = '"+shopName+"' group by bindtime");
			while(rs.next()) {//封装到list中
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
	
	//查询最近七天商家出入积分数量的变化
	public List<ShopPointChange> queryShopPointInOut(String shopName){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		List<ShopPointChange> list = new ArrayList<ShopPointChange>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT transferTime, SUM( CASE WHEN status = '1' THEN point ELSE 0 END),SUM( CASE WHEN status = '0' THEN point ELSE 0 END) FROM  transfer where shopName='"+shopName+"' and date_sub(curdate(), INTERVAL 7 DAY) <= date(`transferTime`) group by transferTime");
			while(rs.next()) {//封装到list中
				ShopPointChange pointChangeAweek = new ShopPointChange();
				pointChangeAweek.setInNum(rs.getInt(2));//入积分
				pointChangeAweek.setOutNum(rs.getInt(3));//出积分
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
