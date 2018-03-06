/**
 * Copyright(C) 2017  Luvina
 * 
 * TblUserLogic.java, Oct, 31, 2017, HaiLX
 */
package Logic;

import java.sql.SQLException;
import java.util.List;

import entities.UserInfo;

/**
 * Class interface chứa các hàm logic tới User
 * 
 * @author LeXuanHai TblUserLogic.java Oct 24, 2017
 */
public interface TblUserLogic {

	/**
	 * Kiểm tra email đã tồn tại trong tbl_user chưa
	 * 
	 * @param userId
	 *            truyền vào mã user
	 * @param email
	 *            truyền vào email cần kiểm tra
	 * @return true nếu email tồn tại và false nếu không
	 * @throws SQLException
	 */
	public boolean checkExistedEmail(Integer userId, String email) throws SQLException;

	/**
	 * Kiểm tra loginName đã tồn tại trong tbl_user hay chưa
	 * 
	 * @param userId
	 *            truyền vào mã user
	 * @param loginName
	 *            truyền vào loginName cần kiểm tra
	 * @return true nếu loginName tồn tại false nếu không
	 * @throws SQLException
	 */
	public boolean checkExistedLoginName(Integer userId, String loginName) throws SQLException;

	/**
	 * Kiểm tra xem tên đăng nhập và mật khẩu đúng hay chưa?
	 * 
	 * @param userName
	 *            dữ liệu được lấy từ màn hình đăng nhập
	 * @param passWord
	 *            dữ liệu được lấy từ màn hình đăng nhập
	 * @return true nếu dữ liệu nhập vào trùng khớp false nếu dữ liệu nhập vào không
	 *         trùng khớp
	 * @throws Exception 
	 */
	public boolean checkLogin(String userName, String passWord) throws Exception;

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
	 * Khởi tạo giá trị cho các trường insert User cho 2 bảng TblUser và
	 * TblDetailUserJapan
	 * 
	 * @param listUserInfo
	 *            List dữ liệu được lấy từ màn hình ADM004
	 * @return true nếu insert thành công false nếu insert thất bại
	 * @throws SQLException
	 */
	public boolean createUser(UserInfo listUserInfo) throws SQLException;

	/**
	 * Khởi tạo giá trị cho các trường update User cho 2 bảng TblUser và
	 * TblDetailUserJapan
	 * 
	 * @param listUserInfo
	 *            List dữ liệu được lấy từ màn hình ADM004
	 * @return true nếu update thành công false nếu update thất bại
	 * @throws SQLException
	 */
	public boolean updateUser(UserInfo listUserInfo) throws SQLException;

	/**
	 * Hàm kiểm tra xem trong DB có tồn tại IdUser hay không
	 * 
	 * @param id
	 *            id được truyền vào kiểm tra
	 * @return true nếu có tồn tại, false nếu không tồn tại
	 */
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
	 * @param password
	 *            password được truyền vào
	 * @return true nếu update password thành công false nếu update password thất
	 *         bại
	 * @throws SQLException
	 */
	public boolean updatetPasswordUser(int id, String password) throws SQLException;

	/**
	 * Hàm thực hiện delete UserInfo theo ID
	 * 
	 * @param id
	 *            id user được truyền vào
	 * @return true nếu delete thành công false nếu delete thất bại
	 * @throws SQLException
	 */
	public boolean deleteUserInfo(int id) throws SQLException;

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
