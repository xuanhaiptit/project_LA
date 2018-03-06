/**
 * Copyright(C) 2010 Luvina Software Company
 * Oct 20, 2017 Common.java
 */
package utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.TblDetailUserJapan;
import entities.TblUser;
import entities.UserInfo;
import properties.ConfigMysql;

/**
 * Class chứa các phương thức Common cho toàn dự án
 * 
 * @author xuanhai Oct 20, 2017 Common.java
 *
 */
public class Common {
	/**
	 * Hàm lấy key ngẫu nhiên theo thời gian hiện tại
	 * 
	 * @return key cần lấy
	 */
	public static String getKey() {
		String key = "" + System.currentTimeMillis();
		return key;
	}

	/**
	 * Hàm lấy mã role của Admin
	 * 
	 * @return giá trị Role của Admin kiểu INT
	 */
	public static int getRoleAdmin() {
		String roleAdmin = ConfigMysql.getString("roleAdmin");

		return Common.parseInt(roleAdmin);
	}

	/**
	 * Hàm lấy mã role của Member
	 * 
	 * @return giá trị Role của Member kiểu INT
	 */
	public static int getRoleMember() {
		String roleMember = ConfigMysql.getString("roleMember");
		return Common.parseInt(roleMember);
	}

	/**
	 * check chuỗi có null hay rỗng không
	 * 
	 * @param input
	 *            chuỗi cần check
	 * @return true khác null, khác rỗng, false null hoặc rỗng
	 */
	public static boolean checkNullOrEmpty(String input) {
		return input != null && !input.isEmpty();
	}

	/**
	 * Hàm set dữ liệu từ User vào tblUser để update hoặc add hoặc delete dữ liệu
	 * trong DB
	 * 
	 * @param userInfos
	 *            userInfo được lấy từ view
	 * @param tblUser
	 *            tblUser sẽ dùng
	 */
	public static void setTblUser(UserInfo userInfos, TblUser tblUser) {
		tblUser.setGroupId(Integer.valueOf(userInfos.getGroup())); // 2
		tblUser.setLoginId(userInfos.getLoginName()); // 3
		tblUser.setFullName(userInfos.getFullName()); // 5
		tblUser.setFullNameKana(userInfos.getKanaName()); // 6
		tblUser.setEmail(userInfos.getEmail()); // 7
		tblUser.setTel(userInfos.getTel()); // 8
		tblUser.setBirthday(Common.changeDate(userInfos.getArrBirthDay()));
	}

	/**
	 * Hàm set dữ liệu từ User vào tblDetaiJapan để update hoặc add hoặc delete dữ
	 * liệu trong DB
	 * 
	 * @param userInfos
	 *            userInfo được lấy từ view
	 * @param tblUser
	 *            tblUser sẽ dùng
	 */
	public static void setTblDetaiJapan(TblDetailUserJapan tblDetailUserJapan, UserInfo userInfo) {
		tblDetailUserJapan.setCodeLevel(userInfo.getCodeLevel()); // 2
		tblDetailUserJapan.setStartDate(userInfo.getStartDate()); // 3
		tblDetailUserJapan.setEndDate(userInfo.getEndDate()); // 4
		tblDetailUserJapan.setTotal(Integer.valueOf(userInfo.getTotal())); // 5
	}

	/**
	 * Kiểm tra định dạng của loginName [a->z,0-9,_]
	 * 
	 * @param loginName
	 *            chuỗi loginName
	 * @return true nếu đúng định dạng và false nếu sai
	 */
	public static boolean checkFormatLoginName(String loginName) {
		String regex = "[a-z0-9_]+";
		boolean check = loginName.matches(regex);
		return check;
	}

	/**
	 * Kiểm tra chuỗi có lớn hơn 255 kí tự không
	 * 
	 * @param data
	 *            chuỗi truyền vào
	 * @return true lớn hơn 255 kí tự và false nếu không
	 */
	public static boolean checkMaxLength(String data) {
		boolean check = false;
		if (data.length() > 255) {
			check = true;
		}
		return check;
	}

	/**
	 * Hàm chuyển Date trong ArrayList sang dạng Date của SQL
	 * 
	 * @param arrayList
	 *            list chứa dữ liệu
	 * @return Date dạng SQL
	 */
	public static java.sql.Date changeDate(ArrayList<Integer> arrayList) {
		Date date = null;
		String getDate = arrayList.get(0).toString() + "-" + arrayList.get(1).toString() + "-"
				+ arrayList.get(2).toString();
		date = java.sql.Date.valueOf(getDate);
		return (java.sql.Date) date;
	}

	/**
	 * Hàm kiểm tra ngày có hợp lệ
	 * 
	 * @param date
	 *            truyền vào ngày
	 * @return true nếu ngày hợp lệ và false nếu sai
	 */
	public static boolean checkFormatDate(ArrayList<Integer> date) {
		int year = date.get(0);
		int month = date.get(1);
		int day = date.get(2);
		boolean check = true;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day < 1 || day > 31) {
				check = false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				check = false;
			}
			break;
		case 2:
			// nếu là năm nhuận
			if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
				if (day < 1 || day > 29) {// ngày <1 hoặc >29 thì ngày sai
					check = false;
				}
			} else { // nếu ko phải năm nhuận thì tháng 2 chỉ max 28 ngày
				if (day < 1 || day > 28) {
					check = false;
				}
			}
			break;
		default:
			check = false;
		}
		return check;
	}

	/**
	 * Kiểm tra chữ kana
	 * 
	 * @param fullNameKana
	 *            chuỗi tên kanaa
	 * @return true nếu đúng định dạng và false nếu sai
	 */
	public static boolean checkKanaName(String kanaName) {
		String regex = "[ァ-ン]*"; // biểu thức chính quy
		boolean check = kanaName.matches(regex);
		return check;
	}

	/**
	 * Kiểm tra định dạng của email chứa @.
	 * 
	 * @param email
	 *            chuỗi email
	 * @return true nếu đúng định dạng false nếu sai
	 */
	public static boolean checkFormatEmail(String email) {
		String regex = "[a-z0-9-]+@[a-z0-9]+\\.[a-z]+";
		boolean check = email.matches(regex);
		return check;
	}

	/**
	 * Kiểm tra kí tự 1 byte của password
	 * 
	 * @param password
	 *            pass cần kiểm tra
	 * @return true nếu kí tự lớn 1 byte và false ngược lại
	 */
	public static boolean checkBytePassword(String password) {
		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (c < 0 || c > 255) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hàm kiểm tra ngày hết hạn có lớn hơn ngày cấp không
	 * 
	 * @param endDate
	 *            ngày hết hạn
	 * @param startDate
	 *            ngày cấp
	 * @return trả về đúng nếu ngày cấp nhỏ hơn ngày hết hạn và false nếu ngược lại
	 */
	public static boolean checkEndDate(ArrayList<Integer> endDate, ArrayList<Integer> startDate) {
		boolean check = true;
		if (endDate.get(0) < startDate.get(0)) {
			check = false;
		} else if (endDate.get(0).equals(startDate.get(0))) {
			if (endDate.get(1) < startDate.get(1)) {
				check = false;
			} else if (endDate.get(1).equals(startDate.get(1))) {
				if (endDate.get(2) < startDate.get(2) || endDate.get(2).equals(startDate.get(2))) {
					check = false;
				}
			}
		}
		return check;
	}

	/**
	 * Kiểm tra điểm nhập vào có là số halfsize không
	 * 
	 * @param score
	 *            truyền vào số điểm
	 * @return true nếu là số
	 */
	public static boolean checkHalfSize(String score) {
		int length = score.length();
		for (int i = 0; i < length; i++) {
			char c = score.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Kiểm tra mật khẩu nhập vào có đúng không
	 * 
	 * @param password
	 *            chuỗi password
	 * @param confirmPasswrod
	 *            chuỗi password nhập lại
	 * @return true nếu trùng nhau và false nếu khác nhau
	 */
	public static boolean checkConfirmPassword(String password, String confirmPasswrod) {
		boolean check = password.equals(confirmPasswrod);
		return check;
	}

	/**
	 * Kiểm tra định dạng số điện thoại: XXXX-XXXX-XXXX
	 * 
	 * @param tel
	 *            chuỗi truyền vào
	 * @return true nếu đúng định dạng và false nếu sai định dạng
	 */
	public static boolean checkFormatTel(String tel) {
		String regex = "[0-9]+-[0-9]+-[0-9]+";
		boolean check = tel.matches(regex);
		return check;
	}

	/**
	 * kiểm tra độ dài chuỗi có nằm trong khoảng kí tự không
	 * 
	 * @param data
	 *            chuỗi truyền vào
	 * @param max
	 *            dộ dài max
	 * @param min
	 *            độ dài min
	 * @return true nếu độ dài chuỗi nằm trong khoảng min, max false nếu không
	 */
	public static boolean checkLength(String data, int max, int min) {
		int length = data.length();
		if (min <= length && length <= max) {
			return true;
		}
		return false;
	}

	/**
	 * Lấy danh sách các ngày
	 * 
	 * @return List<Integer> list các ngày trong tháng
	 */

	public static List<Integer> getListDay() {
		List<Integer> listDay = new ArrayList<>();
		int day = 1;
		while (day <= 31) {
			listDay.add(day);
			day++;
		}
		return listDay;
	}

	/**
	 * Lấy danh sách các tháng
	 * 
	 * @return List<Integer> list các tháng trong năm
	 */

	public static List<Integer> getListMonth() {
		List<Integer> listMonth = new ArrayList<>();
		int month = 1;
		while (month <= 12) {
			listMonth.add(month);
			month++;
		}
		return listMonth;
	}

	/**
	 * Hàm lấy dữ liệu year, month, day từ request cho vào mảng Integer
	 * 
	 * @param request
	 *            request từ client
	 * @param yearDate
	 *            dữ liệu year trên view
	 * @param monthDate
	 *            dữ liệu month trên view
	 * @param dayDate
	 *            dữ liệu day trên view
	 * @return mảng Array Integer
	 */
	public static ArrayList<Integer> setArrayDate(HttpServletRequest request, String yearDate, String monthDate,
			String dayDate) {
		int year = Common.parseInt(request.getParameter(yearDate));
		int month = Common.parseInt(request.getParameter(monthDate));
		int day = Common.parseInt(request.getParameter(dayDate));
		ArrayList<Integer> array = new ArrayList<>();
		array.add(year);
		array.add(month);
		array.add(day);
		return array;
	}

	/**
	 * Hàm lấy Date từ DB cho vào ArrayList
	 * 
	 * @param userInfo
	 *            dữ liệu user lấy từ DB
	 * @return mảng ArrayList chứa dữ liệu Date
	 */

	public static ArrayList<Integer> getDate(Date date) {

		ArrayList<Integer> arrayList = new ArrayList<>();
		Calendar call = Calendar.getInstance();
		call.setTime(date);
		int year = call.get(Calendar.YEAR);
		int month = call.get(Calendar.MONTH);
		int day = call.get(Calendar.DAY_OF_MONTH);

		arrayList.add(year);
		arrayList.add(month);
		arrayList.add(day);
		return arrayList;
	}

	/**
	 * Lấy danh sách các năm từ năm 1900 -> năm hiện tại + 1
	 * 
	 * @param startYear
	 *            int fromYear Lấy từ năm nào
	 * @param endYear
	 *            int toYear Lấy đến năm nào
	 * @return List<Integer> list các năm
	 */

	public static List<Integer> getListYear(int startYear, int endYear) {
		List<Integer> listYear = new ArrayList<>();
		while (startYear <= endYear) {
			listYear.add(startYear);
			startYear++;
		}
		return listYear;
	}

	/**
	 * Lấy năm hiện tại
	 * 
	 * @return int nowYear
	 */

	public static int getYearNow() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * Hàm chuyển đổi các kí tự đặc biệt
	 * 
	 * @param str
	 *            chuỗi cần chuyển đổi
	 * @return trả về chuỗi đã chuyển đổi
	 */
	public static String replaceWildCard(String str) {
		str = str.replace("\\", "\\\\"); // toán tử like có sử dụng
		str = str.replace("%", "\\%"); // kí tự của toán tử whica (toán tử like có sử dụng )
		str = str.replace("_", "//%");// (toán tử like có sử dụng )
		return str;
	}

	/**
	 * Hàm mã hóa password
	 * 
	 * @param password
	 *            password đc truyền vào
	 * @param salt
	 *            mã salt để mã hóa pass
	 * @return chuỗi đã được mã hóa
	 */

	public static String getEnctypionPass(String password, String salt) {
		String enctypion = "";
		password += salt; // cho pass và chuỗi salt thành 1 để mã hóa
		enctypion = SHA1(password); // thực hiện mã hóa
		return enctypion;
	}

	/**
	 * Hàm mã hóa một String
	 * 
	 * @param str
	 *            chuỗi String
	 * @return chuỗi String đã mã hóa
	 */

	public static String SHA1(String str) {
		String result = "";
		MessageDigest digest; // tạo 1 đối tượng(lớp) chứa thuật toán mã hóa
		try {
			digest = MessageDigest.getInstance("SHA1"); // đối tượng lấy kiểu mã hóa SHA-1
			digest.update(str.getBytes()); // update dữ liệu kiểu byte
			// Hàm khởi tạo số nguyên lớn từ một chuỗi
			BigInteger bigInteger = new BigInteger(1, digest.digest()); // thực hiện mã hóa (khởi tạo từ một chuỗi)
			result = bigInteger.toString(16); // ép kiểu mã hóa Hexa (đồng bộ với chuỗi mã hóa mySQL)
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(SHA1("HAI"));
	}

	/**
	 * Kiểm tra đã login chưa
	 * 
	 * @param session
	 *            biến session
	 * @return null nếu chưa không tồn tại session
	 */
	public static String checkLogin(HttpSession session) {
		String str = "";
		if (session.getAttribute("loginName") != null) {
			str = "true";
		}
		return str;
	}

	/**
	 * Hàm tính toán logic để tạo ra các trang cần hiển thị ở chuỗi paging theo
	 * trang hiện tại
	 * 
	 * @param totalUser
	 *            tổng sô user
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @param currentPage
	 *            trang hiện tại
	 * @return List<Integer> Danh sách các trang cần hiển thị ở chuỗi paging theo
	 *         trang hiện tại
	 * 
	 */

	public static List<Integer> getListPaging(int totalUser, int limit, int currentPage) {
		List<Integer> listPage = new ArrayList<>();
		int totalPage = 0;
		int limitPage = getLimitPage(); // số đánh số trang có thể hiển thị trên view (1|2|3)
		int getCurrentRange = (currentPage - 1) / limitPage; // số thứ tự dãy hiện tại( 1|2|3: STT 0; 4|5|6: STT 1)
		int pagingFirst = getCurrentRange * limitPage + 1; // số đánh trang đầu hiện sẽ hiện (1, 4, 7)
		if (totalUser % limit == 0) {
			totalPage = totalUser / limit; // tìm số trang, trường hợp chẵn
		} else {
			totalPage = totalUser / limit + 1; // tìm số trang, trường hợp lẻ
		}
		if (pagingFirst + limitPage > totalPage) { // nếu trường hơp số limitPage lớn hơn số trang có thể hiện
			limitPage = totalPage - pagingFirst; // xét lại limitPage
		} else {
			limitPage = limitPage - 1;
		}
		for (int i = pagingFirst; i <= pagingFirst + limitPage; i++) {
			listPage.add(i);
		}
		return listPage;
	}

	/**
	 * Xử lý logic để tính ra vị trí cần lấy dựa và currentPage và limit
	 * 
	 * @param currentPage
	 *            Vị trí Trang hiện tại
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @return vị trí cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		int offset = (currentPage - 1) * limit;
		return offset;
	}

	/**
	 * Lấy header cho hàm export User ra file csv
	 * 
	 * @param key
	 *            key được truyền vào để phân biệt chuỗi header cần lấy
	 * 
	 * @return chuỗi String header
	 */
	public static String getHeaderUser(String key) {
		StringBuffer str = new StringBuffer();
		if ("ExportUser".equals(key)) {
			str.append(ConfigMysql.getString("header_getUser"));
			str.append("\n");
		}
		return str.toString();
	}

	/**
	 * Lấy số lượng hiển thị bản ghi trên 1 trang từ file config.properties
	 * 
	 * @return int số lượng recorscần lấy
	 */

	public static int getLimit() {
		// lấy số lượng bản ghi trên 1 trang tại file proerties
		int limit = Common.parseInt(ConfigMysql.getString("limit"));
		return limit;
	}

	/**
	 * Lấy năm bắt đầu từ file config.properties
	 * 
	 * @return
	 */

	public static int getStartYear() {
		int startYear = Common.parseInt(ConfigMysql.getString("startYear"));
		return startYear;
	}

	/**
	 * Lấy số lượng hiển thị LimitPage (số trang có thể click) trên 1 trang từ file
	 * config.properties
	 * 
	 * @return int số lượng recorscần lấy
	 */

	public static int getLimitPage() {
		// lấy số trang có thể click tại propeties
		int limitPage = Common.parseInt(ConfigMysql.getString("limitPage"));
		return limitPage;
	}

	/**
	 * Tính tổng số trang
	 * 
	 * @param totalUser
	 *            tổng số User
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @return tổng số trang
	 */

	public static int getTotalPage(int totalUser, int limit) {
		int totalPage = 0;
		if (totalUser % limit == 0) {
			totalPage = totalUser / limit; // tìm số trang, trường hợp chẵn
		} else {
			totalPage = totalUser / limit + 1; // tìm số trang, trường hợp lẻ
		}
		return totalPage;
	}

	/**
	 * Hàm ép chuỗi sang dạng date của sql: YYYY-MM-DD
	 * 
	 * @param date
	 * @return chuỗi string theo định dạng YYYY-MM-DD
	 */
	public static String changeDateString(ArrayList<Integer> date) {
		String str = date.get(0) + "-" + date.get(1) + "-" + date.get(2);
		return str;
	}

	/**
	 * Hàm chuyển dữ liệu từ UserInfo sang TblDetailUserJapan với các thuộc tính
	 * tương ứng
	 * 
	 * @param tblDetailUserJapan
	 *            trình độ tiếng nhật cần lấy thông tin
	 * @param userInfo
	 *            thông tin user
	 * @return tblDetailUserJapan trình độ tiếng Nhật chứa các thông tin
	 */
	public static TblDetailUserJapan setDetailUserJapan(TblDetailUserJapan tblDetailUserJapan, UserInfo userInfo) {
		tblDetailUserJapan.setIdUser(userInfo.getId()); // 1
		tblDetailUserJapan.setCodeLevel(userInfo.getCodeLevel()); // 2
		tblDetailUserJapan.setStartDate(userInfo.getStartDate()); // 3
		tblDetailUserJapan.setEndDate(userInfo.getEndDate()); // 4
		tblDetailUserJapan.setTotal(Integer.valueOf(userInfo.getTotal())); // 5

		return tblDetailUserJapan;
	}

	/**
	 * Hàm parseInt từ String sang Integer
	 * 
	 * @param str
	 *            chuỗi string cần parserInt
	 * @return Trả về số nếu chuỗi là số trả về -1 nếu chuỗi ko phải là số
	 */

	public static int parseInt(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
		} catch (Exception e) {
			temp = 0;
		}
		return temp;
	}

	/**
	 * Hàm lấy dữ liệu từ object chuyển thành String
	 * 
	 * @param data
	 *            dữ liệu object cần chuyển
	 * @return chuỗi string dữ liệu
	 */
	public static String getDataExport(List<?> data, String key) {
		StringBuilder builder = new StringBuilder();
		for (Object object : data) {
			builder.append(Common.convertToStringObject(object, key) + "\n");
		}
		return builder.toString();
	}

	/**
	 * Hàm lấy câu thông báo lỗi từ file
	 * 
	 * @param key
	 *            key thông báo cần tìm
	 * @return câu thông báo
	 */
	public static String getMessageJaProperties(String key) {
		String message = ConfigMysql.getString(key);
		return message;
	}

	/**
	 * Hàm xử lí chuyển dữ liệu Object thành toString
	 * 
	 * @param object
	 *            object dữ liệu truyền vào
	 * @param type
	 *            key để xem nó thuộc object nào
	 * @return chuỗi String cần convert
	 */
	public static String convertToStringObject(Object object, String type) {
		StringBuilder str = new StringBuilder();
		if ("ExportUser".equals(type)) { // nếu dữ liệu object là UserInfo
			UserInfo info = new UserInfo();
			info = (UserInfo) object;
			str.append(info.getId() + ", " + info.getLoginName() + "," + info.getFullName() + "," + info.getBirthday());
			str.append(
					"," + info.getGroup() + "," + info.getEmail() + ", " + info.getTel() + "," + info.getCodeLevel());
			str.append("," + info.getEndDate() + ", " + info.getTotal());
		}
		return str.toString();
	}

	/**
	 * Gửi dữ liệu vào File csv
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param listData
	 *            list dữ liệu cần cho vào file
	 * @param type
	 *            kiểu dữ liệu sẽ lấy
	 * @param fileName
	 *            tên file muốn đặt tên
	 * @throws IOException
	 * 
	 */
	public static void writerDataInFile(HttpServletResponse response, List<?> listData, String type, String fileName)
			throws IOException {
		String str = "";
		if (listData.size() == 0) { // trường hợp không có bản ghi dữ liệu nào
			str = Common.getMessageJaProperties(Constant.MSG005);
		} else { // trường hợp tồn tại bản ghi dữ liệu
			str = getDataExport(listData, type);
		}
		response.setContentType("text/csv");
		String reportName = fileName + System.currentTimeMillis() + ".csv";
		response.setHeader("Content-disposition", "attachment; " + "filename=" + reportName);
		response.getWriter().write(str);
	}
}
