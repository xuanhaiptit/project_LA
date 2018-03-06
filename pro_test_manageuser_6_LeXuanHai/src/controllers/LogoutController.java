/**
 * 
 * Controller để xử lý logout khi người dùng click vào link logout
 * Copyright(C) 2017  Luvina
 * 
 * LogoutController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Constant;

/**
 * Servlet implementation class LogoutController
 * 
 * @author HaiLX
 */
@WebServlet(name = "LogoutController", urlPatterns = { Constant.LOGOUT })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
	}

	/**
	 * Controller sẽ đc thực hiện Khi người dùng click vào link logout tại header
	 * của các màn hình view
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			session.invalidate(); // tất cả các dữ liệu session được xóa
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM001);
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
