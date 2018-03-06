/**
 * Copyright(C) 2017  Luvina
 * 
 * MstJapanDaoImpl.java, Oct 25, 2017, HaiLX
 */
package Dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.MstJapanDao;
import entities.MstJapan;

/**
 * class xử lí dữ liệu của bảng mst_japan lấy từ MstJapanDao
 * 
 * @author LeXuanHai MstJapanDao.java Oct 25, 2017
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {
	PreparedStatement statement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.MstJapanDao#getAllMstJapan()
	 */
	// ok
	@Override
	public List<MstJapan> getAllMstJapan() throws Exception {
		List<MstJapan> listJapan = new ArrayList<>();
		try {
			conn = getConnect();
			if (conn != null) {
				String sql = "Select * from mst_japan;";
				statement = conn.prepareStatement(sql);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					MstJapan mstJapan = new MstJapan();
					int temp = 1;
					mstJapan.setCodeLevel(rs.getString(temp++));
					mstJapan.setNameLevel(rs.getString(temp++));
					listJapan.add(mstJapan);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return listJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.MstJapanDao#getLevelJapan(java.lang.String)
	 */
	@Override
	public String getLevelJapan(String codeLevel) throws SQLException {
		String levelJapan = "";
		try {
			conn = getConnect();
			if (conn != null) {

				String sql = "Select * from mst_japan WHERE code_level = ?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, codeLevel);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					levelJapan = rs.getString(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return levelJapan;
	}

}
