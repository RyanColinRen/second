package com.ny.service;

import java.sql.SQLException;

import com.ny.dao.UserDao;
import com.ny.model.user;
import com.ny.util.JdbcUtils;

public class UserService {
	private UserDao userdao = new UserDao();
	
	/**
	 * ���౻LoginServlet����
	 * login���������жϵ�¼
	 * @param user ��user��LoginServlet���룬Ȼ�����UserDao��querByName�������û������ң��鿴�Ƿ���ڸ��û�
	 * @return
	 * @throws SQLException
	 */
	public user login(user user,String pwd) throws SQLException {
		user u = null;
		try {
			u = userdao.queryByName(user);
			if(u==null) {
				throw new SQLException("û�и��û�");
			}else if(!user.getPassword().equals(u.getPassword())) {
				throw new SQLException("�û����������");
			}
		} finally {
			JdbcUtils.closeConnection();
		}
		return u;
	}
	
	public void register(String name,String Password) throws SQLException{
		userdao.insert(name, Password);//����userdao�еĲ��뷽��
	}
}
