/**
 * Copyright(C) 2017  Luvina
 * 
 * MstJapanLogic.java, Oct, 31, 2017, HaiLX
 */
package Logic;

import java.sql.SQLException;
import java.util.List;

import entities.MstJapan;

/**
 * interface xử lí dữ liệu của bảng mst_Japan lấy từ MstJapanDao
 * 
 * @author LeXuanHai MstJapanLogic.java Oct 25, 2017
 */
public interface MstJapanLogic {
	/**
	 * Hàm lấy tất cả dữ liệu trong table mst_japan
	 * 
	 * @return list danh sách MstJapan
	 * @throws Exception 
	 */
	public List<MstJapan> getAllMstJapan() throws Exception;

	/**
	 * Kiểm tra codelevel có tồn tại không
	 * 
	 * @param codeLevel
	 *            truyền vào code level cần kiểm tra
	 * @return true nếu tồn tại false nếu ngược lại
	 * @throws SQLException
	 */
	public boolean checkExistLevelJapan(String codeLevel);
}
