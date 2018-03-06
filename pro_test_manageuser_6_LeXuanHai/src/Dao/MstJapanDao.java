/**
 * Copyright(C) 2017  Luvina
 * 
 * MstJapanDao.java, Oct 25, 2017, HaiLX
 */
package Dao;

import java.sql.SQLException;
import java.util.List;

import entities.MstJapan;

/**
 * interface xử lí dữ liệu của bảng mst_japan lấy từ MstJapanDao
 * 
 * @author LeXuanHai MstJapanDao.java Oct 25, 2017
 */
public interface MstJapanDao {
	/**
	 * Hàm lấy tất cả dữ liệu trong table Group
	 * 
	 * @return list danh sách MstJapan
	 * @throws Exception 
	 */
	public List<MstJapan> getAllMstJapan() throws Exception;

	/**
	 * Lấy level japan theo code level
	 * 
	 * @param codeLevel
	 *            truyền vào codelevel
	 * @return trả về level japan tương ứng
	 * @throws SQLException
	 */
	public String getLevelJapan(String codeLevel) throws SQLException;
}
