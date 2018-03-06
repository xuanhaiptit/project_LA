/**
 * 
 * Controller để xử lý khi người dùng thực hiện các việc add, update, change pass, delete không thành công
 * Copyright(C) 2017  Luvina
 * 
 * SystemErrorController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Constant;

/**
 * Controller xử lý các logic thông báo thành công và lỗi hệ thống
 * 
 * Servlet implementation class SystemErrorController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.ERROR })
public class SystemErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Controller xử lý các logic thông báo thành công và lỗi hệ thống
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public SystemErrorController() {
		super();
	}

	/**
	 * xử lý các logic thông báo thành công và lỗi hệ thống
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (Constant.SYSTEM_ERROR.equals(type)) {
			request.setAttribute(Constant.SYSTEM_ERROR_MARK, Constant.ER015);
		}
		if ((Constant.MSG_NO_RECORD).equals(type)) {
			request.setAttribute(Constant.SYSTEM_ERROR_MARK, properties.MessageErrorProperties.getString("ER013"));
		}
		if ((Constant.MSG_NO_RECORD + Constant.DELETE_SUCCESS).equals(type)) {
			request.setAttribute(Constant.SYSTEM_ERROR_MARK, properties.MessageErrorProperties.getString("ER014"));
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.SYSTEM_ERROR_PATH);
		dispatcher.forward(request, response);
	}

}
