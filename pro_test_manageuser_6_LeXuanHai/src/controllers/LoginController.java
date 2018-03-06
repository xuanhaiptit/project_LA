/**
 * 
 * Controller để xử lý login cho màn hình ADM001
 * Copyright(C) 2017  Luvina
 * 
 * LoginController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Constant;
import validates.ValidateUser;

/**
 * Controller để xử lý login cho màn hình ADM001
 * 
 * Servlet implementation class LoginController
 * 
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { "/" + Constant.LOGIN })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 0L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * Khi người dùng click vào button Login tại ADM001, dữ liệu sẽ được lấy từ form
	 * tại 2 textbox loginName và Pass. Sau đó sẽ so sánh dữ liệu với DB, nếu đúng
	 * là admin, thì mới có thể đăng nhập. Nếu không sẽ hiện thông báo lỗi
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginName = request.getParameter(Constant.LOGIN_NAME); // lấy dữ liệu loginName từ request
		String password = request.getParameter(Constant.PASS_WORD); // lấy dữ liệu Pass từ request

		try {
			ArrayList<String> err = new ArrayList<>();
			err = ValidateUser.validateLogin(loginName, password); // lấy câu thông báo lỗi
			if (err.size() == 0) { // Nếu đăng nhâp thành công (ko có lỗi)
				HttpSession session = request.getSession();
				session.setAttribute(Constant.LOGIN_NAME, loginName);
				response.sendRedirect(request.getContextPath() + Constant.LIST_ALL_USER);
			} else { // nếu đăng nhập không thành công (có lỗi)
				request.setAttribute("err", err); // gửi lỗi lên request để hiện tại ADM001
				request.setAttribute(Constant.LOGIN_NAME, loginName); // gửi lên request để vẫn hiện loginName đã nhập
																		// sai
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM001);
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
			e.printStackTrace();
		}
	}
}
