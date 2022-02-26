package com.ny.model;

public class user {
	/**
	 * 实体的属性一般与数据库中的属性一致
	 */
	private int id;
	private String name;
	private String password;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 实体中一般会对属性构建set方法和get方法
	 */
	
	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", password=" + password + "]";
	}
}
