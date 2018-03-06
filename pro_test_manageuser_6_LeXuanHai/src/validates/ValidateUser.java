/**
 * Copyright(C) 2010 Luvina Software Company
 * Oct 20, 2017 ValidateUser.java
 */
package validates;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.impl.MstGroupLogicImpl;
import Logic.impl.MstJapanLogicImpl;
import Logic.impl.TblUserLogicImpl;
import entities.UserInfo;
import properties.MessageErrorProperties;
import utils.Common;

/**
 * @author LA-PM Class check các lỗi nhập liệu
 */
public class ValidateUser {
	public static TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
	public static MstGroupLogicImpl groupLogicImpl = new MstGroupLogicImpl();
	public static MstJapanLogicImpl japanLogicImpl = new MstJapanLogicImpl();

	/**
	 * Hàm kiểm tra đăng nhập
	 * 
	 * @param loginName
	 *            Tên đăng nhập
	 * @param password
	 *            Password đăng nhập
	 * @return return lỗi. Nếu đăng nhập thành công thì lỗi messArray có size = 0.
	 *         Còn nếu có lỗi messArray có size > 0.
	 * @throws Exception
	 */
	
	public static ArrayList<String> validateLogin(String loginName, String password) throws Exception {
		ArrayList<String> messArray = new ArrayList<>();
		if (password.isEmpty() && loginName.isEmpty()) { // nếu chưa nhập mật khẩu và pass
			messArray.add(MessageErrorProperties.getString("ER001_LOGINNAME_PASS"));
		} else if (loginName.isEmpty()) { // nếu chưa nhập tên đăng nhập
			messArray.add(MessageErrorProperties.getString("ER001_LOGINNAME"));
		} else if (password.isEmpty()) { // nếu chưa nhập mật khẩu
			messArray.add(MessageErrorProperties.getString("ER001_PASS"));
		} else {
			// kiểm tra xem có đúng là admin không
			boolean check = new TblUserLogicImpl().checkLogin(loginName, password);
			if (check == false) { // Nếu không phải là admin
				messArray.add(MessageErrorProperties.getString("ER016"));
			}
		}
		return messArray;

	}

	/**
	 * Hàm check các validate của loginName
	 * 
	 * @param userId
	 *            mã user
	 * @param loginName
	 *            truyền vào loginName cần check
	 * @return trả về string thông báo lỗi
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static String validateLoginName(Integer userId, String loginName)
			throws SQLException, UnsupportedEncodingException {

		String error = "";

		if (loginName.isEmpty()) {
			error = MessageErrorProperties.getString("ER001_LOGINNAME");
		} else if (!utils.Common.checkFormatLoginName(loginName)) {
			error = MessageErrorProperties.getString("ER019_LOGINNAME");
		} else if (!utils.Common.checkLength(loginName, 15, 4)) {
			error = MessageErrorProperties.getString("ER007_LOGINNAME");
		} else if (!tblUserLogicImpl.checkExistedLoginName(userId, loginName)) {
			error = MessageErrorProperties.getString("ER003_LOGINNAME");
		}
		return error;
	}

	/**
	 * Check validate cho email
	 * 
	 * @param userId
	 *            mã user
	 * @param email
	 *            email cần check
	 * @return câu thông báo lỗi nếu có
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static String validateEmail(Integer userId, String email, String key)
			throws SQLException, UnsupportedEncodingException {
		String error = "";
		if (email.isEmpty()) {// email rỗng
			error = MessageErrorProperties.getString("ER001_EMAIL");
		} else if (!utils.Common.checkFormatEmail(email)) { // format email
			error = MessageErrorProperties.getString("ER005_EMAIL");
		} else if (utils.Common.checkMaxLength(email)) { // maxLength
			error = MessageErrorProperties.getString("ER006_EMAIL");
			// nếu chức năng add và email chưa tồn tại (chức năng edit ko cần check tồn tại)
		} else if ("".equals(key) && !tblUserLogicImpl.checkExistedEmail(userId, email)) {
			error = MessageErrorProperties.getString("ER003_EMAIL");
		}
		return error;
	}

	/**
	 * Hàm check validate cho fullname
	 * 
	 * @param fullName
	 *            fullName cần check
	 * @return trả về câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateFullName(String fullName) throws UnsupportedEncodingException {
		String error = "";
		if (fullName.isEmpty()) {
			error = MessageErrorProperties.getString("ER001_FULLNAME");
		} else if (utils.Common.checkMaxLength(fullName)) {
			error = MessageErrorProperties.getString("ER006_FULLNAME");
		}
		return error;
	}

	/**
	 * Hàm check validate cho fullnameKana
	 * 
	 * @param fullName
	 *            fullNameKana cần check
	 * @return trả về câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateKanaName(String kanaName) throws UnsupportedEncodingException {
		String error = "";
		if (!utils.Common.checkKanaName(kanaName)) {
			error = MessageErrorProperties.getString("ER009");
		} else if (utils.Common.checkMaxLength(kanaName)) {
			error = MessageErrorProperties.getString("ER006_FULLNAME_KANA");
		}
		return error;
	}

	/**
	 * Hàm check validate cho ngày sinh
	 * 
	 * @param arrBirthDay
	 *            mảng ngày thàng năm sinh
	 * @return trả về câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateBirthDay(ArrayList<Integer> arrBirthDay) throws UnsupportedEncodingException {
		String error = "";
		if (!utils.Common.checkFormatDate(arrBirthDay)) {
			error = MessageErrorProperties.getString("ER011_BIRTHDAY");
		}
		return error;
	}

	/**
	 * Check validate cho password
	 * 
	 * @param password
	 *            pass cần check
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validatePassword(String password) throws UnsupportedEncodingException {
		String error = "";
		if (password.isEmpty()) {
			error = MessageErrorProperties.getString("ER001_PASS");
		} else if (utils.Common.checkBytePassword(password)) {
			error = MessageErrorProperties.getString("ER008_PASS");
		} else if (!utils.Common.checkLength(password, 15, 4)) {
			error = MessageErrorProperties.getString("ER007_PASS");
		}
		return error;
	}

	/**
	 * Check validate cho password
	 * 
	 * @param password
	 *            pass cần check
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateConfirmPassword(String password, String passwordConfirm)
			throws UnsupportedEncodingException {
		String error = "";
		if (!utils.Common.checkConfirmPassword(password, passwordConfirm)) {
			error = MessageErrorProperties.getString("ER017");
		}
		return error;
	}

	/**
	 * Hàm check vaildate cho group
	 * 
	 * @param groupId
	 *            mã group
	 * @return câu thông báo lỗi
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static String validateGroup(int groupId) throws SQLException, UnsupportedEncodingException {
		String error = "";
		if (groupId == 0) {
			error = MessageErrorProperties.getString("ER002_GROUP");
		} else if (groupLogicImpl.checkExistedGroup(groupId)) {
			error = MessageErrorProperties.getString("ER004_GROUP");
		}
		return error;
	}

	/**
	 * Hàm check validate cho ngày cấp chứng chỉ
	 * 
	 * @param arrStartDate
	 *            mảng ngày tháng năm cấp chứng chỉ
	 * @return trả về câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateStartDate(ArrayList<Integer> arrStartDate) throws UnsupportedEncodingException {
		String error = "";
		if (!utils.Common.checkFormatDate(arrStartDate)) {
			error = MessageErrorProperties.getString("ER011_START_DATE");
		}
		return error;
	}

	/**
	 * Check validate cho điện thoại
	 * 
	 * @param tel
	 *            chuỗi số điện thoại cần check
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateTel(String tel) throws UnsupportedEncodingException {
		String error = "";
		if (tel.isEmpty()) {
			error = MessageErrorProperties.getString("ER001_TEL");
		} else if (!utils.Common.checkFormatTel(tel)) {
			error = MessageErrorProperties.getString("ER005_TEL");
		} else if (!utils.Common.checkLength(tel, 14, 5)) {
			error = MessageErrorProperties.getString("ER007_TEL");
		}
		return error;
	}

	/**
	 * Check validate cho CodeLevel
	 * 
	 * @param codeLevel
	 * @return câu thông báo lỗi nếu có lỗi
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static String validateCodeLevel(String codeLevel) throws SQLException, UnsupportedEncodingException {
		String error = "";
		if (!japanLogicImpl.checkExistLevelJapan(codeLevel)) {
			error = MessageErrorProperties.getString("ER004_LEVEL");
		}
		return error;
	}

	/**
	 * Check validate cho ngày hết hạn
	 * 
	 * @param arrEndDate
	 *            mảng ngày tháng năm hết hạn
	 * @param arrStartDate
	 *            màng ngày tháng năm cấp chứng chỉ
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateEndDate(ArrayList<Integer> arrEndDate, ArrayList<Integer> arrStartDate)
			throws UnsupportedEncodingException {
		String error = "";
		if (!utils.Common.checkFormatDate(arrStartDate)) {
			error = MessageErrorProperties.getString("ER011_END_DATE");
		} else if (!utils.Common.checkEndDate(arrEndDate, arrStartDate)) {
			error = MessageErrorProperties.getString("ER012");
		}
		return error;
	}

	/**
	 * Check validate cho total
	 * 
	 * @param total
	 *            được truyền vào từ giao diện
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static String validateTotal(String total) throws UnsupportedEncodingException {
		String error = "";
		if (total.isEmpty()) {
			error = MessageErrorProperties.getString("ER001_TOTAL");
		} else if (utils.Common.checkHalfSize(total) == false) {
			error = MessageErrorProperties.getString("ER018");
		}
		return error;
	}

	/**
	 * Check validate cho Password và ConfigPassword
	 * 
	 * @param password
	 *            password được nhập từ bàn phím
	 * @param passwordConfig
	 *            password xác nhận được nhập từ bàn phím
	 * @return câu thông báo lỗi nếu có
	 * @throws UnsupportedEncodingException
	 */
	public static List<String> validatePassUser(String password, String passwordConfig)
			throws UnsupportedEncodingException {
		List<String> listError = new ArrayList<>();
		String error = "";
		error = validatePassword(password);
		addErro(listError, error);
		error = validateConfirmPassword(password, passwordConfig);
		addErro(listError, error);
		return listError;
	}

	/**
	 * Hàm add lỗi
	 * 
	 * @param listError
	 *            list String chứa lỗi
	 * @param error
	 *            lỗi cần add
	 */
	public static void addErro(List<String> listError, String error) {
		if (!error.isEmpty()) {
			listError.add(error);
		}
	}

	/**
	 * Check validate cho cả userInfo
	 * 
	 * @param userInfo
	 *            đối tượng userInfo cần check
	 * @return trả về list các thông báo lỗi nếu có
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static List<String> validateUserInfo(UserInfo userInfo, String key)
			throws SQLException, UnsupportedEncodingException {
		List<String> listError = new ArrayList<>();
		String error = "";
		int userId = userInfo.getId();
		String email = userInfo.getEmail();
		String password = userInfo.getPassWord();
		String codeLevel = userInfo.getCodeLevel();
		if (userId == 0) {
			error = validateLoginName(userId, userInfo.getLoginName());
			addErro(listError, error);
		}
		error = validateGroup(Common.parseInt(userInfo.getGroup()));
		addErro(listError, error);
		error = validateFullName(userInfo.getFullName());
		addErro(listError, error);
		error = validateKanaName(userInfo.getKanaName());
		addErro(listError, error);
		error = validateBirthDay(userInfo.getArrBirthDay());
		addErro(listError, error);
		if (userId == 0) {
			error = validateEmail(userId, email, key);
			addErro(listError, error);
		}
		error = validateTel(userInfo.getTel());
		addErro(listError, error);
		if (("".equals(key))) {
			error = validatePassword(password);
			addErro(listError, error);
			error = validateConfirmPassword(password, userInfo.getConfirmPassWord());
			addErro(listError, error);
		}
		if (!"0".equals(codeLevel)) {
			error = validateCodeLevel(codeLevel);
			addErro(listError, error);
			error = validateStartDate(userInfo.getArrStartDate());
			addErro(listError, error);
			error = validateEndDate(userInfo.getArrEndDate(), userInfo.getArrStartDate());
			addErro(listError, error);
			error = validateTotal(userInfo.getTotal());
			addErro(listError, error);
		}
		return listError;
	}
}
