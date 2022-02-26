package com.ny.service;

import java.sql.SQLException;

import com.ny.dao.UserDao;
import com.ny.model.user;
import com.ny.util.JdbcUtils;

public class UserService {
	private UserDao userdao = new UserDao();
	
	/**
	 * 该类被LoginServlet调用
	 * login方法用于判断登录
	 * @param user 该user从LoginServlet传入，然后调用UserDao中querByName（根据用户名查找）查看是否存在该用户
	 * @return
	 * @throws SQLException
	 */
	public user login(user user,String pwd) throws SQLException {
		user u = null;
		try {
			u = userdao.queryByName(user);
			if(u==null) {
				throw new SQLException("没有该用户");
			}else if(!user.getPassword().equals(u.getPassword())) {
				throw new SQLException("用户名密码错误");
			}
		} finally {
			JdbcUtils.closeConnection();
		}
		return u;
	}
	
	public void register(String name,String Password) throws SQLException{
		userdao.insert(name, Password);//调用userdao中的插入方法
	}
}
