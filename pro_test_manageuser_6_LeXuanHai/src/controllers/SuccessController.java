/**
 * 
 * Controller để xử lý khi người dùng thực hiện các việc add, update, change pass, delete thành công
 * Copyright(C) 2017  Luvina
 * 
 * SuccessController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import properties.MessageErrorProperties;
import utils.Constant;

/**
 * Servlet implementation class SuccessController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.SUCCESS })
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuccessController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String type = request.getParameter("type");
		/*
		 * Lấy giá trị type từ request: + Nếu type = success thì hiện thị ADM002 + Nếu
		 * type = error thì hiện thị system_error với câu thông báo lỗi tương ứng
		 */
		if (Constant.INSERT_SUCCESS.equals(type)) {
			request.setAttribute(Constant.MESSAGE, MessageErrorProperties.getString("INSERT_SUCCESS"));
		}

		if (Constant.EDIT_SUCCESS.equals(type)) {
			request.setAttribute(Constant.MESSAGE, MessageErrorProperties.getString("UPDATE_SUCCESS"));
		}

		if (Constant.DELETE_SUCCESS.equals(type)) {
			request.setAttribute(Constant.MESSAGE, MessageErrorProperties.getString("DELETE_SUCCESS"));
		}
		dispatcher = request.getRequestDispatcher(Constant.ADM006);
		dispatcher.forward(request, response);
	}
}
