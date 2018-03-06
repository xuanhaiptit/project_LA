/**
 * Copyright(C) 2017  Luvina
 * 
 * MstGroupLogic.java, Oct, 31, 2017, HaiLX
 */
package Logic;

import java.sql.SQLException;
import java.util.List;

import entities.MstGroup;

/**
 * interface xử lí dữ liệu của bảng Group lấy từ MstGroupDao
 * 
 * @author LeXuanHai MstGroupLogic.java Oct 25, 2017
 */
public interface MstGroupLogic {
	/**
	 * Hàm lấy tất cả dữ liệu trong table Group
	 * 
	 * @return list danh sách Group
	 * @throws Exception 
	 */
	public List<MstGroup> getAllMstGroup() throws Exception;

	/**
	 * Kiểm tra groupId có tồn tại hay không
	 * 
	 * @return trả về true nếu tồn tại false nếu không
	 * @throws SQLException
	 */
	public boolean checkExistedGroup(int groupId) throws SQLException;
}
