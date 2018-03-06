<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<div>
		<table>
			<tr>
				<td width="80%"><img
					src="${pageContext.request.contextPath }/View/images/logo-manager-user.gif"
					alt="Luvina" />
				<td>
				<td align="left"><a
					href="<%=request.getContextPath() + "/LogoutController"%>">
						ログアウト </a> &nbsp; <a
					href="<%=request.getContextPath() + "/ListUser.do"%>"> トップ </a>
				<td>
			</tr>
		</table>
	</div>
</div>