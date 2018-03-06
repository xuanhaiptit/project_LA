/**
 * Copyright(C) 2017  Luvina
 * LoginFilter.java, Oct 24, 2017, HaiLX
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Common;
import utils.Constant;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns = { "/*" })
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath(); // đường dẫn servlet

		String checkLogin = "/" + Constant.LOGIN; // /login.do
		System.out.println("start 2");
		System.out.println(servletPath);
		System.out.println("start 3");
		if (!checkLogin.equals(servletPath)) { // kiểm tra xem có phải địa chỉ login
			if ("".equals(Common.checkLogin(session))) { // nếu chưa đăng nhập về ADM001
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM001);
				dispatcher.forward(request, response);
			} else if (servletPath.contains(".jsp")) { // nếu chọn URL chứa JSP
				response.sendRedirect(request.getContextPath() + Constant.LIST_ALL_USER);
			} else {
				// Cho phép request vượt qua Filter
				chain.doFilter(req, resp);
			}
		} else { // nếu đang ở giao diện login
			if (!"".equals(Common.checkLogin(session))) {
				response.sendRedirect(request.getContextPath() + Constant.LIST_ALL_USER);
			} else {
				chain.doFilter(req, resp);
			}
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
