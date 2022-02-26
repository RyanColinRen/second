package com.ny.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ny.model.user;
import com.ny.service.UserService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//req.setCharacterEncoding()������ȷ�������������Ĳ����ı����ʽ�����ô�request��ȡ�õ�ֵ������ݿ���ȡ����ֵ��
		req.setCharacterEncoding("UTF-8");
		
		//�����name��password��jsp�����ģ�getParameter���ڴ�jsp��ȡ���ݣ�req�ǹ��������е�req
		String name = req.getParameter("name");
		String password = req.getParameter("pwd");
		
		//�����������ڴ��ݵ����ж��û���������
		user user = new user();
		user.setName(name);
		user.setPassword(password);
		
		//��ʼ���÷��������ж�
		try {
			UserService usv = new UserService();
			user = usv.login(user,password);
			req.setAttribute("user", user.getName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			req.getRequestDispatcher("/loginSuccess.jsp").forward(req, resp);
		}
	}
}
