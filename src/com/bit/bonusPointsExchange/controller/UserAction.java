package com.bit.bonusPointsExchange.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.UserManager;
import com.bit.bonusPointsExchange.utils.Encode;

/**
 * �û�ģ��
 * @author gmx
 *
 */
public class UserAction extends Action{

	UserManager um = new UserManager();
	User user = new User();
	String flag = "alterBefore";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub		
		
		String methodCode = request.getParameter("methodCode");
		if(methodCode.equals("alter_user_info")){			
			this.alterUserInfo(request, response);				
		}
		if(methodCode.equals("query_user_info")){
			this.queryUserInfo(request, response);
		}
		if(methodCode.equals("alter_user_passwd")){
			this.alterUserPasswd(request,response);
		}		
	}
	
	/**�޸��û�������Ϣ**/
	public void alterUserInfo(HttpServletRequest request, HttpServletResponse response){
		//String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String userName =(String)request.getSession().getAttribute("userName");
		String fullName = request.getParameter("fullName");
		String phone = request.getParameter("phone");		
		user.setUserName(userName);
		user.setEmail(email);
		user.setFullName(fullName);
		user.setPhone(phone);			
		int result = um.alterUserInfo(user);
		try {
			if(result>0){
				flag = "alterAfter";
				request.setAttribute("userChangeResult", "Y");
				this.queryUserInfo(request, response);
				
				//request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			}else {	
				request.setAttribute("userChangeResult", "N");
				request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**��ѯ�û���Ϣ**/
	public void queryUserInfo(HttpServletRequest request, HttpServletResponse response){	
		String userName=(String)request.getSession().getAttribute("userName");
		user = um.queryUserInfo(userName);	
		request.setAttribute("email", user.getEmail());
		request.setAttribute("fullName", user.getFullName());
		request.setAttribute("phone", user.getPhone());
		try {
			
			//if(flag.equals("alterBefore")){
			
			//request.setAttribute("userChangeResult", "Y");// �������ԣ���ʾ�޸���Ϣ�ɹ�����ǰ̨��ȡ������ʾ����
			request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			//}else if(flag.equals("alterAfter")){
			//	request.setAttribute("userChangeResult", "Y");// �������ԣ���ʾ�޸���Ϣ�ɹ�����ǰ̨��ȡ������ʾ����
			//	request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			//}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**�޸��û����� **/
	public void alterUserPasswd(HttpServletRequest request,HttpServletResponse response) {
		UserManager um = new UserManager();
		String userName = (String) request.getSession().getAttribute("userName");
		String oldPasswd = request.getParameter("oldPassword");
		String MD5password = Encode.MD5Encode(oldPasswd);//����֮�������
		String newPasswd = request.getParameter("newPassword");
		user = um.queryUserPasswd(userName);
		request.setAttribute("index", "2");
		if(user.getPasswd().equals(MD5password)){
			user = new User();
			user.setUserName(userName);
			user.setPasswd(newPasswd);
			int result = um.alterUserPasswd(user);
			try {
				if(result==1){					
					request.setAttribute("userChangePasswd", "Y");// �������ԣ���ʾ�޸���Ϣ�ɹ�����ǰ̨��ȡ������ʾ����
					request.getRequestDispatcher("login.jsp").forward(request, response);
					//this.queryUserInfo(request, response);
				}else{
					request.setAttribute("userChangePasswd", "N");
					request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
				}
			} catch (ServletException e) {
				// TODO Auto-generated catch block	
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			request.setAttribute("userChangePasswd", "errorPasswd");//����ľ��������
			try {
				request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

}
