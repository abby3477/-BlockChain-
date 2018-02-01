package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.utils.DBUtils;

/**
 * �û����ݿ����
 * @author gmx
 *
 */
public class UserManager {
	/*
	 * �����û����ݿ�
	 */
	
	private Connection conn = null;
	private Statement stmt = null;
	private String sql = null; 
	private ResultSet rs = null;
	private User user = null;
	
	public int alterUserInfo(User user){     //�޸��û� �������绰
		int count = 0;
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			sql="update user set fullName='"+user.getFullName()+"',phone='"+user.getPhone()+"' where userName='"+user.getUserName()+"'" ;
			count =stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}	
		
		return count;
	}
	
	public User queryUserInfo(String userName){  //��ѯ�û����䡢�������绰
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			sql="select email,fullName,phone from user where userName='"+userName+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				user=new User();				
				user.setEmail(rs.getString("email"));
				user.setFullName(rs.getString("fullName"));
				user.setPhone(rs.getString("phone"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}	
		return user;
		
	}
	
	public int alterUserPasswd(User user){  //�޸��û�����		
		conn=DBUtils.getConnection();
		int result = 0;
		try {
			stmt=conn.createStatement();
			sql="update user set passwd='"+user.getPasswd()+"' where userName='"+user.getUserName()+"'";
			result = stmt.executeUpdate(sql);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return result;
	}
	
	public User queryUserPasswd(String userName){ //��ѯ�û�����
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			sql="select passwd from user where userName ='"+userName+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				user = new User();
				user.setPasswd(rs.getString("passwd"));	
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}	
		return user;
	}
	
/*	public int queryUserByName(String userName){ //��ѯ�û��˺��Ƿ����
		Connection conn=DBUtils.getConnection();
		int result=0;						
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where userName='"+userName+"'");			
			System.out.println();
			if(rs.next())
				result=1;//�û��˺Ŵ���  					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}*/
	
	public int registUser(User user){ //ע���û�
		int count =0;//ע���Ƿ�ɹ���־�� 0Ϊʧ��
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			sql="insert into user(userName,passwd,email,fullName,phone) values('"+user.getUserName()+"','"+user.getPasswd()+"','"+user.getEmail()+"','"+user.getFullName()+"','"+user.getPhone()+"')";
			//sql="insert into user values('"+user.getUserName()+"','"+user.getPasswd()+"','"+user.getEmail()+"','"+user.getFullName()+"','"+user.getPhone()+"')";
			count=stmt.executeUpdate(sql);//ִ�в�����䣬�����ز������ݵĸ���	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(null, stmt, conn);
		}
		return count;			
		
	}
	
	public int queryUserByNameAndEmail(User user){
		int count = 0;
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			sql="select * from user where userName='"+user.getUserName()+"' and email='"+user.getEmail()+"'";
			rs=stmt.executeQuery(sql);
			if(rs.next())
				count = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return count;
		
	}
	public User queryUserByName(String userName){
		Connection conn=DBUtils.getConnection();				
		Statement stmt=null;
		User user = null;
		try {
			stmt = conn.createStatement();
			sql="select * from user where userName='"+userName+"'";
			rs=stmt.executeQuery(sql);			
			System.out.println();
			if(rs.next()){
				user.setUserName(rs.getString("userName"));
				user.setPasswd(rs.getString("passwd"));	
				user.setEmail("email");
				user.setFullName("fullName");
				user.setPhone("phone");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return user;
	}
	
}
