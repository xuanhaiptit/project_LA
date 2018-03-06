/**
 * Controller xử lí khi người dùng click vào button add tại ADM003
 * Copyright(C) 2017  Luvina
 * 
 * AddUserInputController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Logic.impl.MstGroupLogicImpl;
import Logic.impl.MstJapanLogicImpl;
import Logic.impl.TblUserLogicImpl;
import entities.MstGroup;
import entities.MstJapan;
import entities.UserInfo;
import utils.Common;
import utils.Constant;
import validates.ValidateUser;

/**
 * Servlet implementation class AddUserInputUser
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.ADD_USER_INPUT })
public class AddUserInputController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public AddUserInputController() {
	}

	/**
	 * doGet của sevlet xử lý lấy những dữ liệu cần thiết tại ADM002 để cho sang
	 * ADM003 nếu chọn add, và từ ADM005 về ADM003 nếu chọn EDIT.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		try {
			if ("ADM003".equals(type)) { // khi người dùng từ ADM002 sang ADM003 chọn button ADD
				setDataLogic(request, response); // set dữ liệu (list group, time) lên view
				UserInfo userInfo = setDefaultValue(request, response);
				request.setAttribute("display", "none"); // none để ấn trình độ tiếng Nhật
				request.setAttribute("userInfo", userInfo);

			} else if ("edit".equals(type)) { // xét trường hợp nếu người chọn ấn từ button ADM005 chọn chức năng Edit
				int id = Integer.parseInt(request.getParameter("key"));
				UserInfo userInfo = setDefaultValue(request, response);
				userInfo.setId(id);
				setDataLogic(request, response);
				request.setAttribute("display", "none");
				request.setAttribute("userInfo", userInfo);
				request.setAttribute("edit", "edit");
			} else if ("back".equals(type)) {
				HttpSession session = request.getSession();
				// String key: key được truyền theo session, theo từng hành động Edit hoặc add
				// với key là thời gian hiện tại
				String key = (String) session.getAttribute("key");

				UserInfo userInfo = (UserInfo) session.getAttribute(key);
				setDataLogic(request, response);
				request.setAttribute("userInfo", userInfo);
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM003);
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SUCCESS + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String tempClick = ""; // một biến nhớ (nếu đang thực hiện là chức năng Edit, thì tempClick sẽ có giá
									// trị "tempEdit"; nếu thực hiện chức năng Add, thì tempClick sẽ có giá trị
									// "tempAdd".
			String type = request.getParameter("edit");
			UserInfo userInfo = null;
			List<String> listError = null;
			if (type.equals("edit")) { // trường hợp người dùng ấn OK tại chức năng Edit của ADM003
				String userId = request.getParameter("userId"); // khi edit, sẽ có ID user sẵn
				request.setAttribute("edit", "edit"); // gửi request lên để xử lí tại password (ẩn pass)
				tempClick = "tempEdit"; // gán giá trị cho tempClick
				userInfo = setDefaultValue(request, response);
				userInfo.setId(Common.parseInt(userId));
				listError = ValidateUser.validateUserInfo(userInfo, "edit"); // validate với trường hợp edit, sẽ ko
																				// validate password
			} else { // trường hợp người dùng ấn OK tại chức năng Add của ADM003
				request.setAttribute("edit", ""); // gửi request lên để nhận diện là chức năng edit(xử lí pass)
				userInfo = setDefaultValue(request, response);
				tempClick = "tempAdd";
				listError = ValidateUser.validateUserInfo(userInfo, ""); // validate sẽ có validate password
			}
			// xử lý sau khi vào 1 trong 2 trường hợp EDIT hoặc ADD
			if ("0".equals(userInfo.getCodeLevel())) {
				request.setAttribute("display", "none"); // chọn ẩn trình độ tiếng Nhật
			} else {
				request.setAttribute("display", "block"); // hiển thị trình độ tiếng Nhật
			}
			if (listError.size() == 0) { // nếu không có lỗi
				HttpSession session = request.getSession();
				String key = Common.getKey(); // lấy key theo thời gian hiện tại
				session.setAttribute(key, userInfo);
				// nếu người dùng edit thì tồn tại tempEdit, còn nếu chọn add thì tempEdit rỗng
				// Key là biến nhớ theo từng hành động add hoặc edit là 1 session riêng biệt
				response.sendRedirect(
						request.getContextPath() + "/AddUserConfirm.do?tempClick=" + tempClick + "&key=" + key);
			} else { // nếu có lỗi khi nhập liệu
				request.setAttribute("userInfo", userInfo); // request dữ liệu đã nhập lên form
				request.setAttribute("listError", listError); // request câu thông báo lỗi lên form
				setDataLogic(request, response);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM003);
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * Thực hiện set giá trị cho các hạng mục selectbox ở màn hình ADM003.
	 * 
	 * Thực hiện gọi các hàm trong lớp logic:
	 * 
	 * - mstGroupLogic.getAllMstGroup(): các Group
	 * 
	 * mstJapanLogic.getAllMstJapan(): Các trình độ tiếng Nhật
	 * 
	 * Thực hiện gọi các hàm trong lớp Common:
	 * 
	 * - common.getListYear(int, int) : List năm
	 * 
	 * common.getListMonth() : list Tháng
	 * 
	 * - common.getListDay(): List ngày
	 * 
	 * Sau khi lấy được dữ liệu thì thực hiện set các dữ liệu đó lên request.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MstGroup> listGroup = new ArrayList<>();
		List<MstJapan> listJapan = new ArrayList<>();
		List<Integer> listYear = new ArrayList<>();
		List<Integer> listMonth = new ArrayList<>();
		List<Integer> listDay = new ArrayList<>();
		int startYear = Common.getStartYear();
		listGroup = new MstGroupLogicImpl().getAllMstGroup();
		listJapan = new MstJapanLogicImpl().getAllMstJapan();
		listYear = Common.getListYear(startYear, Common.getYearNow());
		listMonth = Common.getListMonth();
		listDay = Common.getListDay();
		// set dữ liệu lên các trường tương ứng
		request.setAttribute("listGroup", listGroup);
		request.setAttribute("listJapan", listJapan);

		request.setAttribute("listYear", listYear);
		request.setAttribute("listMonth", listMonth);
		request.setAttribute("listDay", listDay);
	}

	/**
	 * Set giá trị default cho màn hình ADM003.
	 * 
	 * @param request
	 * @param response
	 * @return UserInfor
	 * 
	 *         userInfor đối tượng chứa thông tin của màn hình ADM003
	 * 
	 * @throws SQLException
	 */
	private UserInfo setDefaultValue(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String codeLevel = "0";
		UserInfo userInfo = new UserInfo(); // UserInfo để truyền lên ADM004
		ArrayList<Integer> arrBirthday = new ArrayList<Integer>();
		ArrayList<Integer> arrStartDate = new ArrayList<Integer>();
		ArrayList<Integer> arrEndDate = new ArrayList<Integer>();

		String type = request.getParameter("type");
		if ("edit".equals(type) || "edit".equals(type)) { // 2 trường hợp back và edit đều có dữ liệu sẵn
			UserInfo userInfoByID = null;// userInfo lấy dữ liệu theo ID
			if ("edit".equals(type)) { // chọn chức năng Edit từ ADM005
				int idUser = Common.parseInt(request.getParameter("key"));
				userInfoByID = new TblUserLogicImpl().getUserInfo(idUser);
				codeLevel = userInfoByID.getCodeLevel();
				arrBirthday = Common.getDate(userInfoByID.getBirthday());
				if (codeLevel != null) { // nếu tồn tại trình độ tiếng Nhật thì sẽ có StartDate và EndDate từ CSDL
					arrStartDate = Common.getDate(userInfoByID.getStartDate());
					arrEndDate = Common.getDate(userInfoByID.getEndDate());
				} else { // không có trình độ tiếng Nhật thì set giá trị mặc định cho StartDate, EndDate
					Date date = new Date();
					arrStartDate = Common.getDate(date);
					arrEndDate.add(arrStartDate.get(0));
					arrEndDate.add(arrStartDate.get(1));
					arrEndDate.add(arrStartDate.get(2));
				}
				// edit thì dữ liệu được giữ nguyên
				userInfo = userInfoByID;
				userInfo.setArrBirthDay(arrBirthday);
				userInfo.setArrStartDate(arrStartDate);
				userInfo.setArrEndDate(arrEndDate);
			} else if ("back".equals(type)) { // chọn nút back từ ADM004
				HttpSession session = request.getSession();
				String key = request.getParameter("key"); // key request đã lưu tại từng hành động
				userInfoByID = (UserInfo) session.getAttribute(key); // lấy userInfo đã được lưu theo key
				// set dữ liệu (do khi back dữ liệu đc giữ nguyên toàn bộ
				userInfo = userInfoByID;
			}
		} else { // 2 trường hợp còn lại
			String loginName = "";
			String fullName = "";
			String kanaName = "";
			String email = "";
			String tel = "";
			String passWord = "";
			String confirmPassWord = "";
			String total = "";
			int groupId = 0;
			if ("ADM003".equals(type)) { // chọn chức năng ADD từ màn hình ADM002
				Date date = new Date();
				arrBirthday = Common.getDate(date); // mặc định giá trị cho time tại Birthday
				arrStartDate = Common.getDate(date); // mặc định giá trị cho time tại StartDate
				arrEndDate = Common.getDate(date); // mặc định giá trị cho time tại EndDate
			} else if ("validateUser".equals(type)) { // chọn nút OK tại ADM003
				// lấy các dữ liệu trên form ADM003 để kiểm tra
				loginName = request.getParameter("loginName");
				groupId = Common.parseInt(request.getParameter("groupId"));
				fullName = request.getParameter("fullName");
				kanaName = request.getParameter("kanaName");
				email = request.getParameter("email");
				tel = request.getParameter("tel");
				passWord = request.getParameter("passWord");
				confirmPassWord = request.getParameter("confirmPassWord");
				codeLevel = request.getParameter("codeLevel");
				total = request.getParameter("total");

				// set các tham số Year,Month,Day vào mảng Integer
				arrBirthday = Common.setArrayDate(request, "yearBirthDay", "monthBirthDay", "dayBirthDay");
				arrStartDate = Common.setArrayDate(request, "yearStartDate", "monthStartDate", "dayStartDate");
				arrEndDate = Common.setArrayDate(request, "yearEndDate", "monthEndDate", "dayEndDate");
			}
			userInfo.setLoginName(loginName);
			userInfo.setGroup(String.valueOf(groupId));
			userInfo.setFullName(fullName);
			userInfo.setKanaName(kanaName);
			userInfo.setEmail(email);
			userInfo.setTel(tel);
			userInfo.setPassWord(passWord);
			userInfo.setConfirmPassWord(confirmPassWord);
			userInfo.setCodeLevel(codeLevel);
			userInfo.setTotal(total);
			userInfo.setArrBirthDay(arrBirthday);
			if (codeLevel != null) {
				userInfo.setArrStartDate(arrStartDate);
				userInfo.setArrEndDate(arrEndDate);
			}
		}
		return userInfo;
	}
}
