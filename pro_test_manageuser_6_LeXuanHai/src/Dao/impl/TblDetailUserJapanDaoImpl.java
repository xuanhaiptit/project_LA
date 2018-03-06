/**
 * Copyright(C) 2017  Luvina
 * 
 * TblDetailUserJapanDao.java, Oct 25, 2017, HaiLX
 */
package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.TblDetailUserJapanDao;
import entities.TblDetailUserJapan;

/**
 * Thao tác với database bảng tblUserDetailJapan
 * 
 * @author xuanh
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {
	PreparedStatement statement;

	/**
	 * Hàm khởi tạo TblUserDaoImpl không tham số
	 * 
	 */
	public TblDetailUserJapanDaoImpl() {
	}

	/**
	 * Hàm khởi tạo TblUserDaoImpl có tham số
	 * 
	 * @param conn
	 *            Connection truyền vào
	 */
	public TblDetailUserJapanDaoImpl(Connection conn) {
		this.conn = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Dao.TblDetailUserJapanDao#insertDetailUserJapan(Entities.TblDetailUserJapan)
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException {
		try {
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO tbl_detail_user_japan (user_id, code_level, start_date, end_date, total) ");
				sql.append(" VALUES (?, ?, ?, ?, ?);");
				int i = 1;

				statement = conn.prepareStatement(sql.toString());
				statement.setInt(i++, detailUserJapan.getIdUser());
				statement.setString(i++, detailUserJapan.getCodeLevel());
				statement.setDate(i++, detailUserJapan.getStartDate());
				statement.setDate(i++, detailUserJapan.getEndDate());
				statement.setInt(i++, detailUserJapan.getTotal());

				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Dao.TblDetailUserJapanDao#updateDetailUserJapan(Entities.TblDetailUserJapan)
	 */
	@Override
	public boolean updateDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException {
		try {
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE tbl_detail_user_japan ");
				sql.append("SET code_level=?, start_date=?, end_date=?, total =? ");
				sql.append(" where user_id = ?; ");

				statement = conn.prepareStatement(sql.toString());
				int temp = 1;
				statement.setString(temp++, detailUserJapan.getCodeLevel());
				statement.setDate(temp++, detailUserJapan.getStartDate());
				statement.setDate(temp++, detailUserJapan.getEndDate());
				statement.setInt(temp++, detailUserJapan.getTotal());
				statement.setInt(temp++, detailUserJapan.getIdUser());

				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Dao.TblDetailUserJapanDao#deletelDetailUserJapan(Entities.TblDetailUserJapan)
	 */
	@Override
	public boolean deleteDetailUserJapan(int idUser) throws SQLException {
		try {
			if (conn != null) {
				String sql = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?; ";

				statement = conn.prepareStatement(sql);
				statement.setInt(1, idUser);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Dao.TblDetailUserJapanDao#getCodeLevelByIdUser(Entities.TblDetailUserJapan)
	 */
	@Override
	public String getCodeLevelByIdUser(int id) throws SQLException {
		String codeLevel = "";
		try {
			if (conn != null) {
				String sql = "Select code_level from `tbl_detail_user_japan` WHERE `user_id`=?;";

				statement = conn.prepareStatement(sql);
				statement.setInt(1, id);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					codeLevel = rs.getString(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return codeLevel;
	}
}
