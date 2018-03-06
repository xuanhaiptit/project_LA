/**
 * Copyright(C) 2017  Luvina
 * 
 * TblUserDao.java, Oct 25, 2017, HaiLX
 */
package Dao;

import java.sql.SQLException;
import java.util.List;

import entities.TblUser;
import entities.UserInfo;

/**
 * Interface chứa các hàm lấy dữ liệu liên quan đến User trong DB
 * 
 * @author LeXuanHai TblUserDao.java Oct 24, 2017
 */
public interface TblUserDao {
	/**
	 * Lấy dữ liệu từ Database theo trường nhập vào
	 * 
	 * @param login_name
	 *            dữ liệu login_name được truyền vào
	 * @return list tblUser gồm login_name, password, role trong bảng User
	 * @throws Exception 
	 */
	public TblUser getUserByLoginId(String login_name) throws Exception;

	/**
	 * lấy trường Salt từ cơ sở dữ liệu
	 * 
	 * @param login_name
	 *            dữ liệu login_name được truyền vào
	 * @return Salt dữ liệu Salt được lấy ra từ bảng User
	 * @throws Exception 
	 */
	public String getSalt(String login_name) throws Exception;

	/**
	 * Hàm lấy số lượng User trong DB theo 2 biến là groupID và fullName
	 * 
	 * @param groupID:
	 *            được truyền vào để tìm kiếm
	 * @param fullName:
	 *            được truyền vào để tìm kiếm
	 * @return số User bản ghi thỏa mãn điều kiện tìm kiếm
	 * @throws Exception 
	 */
	public int getTotalUsers(int groupID, String fullName) throws Exception;

	/**
	 * Lấy user theo email
	 * 
	 * @param userId
	 *            truyền vào mã user
	 * @param email
	 *            truyền vào email của user
	 * @return đối tượng user có email giống email truyền vào
	 * @throws SQLException
	 */
	TblUser getUserByEmail(Integer userId, String email) throws SQLException;

	/**
	 * Lấy user theo loginName
	 * 
	 * @param userId
	 *            truyền vào mã user
	 * @param email
	 *            truyền vào loginUser
	 * @return đối tượng user có loginName và mã giống data truyền vào
	 * @throws SQLException
	 */
	TblUser getUserByLoginName(Integer userId, String loginName) throws SQLException;

	/**
	 * Hàm lấy thông tin User theo các điều kiện để cho dữ liệu vào bảng tại màn
	 * hình ADM002
	 * 
	 * @param offset:
	 *            vị trí data cần lấy nào
	 * @param limit:
	 *            số lượng lấy
	 * @param groupId:
	 *            mã nhóm tìm kiếm
	 * @param fullName:
	 *            Tên tìm kiếm
	 * @param sortType:
	 *            Nhận biết xem cột nào được ưu tiên sắp xếp(full_name or end_date
	 *            or code_level)
	 * @param sortByFullName:
	 *            Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel:
	 *            Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or DESC)
	 * @param sortByEndDate:
	 *            Giá trị sắp xếp của cột Ngày kết hạn(ASC or DESC)
	 * @return list ListUserInfo cần lấy
	 * @throws Exception 
	 */
	public List<UserInfo> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception;

	/**
	 * Thực hiện thêm mới 1 user vào DB
	 * 
	 * @param tblUser
	 *            TblUser tblUser Đối tượng chứa thông tin của user
	 * @return idUser có giá trị khác 0 nếu insert thành công, và 0 nếu insert thất
	 *         bại
	 * @throws SQLException
	 * 
	 */
	public int insertUser(TblUser tblUser) throws SQLException;

	/**
	 * Thực hiện Update 1 user vào DB
	 * 
	 * @param tblUser
	 *            TblUser tblUser Đối tượng chứa thông tin của user
	 * @return idUser có giá trị khác 0 nếu update thành công, và 0 nếu update thất
	 *         bại
	 * @throws SQLException
	 * 
	 */
	public boolean updatetUser(TblUser tblUser) throws SQLException;

	/**
	 * Hàm kiểm tra xem trong DB có tồn tại IdUser hay không
	 * 
	 * @param id
	 *            id được truyền vào kiểm tra
	 * @return true nếu có tồn tại, false nếu không tồn tại
	 * @throws Exception 
	 */
	public boolean checkExistIdUser(int id) throws Exception;

	/**
	 * Hàm lấy dữ liệu InfoUser theo ID_user
	 * 
	 * @param id
	 *            Dữ liệu user_id được truyền vào
	 * @return dữ liệu UserInfo
	 * @throws SQLException
	 */
	public UserInfo getUserInfo(int id) throws SQLException;

	/**
	 * @param id
	 *            ID của user cần thay password
	 * @param salt
	 *            mã salt được thay mới cùng password
	 * @param password
	 *            password được truyền vào
	 * @return true nếu update password thành công false nếu update password thất
	 *         bại
	 * @throws SQLException
	 */
	public boolean updatetPasswordUser(int id, String salt, String password) throws SQLException;

	/**
	 * Hàm thực hiện delete User theo ID
	 * 
	 * @param id
	 *            id user được truyền vào
	 * @return true nếu delete thành công false nếu delete thất bại
	 * @throws SQLException
	 */
	public boolean deleteUser(int id) throws SQLException;

	/**
	 * Hàm lấy listUserInfo để cho ra file
	 * 
	 * @param fullName
	 *            fullName được truyền từ chỗ tìm kiếm
	 * @param groupId
	 *            groupId được truyền từ chỗ tìm kiếm
	 * @return listUserInfo
	 * @throws Exception 
	 */
	public List<UserInfo> getUserInfoExport(String fullName, int groupId) throws Exception;

}
