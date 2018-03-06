
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath }/View/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>

	<!-- Begin vung header -->
	<%@ include file="header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="ListUser.do?type=add" method="post" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<c:if test="${empty listUser}">
					<td>
						<p class="lbl_error" style="color: #FF0000">検索条件に該当するユーザが見つかりません。</p>
					</td>
				</c:if>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="fullName" value="${fn:escapeXml(fullName)}" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>

							<td align="left" width="80px"><select name="groupId">
									<option value="0" id="0">全て</option>
									<c:forEach items="${listGroup}" var="list">
										<option
											${list.groupId == groupId ? 'selected = "selected"' : ''}
											value="${fn:escapeXml(list.groupId)}">${list.groupName}</option>
									</c:forEach>

							</select></td>

							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加"
								onclick="javascript:window.location.href='AddUserInput.do?type=ADM003'" />
								<input class="btn" type="button" value="Export Csv User"
								onclick="javascript:window.location.href='ExportCsvUser.do?type=ExportUser'" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
		width="80%">

		<tr class="tr2">
			<th align="center" width="20px">ID</th>
			<th align="left">氏名 <c:if
					test="${sortTypeName == '1' || empty sortTypeName }">
					<a href="ListUser.do?type=sort&sortName=fullName&sortType=1">▲▽</a>
				</c:if> <c:if test="${sortTypeName == '0'}">
					<a href="ListUser.do?type=sort&sortName=fullName&sortType=0">△▼</a>
				</c:if>
			</th>
			<th align="left">生年月日</th>
			<th align="left">グループ</th>
			<th align="left">メールアドレス</th>
			<th align="left" width="70px">電話番号</th>
			<th align="left">日本語能力 <c:if
					test="${sortTypeLevel == '1' || empty sortTypeLevel }">
					<a href="ListUser.do?type=sort&sortName=nameLevel&sortType=1">▲▽</a>
				</c:if> <c:if test="${sortTypeLevel == '0'}">
					<a href="ListUser.do?type=sort&sortName=nameLevel&sortType=0">△▼</a>
				</c:if></th>

			<th align="left">失効日<c:if
					test="${sortTypeDate == '1' || empty sortTypeDate }">
					<a href="ListUser.do?type=sort&sortName=endDate&sortType=1">▲▽</a>
				</c:if> <c:if test="${sortTypeDate == '0'}">
					<a href="ListUser.do?type=sort&sortName=endDate&sortType=0">△▼</a>
				</c:if>
			</th>
			<th align="left">点数</th>
		</tr>
		<c:forEach items="${listUser}" var="listUser">
			<tr>
				<td align="right"><a
					href="ShowUserInfor.do?userID=${listUser.id}">${listUser.id}</a></td>
				<td>${listUser.fullName}</td>
				<td align="center"><fmt:formatDate pattern="yyyy/MM/dd"
						value="${listUser.birthday}" /></td>
				<td>${listUser.group}</td>
				<td>${listUser.email}</td>
				<td>${listUser.tel}</td>
				<td>${listUser.codeLevel}</td>
				<td align="center"><fmt:formatDate pattern="yyyy/MM/dd"
						value="${listUser.endDate}" /></td>
				<td align="right">${listUser.total}</td>
			</tr>
		</c:forEach>

	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<c:if test="${totalPaging > 1}">
				<td class="lbl_paging"><c:if test="${pagingFirst > limitPage}">
						<a
							href="ListUser.do?type=page&pageType=${pagingFirst - limitPage}">&lsaquo;&lsaquo;</a>
					</c:if> <c:forEach items="${listPaging}" var="Paging">
						<c:if test="${Paging == currentPage}">
							<span>${Paging}</span>
						</c:if>
						<c:if test="${Paging != currentPage}">
							<a href="ListUser.do?type=page&pageType=${Paging}">${Paging}</a>
						</c:if>
					</c:forEach> <c:if test="${pagingFirst +limitPage <= totalPaging}">
						<a href="ListUser.do?type=page&pageType=${pagingFirst +limitPage}">&rsaquo;&rsaquo;</a>
					</c:if></td>
			</c:if>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<%@ include file="footer.jsp"%>
	<!-- End vung footer -->

</body>

</html>