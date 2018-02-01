package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.RegistManager;
import com.bit.bonusPointsExchange.manager.UserManager;
import com.bit.bonusPointsExchange.utils.EmailUtils;
import com.bit.bonusPointsExchange.utils.Encode;

/**
 * ע��
 * @author gmx
 *
 */
public class RegistAction extends Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		RegistManager rm = new RegistManager();
		User user = new User();		
		String userName = request.getParameter("userName");
		String passwd = request.getParameter("passwd");
		String MD5password = Encode.MD5Encode(passwd);//����֮�������
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String phone = request.getParameter("phone");
		
		
		int regist=0;
		int count = rm.isRegist(userName);
		if(count==1){
			out.print("<script language='JavaScript'>alert('��������û���ע�ᣡ');location.href='/bonusPointsExchange/regist.jsp';</script>");
		}
        user.setUserName(userName);
        user.setPasswd(MD5password);
    	user.setEmail(email);
    	user.setFullName(fullName);
    	user.setPhone(phone);
   		//user.setActivated(false);
   		//user.setRandomCode(UUID.randomUUID().toString());
    	regist = rm.registUser(user);
    	
    	
   		if(regist>0){			
    		//EmailUtils.sendAccountActivateEmail(user);// ע��ɹ���,�����ʻ���������
   			request.setAttribute("registRes", "Y");//ע��ɹ�����ת����¼ҳ�����ע�ᣬͬʱ��ʾ�û�ע��ɹ�
   			request.getRequestDispatcher("login.jsp").forward(request, response);
   			//request.getRequestDispatcher("/login.jsp").forward(request, response);
   			//request.setAttribute("userName", userName);
   			//request.setAttribute("email", email);
    		//request.getRequestDispatcher("/registSuccess.jsp").forward(request, response);
    	}
   		else {
   			out.print("<script language='JavaScript'>alert('ע��ʧ�ܣ������½���ע�ᣡ');location.href='/bonusPointsExchange/regist.jsp';</script>");
		}
        
	}

}
