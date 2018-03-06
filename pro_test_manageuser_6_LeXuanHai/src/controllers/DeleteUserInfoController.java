/**
 * 
 * Controller xử lí khi người dùng click vào button delete tại ADM005
 * Copyright(C) 2017  Luvina
 * 
 * DeleteUserInfoController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.impl.TblUserLogicImpl;
import utils.Common;
import utils.Constant;

/**
 * 
 * Servlet implementation class DeleteUserInfoController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.DELETE_USER_INFO })
public class DeleteUserInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserInfoController() {
		super();
	}

	/**
	 * Hàm xử lí khi người dùng click vào button Delete tại ADM005.
	 * 
	 * lệnh sẽ được gửi trực tiếp vào DB, và thực hiện hàm delete.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TblUserLogicImpl logicImpl = new TblUserLogicImpl();
		String id = request.getParameter("key"); // id được lấy từ request
		try {
			if (!logicImpl.checkExistIdUser(Common.parseInt(id))) { // check true nếu user tồn tại
				if ((logicImpl.deleteUserInfo(Common.parseInt(id)))) { // nếu delete User thành công
					response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.DELETE_SUCCESS);
				} else { // nếu delete User thất bại
					response.sendRedirect(
							Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR + Constant.DELETE_SUCCESS);
				}
			} else { // nếu user ko tồn tại
				response.sendRedirect(Constant.ERROR + "?type=" + Constant.MSG_NO_RECORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
