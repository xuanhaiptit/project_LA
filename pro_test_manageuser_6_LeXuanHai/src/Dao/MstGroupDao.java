/**
 * Copyright(C) 2017  Luvina
 * 
 * MstGroupDao.java, Oct 25, 2017, HaiLX
 */
package Dao;

import java.sql.SQLException;
import java.util.List;

import entities.MstGroup;

/**
 * 
 * interface xử lí dữ liệu của bảng Group trong DB
 * @author LeXuanHai
 *	MstGroupDao.java
 * Oct 25, 2017
 */
public interface MstGroupDao {
	/**
	 * Hàm lấy tất cả dữ liệu trong table Group
	 * @return list danh sách Group
	 * @throws Exception 
	 */
	public List<MstGroup> getAllMstGroup() throws Exception;
	/**
	 * Lấy tên group theo id
	 * @param groupId mã group
	 * @return trả về tên group tương ứng
	 * @throws SQLException
	 */
	String getGroupName(int groupId) throws SQLException;
}
