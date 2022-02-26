package com.ny.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.ny.model.user;
import com.ny.util.JdbcUtils;

public class UserDao {
	
	//注册（新增用户）
	public void insert(String name,String password) throws SQLException {
		String sql = "insert into login_demo_user(name,password) values('"+ name + "','" + password + "')";
		int row = JdbcUtils.update(sql, null);
		System.out.println("已影响"+row+"行");
		JdbcUtils.closeConnection();
	}
	
	//根据用户名查询用户（本教程用于判断登录）
	public user queryByName(user user) throws SQLException {
		String sql = "select * from login_demo_user u where u.name=?";
		return JdbcUtils.queryObject(sql, user.class, user.getName());
	}
	
}
