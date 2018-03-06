function clickLinkLevelJapanese() {
	var str = document.getElementById('tableLvelJapan').style;
	if (str.display == 'block') {
		str.display = 'none';
	} else if (str.display == 'none') {
		str.display = 'block';
	}
}
function alertDelete(userId) {
	var check = window.confirm("削除しますが、よろしいでしょうか。");
	if (check) {
		window.location = "DeleteUserInfo.do?key=" + userId;
	}
}