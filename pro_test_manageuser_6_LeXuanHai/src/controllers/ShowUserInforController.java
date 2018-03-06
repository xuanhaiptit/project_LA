/**
 * 
 * Controller để xử lý khi người dùng ấn vào ID tại ADM002
 * Copyright(C) 2017  Luvina
 * 
 * ShowUserInforController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.impl.TblUserLogicImpl;
import entities.UserInfo;
import utils.Common;
import utils.Constant;

/**
 * Servlet implementation class ShowUserInforController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.SHOW_USER_INFOR })
public class ShowUserInforController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowUserInforController() {
		super();
	}

	/**
	 * Sevlet được thực hiện khi người dùng click vào ID tại màn hình ADM002 Khi đó
	 * sẽ thực hiện truyền ID đến đây, tại đây lấy được ID đó và xử lý, Và sẽ show
	 * dữ liệu lên ADM005 nếu dữ liệu tồn tại
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TblUserLogicImpl logicImpl = new TblUserLogicImpl();
		UserInfo userInfo = new UserInfo();
		String id = request.getParameter("userID");
		try {
			// IdUser được lấy từ khi click vào ID ở ADM002
			if (id != null && !logicImpl.checkExistIdUser(Common.parseInt(id))) { // Nếu tồn tại user, cho dữ liệu lên
																					// ADM005
				userInfo = logicImpl.getUserInfo(Common.parseInt(id)); // lấy UserInfo theo ID
				if (userInfo.getCodeLevel() == null) { // đổi Codelevel = "" nếu ko tồn tai codeLevel để xử lí bên JSP
					userInfo.setCodeLevel(""); // nếu ko có codelevel thì ẩn trình độ TN
				}
				request.setAttribute("userInfo", userInfo); // gửi userInfo lên request để show ADM005
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM005);
				requestDispatcher.forward(request, response);

			} else { // không tồn tại User
				response.sendRedirect(Constant.ERROR + "?type=" + Constant.MSG_NO_RECORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
