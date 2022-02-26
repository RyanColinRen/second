package com.ny.dao;

import java.sql.SQLException;
import java.util.List;

import com.ny.model.shop;
import com.ny.util.JdbcUtils;

public class ShopDao {
	public shop queryById(shop shop) throws SQLException {
		String sql = "select * from shop s where s.id=?";
		return JdbcUtils.queryObject(sql, shop.class, shop.getId());
	}
	
	public List<shop> queryAll() throws SQLException {
		String sql = "select * from shop";
		return JdbcUtils.queryList(sql, shop.class);
	}
}
