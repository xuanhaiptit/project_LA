/**
 * Copyright(C) 2017  Luvina
 * 
 * TblDetailUserJapanDao.java, Oct 25, 2017, HaiLX
 */
package Dao;

import java.sql.SQLException;

import entities.TblDetailUserJapan;

/**
 * Intercace xử lí dữ liệu tại bảng TblDetailUserJapan Thao tác với database
 * bảng tblUserDetailJapan
 * 
 * @author HaiLX
 *
 */
public interface TblDetailUserJapanDao {
	/**
	 * Thực hiện thêm mới 1 user vào DB
	 * 
	 * @param detailUserJapan
	 *            TblDetailUserJapan tblDetailUserJapan Đối tượng chứa thông tin của
	 *            TblDetailUserJapan
	 * @return Boolean true: thành công false: không thành công
	 * @throws SQLException
	 * 
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException;

	/**
	 * Thực hiện update 1 DetailUserJapan vào DB
	 * 
	 * @param detailUserJapan
	 *            TblDetailUserJapan tblDetailUserJapan Đối tượng chứa thông tin của
	 *            TblDetailUserJapan
	 * @return Boolean true: thành công false: không thành công
	 * @throws SQLException
	 * 
	 */
	public boolean updateDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException;

	/**
	 * Thực hiện deletel 1 DetailUserJapan trong DB
	 * 
	 * @param detailUserJapan
	 *            TblDetailUserJapan tblDetailUserJapan Đối tượng chứa thông tin của
	 *            TblDetailUserJapan
	 * @return Boolean true: thành công false: không thành công
	 * @throws SQLException
	 * 
	 */
	public boolean deleteDetailUserJapan(int idUser) throws SQLException;

	/**
	 * Thực hiện lấy code Level trong DB theo UserId
	 * 
	 * @param detailUserJapan
	 * @return nếu tồn tại trả về CodeLevel nếu không tồn tại trả về null
	 * @throws SQLException
	 */
	public String getCodeLevelByIdUser(int id) throws SQLException;
}
