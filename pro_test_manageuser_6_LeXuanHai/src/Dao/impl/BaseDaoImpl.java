/**
 * Copyright(C) 2017  Luvina
 * 
 * BaseDao.java, Oct, 31, 2017, HaiLX
 */
package Dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Dao.BaseDao;
import properties.ConfigMysql;

/**
 * Class xử lí dữ liệu liên quan đến kết nối DB, kế thừa từ BaseDao
 * 
 * @author LeXuanHai BaseDaoImpl.java Oct 24, 2017
 */
public class BaseDaoImpl implements BaseDao {
	protected Connection conn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.BaseDao#closeConnect()
	 */
	@Override
	public void closeConnect() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.BaseDao#checkConnectToDB()
	 */
	@Override
	public boolean checkConnectToDB() {
		if (conn != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.BaseDao#getConnect()
	 */
	@Override
	public Connection getConnect() {
		String classname = ConfigMysql.getString("classname");
		String url = ConfigMysql.getString("url");
		String user = ConfigMysql.getString("username");
		String password = ConfigMysql.getString("password");
		try {
			Class.forName(classname);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("Ket noi khong thanh cong");
		}
		return conn;
	}

}
