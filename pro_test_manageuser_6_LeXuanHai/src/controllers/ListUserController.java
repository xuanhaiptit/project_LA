/**
 * Controller để xử lý cho màn hình ADM002 sau khi người dùng đăng nhập thành công
 * 
 * Copyright(C) 2017  Luvina
 * 
 * ListUserController.java, Oct, 31, 2017, HaiLX
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
import javax.servlet.http.HttpSession;

import Logic.impl.MstGroupLogicImpl;
import Logic.impl.TblUserLogicImpl;
import entities.MstGroup;
import entities.UserInfo;
import utils.Common;
import utils.Constant;

/**
 * Servlet implementation class ListUserController
 * 
 * @author HaiLX
 */
@WebServlet(urlPatterns = { Constant.LIST_USER })
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 2L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListUserController() {
		super();
	}

	/**
	 * Do có các sự kiện sẽ thực hiện trong doGet, nhưng sẽ chuyển hết vào doPost để
	 * xử lí 1 luồng
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Xử lý các dữ liệu liên quan đến màn hình ADM002 tại doPost
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sortByEndDate = Constant.DESC; // kiểu sắp xếp theo EndDate mặc định
		String sortByCodeLevel = Constant.ASC; // kiểu sắp xếp theo Codelevel mặc định
		String sortByFullName = Constant.DESC; // kiểu sắp xếp theo FullName mặc định
		String groupId = ""; // dữ liệu tại textBox groupId trên ADM002
		String fullName = ""; // dữ liệu tại text tìm kiếm theo tên trên ADM002
		String sortType = ""; // kiểu dữ liệu sẽ sắp xếp
		int totalPaging; // tổng số trang
		int pagingFirst; // trang bắt đầu (bên trái)
		int idGroup; // Id Group để xử lí tại DB
		int totalUser; // tổng số User
		int offset = 0; // vị trí bắt đầu xuất hiện trong DB
		int limit = Common.getLimit(); // số user sẽ được xuất hiện tại view ADM002
		int limitPage = Common.getLimitPage(); // số trang sẽ hiện tại (1,2,3) view ADM002
		int currentPage = 1; // số đánh trang hiện tại
		MstGroupLogicImpl mstGroupLogicImpl = new MstGroupLogicImpl();
		List<MstGroup> listGroup = new ArrayList<>();
		listGroup = mstGroupLogicImpl.getAllMstGroup();
		request.setAttribute("listGroup", listGroup);
		HttpSession session = request.getSession();
		String type = request.getParameter("type");
		// lấy các dữ liệu đã được nhập tại ô tìm kiếm fullName và group
		groupId = (String) session.getAttribute("groupId");
		fullName = (String) session.getAttribute("fullName");
		try {
			if ("listAllUser".equals(type)) { // xảy ra khi admin đăng nhập thành công
				fullName = ""; // chưa có tìm kiếm về một tên nào đó (sẽ tìm tất cả các tên)
				groupId = "0"; // chưa chọn 1 group nào đó ( sẽ tìm tất cả group)
			} else if (("sort").equals(type)) { // xảy ra khi chọn vào sắp xếp
				String sortName = request.getParameter("sortName");
				sortByFullName = (String) session.getAttribute("sortByFullName");
				sortByCodeLevel = (String) session.getAttribute("sortByCodeLevel");
				sortByEndDate = (String) session.getAttribute("sortByEndDate");
				if ("fullName".equals(sortName)) { // xảy ra khi chọn vào sắp xếp theo column fullName
					sortType = "fullName";
					String sortname = request.getParameter("sortType");
					if ("1".equals(sortname)) { // nếu sắp xếp tăng
						sortByFullName = Constant.ASC;
						session.setAttribute("sortTypeName", "0");
					} else { // nếu sắp xếp giảm
						sortByFullName = Constant.DESC;
						session.setAttribute("sortTypeName", "1");
					}
				} else if ("nameLevel".equals(sortName)) { // xảy ra khi chọn vào sắp xếp theo column fullName
					sortType = "nameLevel";
					String sortname = request.getParameter("sortType");
					if ("1".equals(sortname)) { // nếu sắp xếp tăng
						sortByCodeLevel = Constant.ASC;
						session.setAttribute("sortTypeLevel", "0");
					} else { // nếu sắp xếp giảm
						sortByCodeLevel = Constant.DESC;
						request.setAttribute("sortTypeLevel", "1");
					}
				} else { // sắp xếp theo cột EndDate
					sortType = "endDate";
					String sortname = request.getParameter("sortType");
					if ("1".equals(sortname)) { // nếu sắp xếp tăng
						sortByEndDate = Constant.ASC;
						request.setAttribute("sortTypeDate", "0");
					} else { // nếu sắp xếp giảm
						sortByEndDate = Constant.DESC;
						request.setAttribute("sortTypeDate", "1");
					}
				}
			} else if ("page".equals(type)) { // Xảy ra khi chọn chuyển trang
				// pageType chính là số trang được đặt tại
				// ví dụ: << 4 5 6 << thì pageType của << là 1, >> là 7
				currentPage = Common.parseInt(request.getParameter("pageType"));
			} else if ("back".equals(type)) {
				// số thứ tự trang hiện tại khi click back sẽ được giữ nguyên trước và sau
				currentPage = (int) session.getAttribute("currentPage");
			} else { // xảy ra khi chọn tìm kiếm
				groupId = request.getParameter("groupId");
				fullName = request.getParameter("fullName");
				if (groupId == null) { // nếu không chọn Group
					groupId = "0";
				}
				if (fullName == null) { // nếu không nhập trường fullName tìm kiếm
					fullName = "";
				}
			}
			// xử lí chung cho các trường hợp
			TblUserLogicImpl logicImpl = new TblUserLogicImpl();
			List<UserInfo> listUserInfos = new ArrayList<>();
			List<Integer> listPaging = new ArrayList<>();
			idGroup = Common.parseInt(groupId);
			// Xử lý phân trang (sẽ chạy ngay từ đầu khi từ login thành công )
			totalUser = logicImpl.getTotalUsers(idGroup, fullName);
			if (totalUser != 0) {
				listPaging = Common.getListPaging(totalUser, limit, currentPage);
				totalPaging = Common.getTotalPage(totalUser, limit);
				offset = Common.getOffset(currentPage, limit);
				pagingFirst = listPaging.get(0);
				// truyền dữ liệu cần xử lí lên DB
				listUserInfos = logicImpl.getListUsers(offset, limit, idGroup, fullName, sortType, sortByFullName,
						sortByCodeLevel, sortByEndDate);

				request.setAttribute("pagingFirst", pagingFirst);
				request.setAttribute("totalPaging", totalPaging);
				request.setAttribute("listPaging", listPaging);
				request.setAttribute("listUser", listUserInfos);
			}
			fullName = Common.replaceWildCard(fullName); // thay dữ liệu nhập vào để xử lí tại DB
			session.setAttribute("groupId", groupId);
			session.setAttribute("fullName", fullName);
			session.setAttribute("sortByFullName", sortByFullName);
			session.setAttribute("sortByCodeLevel", sortByCodeLevel);
			session.setAttribute("sortByEndDate", sortByEndDate);
			session.setAttribute("limitPage", limitPage);
			session.setAttribute("currentPage", currentPage);

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM002);
			dispatcher.forward(request, response);
		} catch (Exception e) { // Nếu gặp lỗi hệ thống thì sẽ về màn hình ADM006
			e.printStackTrace();
			response.sendRedirect(Constant.ERROR + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
