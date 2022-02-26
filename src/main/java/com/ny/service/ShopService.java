package com.ny.service;

import java.sql.SQLException;
import java.util.List;

import com.ny.dao.ShopDao;
import com.ny.model.shop;
import com.ny.util.JdbcUtils;

public class ShopService {
	ShopDao SD = new ShopDao();
	
	public List<shop> show() throws SQLException {
		List<shop> list = null;
		try {
			list = SD.queryAll();
		} finally {
			JdbcUtils.closeConnection();
		}
		return list;
	}
}
