/**
 * Controller xử lí khi người dùng chọn Button ChangePass tại ADM005
 * Copyright(C) 2017  Luvina
 * 
 * ChangePasswordController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.impl.TblUserLogicImpl;
import utils.Common;
import utils.Constant;
import validates.ValidateUser;

/**
 * 
 * Servlet implementation class ChangePasswordController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.CHANGE_PASS })
public class ChangePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePasswordController() {
		super();
	}

	/**
	 * Xử lí khi người dùng click vào Button ChangePass tại ADM005
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("key");
			request.setAttribute("idUser", id);
			if (Common.parseInt(id) > 0) {
				if (!(new TblUserLogicImpl().checkExistIdUser(Integer.valueOf(id)))) { // nếu tồn tại User
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.CHANGE_PASSWORD);
					requestDispatcher.forward(request, response);
				} else { // nếu User không tồn tại
					response.sendRedirect(Constant.ERROR + "?type=" + Constant.MSG_NO_RECORD);
				}
			} else { // không có IdUser
				response.sendRedirect(Constant.ERROR + "?type=" + Constant.MSG_NO_RECORD);
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
		String idUser = request.getParameter("idUser"); // lấy ID của User (truyền theo link)
		String passWord = request.getParameter("passWord"); // pass được nhập
		String passWordConfirm = request.getParameter("confirmPassWord"); // pass confirm được nhập
		try {
			List<String> err = new ArrayList<>();
			err = ValidateUser.validatePassUser(passWord, passWordConfirm);
			if (err.size() == 0) { // Nếu nhập pass đúng thành công(ko có lỗi)
				// update dữ liệu thành công
				if (new TblUserLogicImpl().updatetPasswordUser(Common.parseInt(idUser), passWord)) {
					response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.UPDARTE_SUCCESS);
				} else { // update dữ liệu thất bại
					response.sendRedirect(Constant.ERROR + "?type=" + Constant.UPDATE_ERROR);
				}
			} else { // Nếu nhập pass không thành công
				request.setAttribute("err", err);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.CHANGE_PASSWORD);
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
