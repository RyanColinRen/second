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
		//req.setCharacterEncoding()：用来确保发往服务器的参数的编码格式，设置从request中取得的值或从数据库中取出的值。
		req.setCharacterEncoding("UTF-8");
		
		//这里的name与password是jsp传来的，getParameter用于从jsp获取数据，req是构建方法中的req
		String name = req.getParameter("name");
		String password = req.getParameter("pwd");
		
		//创建对象用于传递调用判断用户名与密码
		user user = new user();
		user.setName(name);
		user.setPassword(password);
		
		//开始调用方法进行判断
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
