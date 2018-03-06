/**
 * Controller xử lí khi người dùng click vào button Export csv tại ADM002
 * Copyright(C) 2017  Luvina
 * 
 * ExportController.java, Oct, 31, 2017, HaiLX
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Logic.impl.TblUserLogicImpl;
import utils.Common;
import utils.Constant;

/**
 * Controller xử lí khi người dùng click vào button ExportUserCsv tại ADM002
 * Servlet implementation class ExportController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.EXPORT_CSV_USER })
public class ExportCsvUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportCsvUserController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			List<?> listData = new ArrayList<>(); // list dữ liệu cần cho lên file
			String type = request.getParameter("type");
			String fileName = "";

			if ("ExportUser".equals(type)) { // khi người dùng click button Export csv tại ADM002
				String groupId = (String) session.getAttribute("groupId");
				int idGroup = Common.parseInt(groupId);
				String fullName = (String) session.getAttribute("fullName");
				// các dữ liệu được lấy: listData, fileName

				listData = new TblUserLogicImpl().getUserInfoExport(fullName, idGroup);

				fileName = "listUser";
			}
			// gửi dữ liệu lên file với các dữ liệu listData, type, fileName
			Common.writerDataInFile(response, listData, type, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
