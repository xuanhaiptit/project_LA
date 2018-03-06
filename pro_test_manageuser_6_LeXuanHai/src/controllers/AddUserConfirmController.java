/**
 * Controller xử lí khi người dùng chọn nút Ok tại ADM003 và dữ liệu được hiện tại ADM004
 * 
 * Copyright(C) 2017  Luvina
 * 
 * AddUserConfirmController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Logic.impl.TblUserLogicImpl;
import entities.UserInfo;
import utils.Common;
import utils.Constant;

/**
 * Servlet implementation class AddUserConfirmController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.ADD_USER_CONFIRM })
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUserConfirmController() {
		super();
	}

	/**
	 * Hàm được xử lí khi người dùng click button OK tại ADM003 và đổ dữ liệu lên
	 * ADM004
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String key = request.getParameter("key"); // key được truyền để lấy session theo key
			String tempClick = request.getParameter("tempClick"); // tempClick để xem thuộc Edit hay Add
			UserInfo userInfo = (UserInfo) session.getAttribute(key); // lấy UserInfo từ session theo key
			if (userInfo != null) { // nếu tồn tại UserInfo
				// gán Date cho Birthday, StartDate, EndDate từ ArrDate có sẵn trong UserInfo
				userInfo.setBirthday(Date.valueOf(Common.changeDateString(userInfo.getArrBirthDay())));
				if (userInfo.getCodeLevel() != null) { // nếu tồn tại trình độ TN
					userInfo.setStartDate(Date.valueOf(Common.changeDateString(userInfo.getArrStartDate())));
					userInfo.setEndDate(Date.valueOf(Common.changeDateString(userInfo.getArrEndDate())));
				}
				request.setAttribute("display", "none");
				if ("0".equals(userInfo.getCodeLevel())) { // Nếu không có trình độ tiếng Nhật thì ẩn chỗ hiển thị trình
															// độ tiếng Nhật đi
					request.setAttribute("display", "none");
				} else {
					request.setAttribute("display", "block");
				}
				session.setAttribute("key", key);
				session.setAttribute("tempClick", tempClick);
				request.setAttribute("userInfo", userInfo);
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM004);
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TblUserLogicImpl logicImpl = new TblUserLogicImpl();
			HttpSession session = request.getSession();
			// tempClick là biến nhớ được truyền trên session
			// Nếu từ EDIT thì tempClick = "tempClick", còn nếu ADD thì tempClick sẽ là ""
			String tempClick = (String) session.getAttribute("tempClick");
			String key = (String) session.getAttribute("key"); // key được truyền theo session, theo từng hành động Edit
																// hoặc add với key là thời gian hiện tại
			UserInfo userInfo = (UserInfo) session.getAttribute(key);
			if (userInfo != null) { // check xem user có tồn tại không
				if ("tempEdit".equals(tempClick)) { // thực hiện khi ấn OK tại ADM004 với chức năng Edit
					if ((logicImpl.updateUser(userInfo))) { // tại trường hợp uodate dữ liệu thành
															// công
						response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.INSERT_SUCCESS);
					} else { // update dữ liệu thất bại
						response.sendRedirect(Constant.SYSTEM_ERRO + "?type=" + Constant.SYSTEM_ERROR);
					}
				} else { // thực hiện khi ấn OK tại ADM004 với chức năng Add
					if ((logicImpl.createUser(userInfo))) { // tại trường hợp add dữ liệu thành công
						response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.EDIT_SUCCESS);
					} else { // add dữ liệu thất bại
						response.sendRedirect(Constant.SYSTEM_ERRO + "?type=" + Constant.SYSTEM_ERROR);
					}
				}
			} else { // nếu ko tồn tại user thì ra thông báo lỗi
				response.sendRedirect(
						request.getContextPath() + Constant.SYSTEM_ERRO + "?type=" + Constant.MSG_NO_RECORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
