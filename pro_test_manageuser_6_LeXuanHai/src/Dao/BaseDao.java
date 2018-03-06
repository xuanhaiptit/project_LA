/**
 * Copyright(C) 2017  Luvina
 * 
 * BaseDao.java, Oct, 31, 2017, HaiLX
 */
package Dao;

import java.sql.Connection;
/**
 * Interface chứa các phương thức cơ bản để thao tác với Database
 * 
 * @author HaiLX
 */
public interface BaseDao {
	/**
	 * Đóng kết nối cơ sở dữ liệu
	 */
	public void closeConnect();

	/**
	 * Kiểm tra kết nối cơ sở dữ liệu
	 * 
	 * @return true nếu kết nối DB thành công
	 *  		false nếu kết nối DB thất bại
	 */
	public boolean checkConnectToDB();

	/**
	 * Kết nối cơ sở dữ liệu
	 * 
	 * @return connecttion
	 */
	public Connection getConnect() ;
}
