package com.ny.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.ny.model.user;
import com.ny.util.JdbcUtils;

public class UserDao {
	
	//ע�ᣨ�����û���
	public void insert(String name,String password) throws SQLException {
		String sql = "insert into login_demo_user(name,password) values('"+ name + "','" + password + "')";
		int row = JdbcUtils.update(sql, null);
		System.out.println("��Ӱ��"+row+"��");
		JdbcUtils.closeConnection();
	}
	
	//�����û�����ѯ�û������̳������жϵ�¼��
	public user queryByName(user user) throws SQLException {
		String sql = "select * from login_demo_user u where u.name=?";
		return JdbcUtils.queryObject(sql, user.class, user.getName());
	}
	
}
