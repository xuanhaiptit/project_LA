/**
 * Copyright(C) 2017  Luvina
 * 
 * TblUserLogicImpl.java, Oct, 31, 2017, HaiLX
 */
package Logic.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.impl.BaseDaoImpl;
import Dao.impl.TblDetailUserJapanDaoImpl;
import Dao.impl.TblUserDaoImpl;
import Logic.TblUserLogic;
import entities.TblDetailUserJapan;
import entities.TblUser;
import entities.UserInfo;
import utils.Common;

/**
 * @author LeXuanHai TblUserLogicImpl.java Oct 24, 2017
 */
public class TblUserLogicImpl extends BaseDaoImpl implements TblUserLogic {
	public boolean checkTest() {
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#checkLogin(java.lang.String, java.lang.String)
	 */

	@Override
	public boolean checkLogin(String loginName, String passWord) throws Exception {
		TblUserDaoImpl userDaoImpl = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		// lấy dữ liệu từ DB theo loginNam, lấy ra gồm: loginName, password, Salt, Role
		tblUser = userDaoImpl.getUserByLoginId(loginName);
		String passwrodDB = tblUser.getPassword();
		int role = tblUser.getRole();
		// check password được nhập vào đã mã hóa có giống password đã mã hóa trong DB
		if (Common.getEnctypionPass(passWord, tblUser.getSalt()).equals(passwrodDB) && role == Common.getRoleAdmin()) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#getTotalUsers(int, java.lang.String)
	 */

	@Override
	public int getTotalUsers(int groupID, String fullName) throws Exception {
		TblUserDaoImpl daoImpl = new TblUserDaoImpl();
		fullName = Common.replaceWildCard(fullName);
		fullName = "%" + fullName + "%";
		int totalUser = daoImpl.getTotalUsers(groupID, fullName);
		return totalUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#getListUsers(int, int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public List<UserInfo> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		fullName = Common.replaceWildCard(fullName);
		fullName = "%" + fullName + "%";
		TblUserDaoImpl daoImpl = new TblUserDaoImpl();
		List<UserInfo> listUserInfos = new ArrayList<>();
		listUserInfos = daoImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
				sortByCodeLevel, sortByEndDate);
		return listUserInfos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#checkExistedEmail(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedEmail(Integer userId, String email) throws SQLException {
		TblUserDaoImpl userDao = new TblUserDaoImpl();
		if (userDao.getUserByEmail(userId, email) == null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#checkExistedLoginName(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) throws SQLException {
		TblUserDaoImpl userDao = new TblUserDaoImpl();
		if (userDao.getUserByLoginName(userId, loginName) == null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#createUser(Entities.ListUserInfo)
	 */
	@Override
	public boolean createUser(UserInfo userInfo) throws SQLException {
		boolean check = false;
		try {
			Connection conn = getConnect();
			if (conn != null) {
				String salt = Common.getKey(); // lấy mã salt
				TblUser tblUser = new TblUser();
				String codeLevel = userInfo.getCodeLevel();
				TblDetailUserJapan detailUserJapan = new TblDetailUserJapan();
				Common.setTblUser(userInfo, tblUser);
				tblUser.setPassword(Common.getEnctypionPass(userInfo.getPassWord(), salt)); // 4
				tblUser.setRole(0);
				tblUser.setSalt(salt);
				int generatedKey = 0;

				TblUserDaoImpl tblUserDaoImpl = new TblUserDaoImpl(conn);
				TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl(conn);
				conn.setAutoCommit(false);
				// kiểm tra xem đã tồn tại user
				if (new TblUserLogicImpl().checkExistedLoginName(0, tblUser.getLoginId())) {
					generatedKey = tblUserDaoImpl.insertUser(tblUser);
					if (generatedKey != 0) {
						check = true;
						if (!"0".equals(codeLevel)) { // trường hợp có trình độ tiếng Nhật
							detailUserJapan.setIdUser(generatedKey); // 1
							Common.setTblDetaiJapan(detailUserJapan, userInfo);
							check = tblDetailUserJapanDaoImpl.insertDetailUserJapan(detailUserJapan);
						}
					}
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			check = false;
		} finally {
			closeConnect();
		}
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#checkExitIdUser(int)
	 */
	@Override
	public boolean checkExistIdUser(int id) throws Exception {
		boolean check = new TblUserDaoImpl().checkExistIdUser(id);
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#getUserInfo(int)
	 */
	@Override
	public UserInfo getUserInfo(int id) throws SQLException {
		UserInfo userInfo = new TblUserDaoImpl().getUserInfo(id);
		return userInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#updateUser(Entities.ListUserInfo)
	 */
	@Override
	public boolean updateUser(UserInfo userInfo) throws SQLException {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblUser tblUser = new TblUser();
		boolean check = false;
		try {
			Connection conn = getConnect();
			if (conn != null) {
				TblUserDaoImpl tblUserDaoImpl = new TblUserDaoImpl(conn);
				TblDetailUserJapanDaoImpl daoImpl = new TblDetailUserJapanDaoImpl(conn);
				conn.setAutoCommit(false);

				tblUser.setId(userInfo.getId()); // 1
				Common.setTblUser(userInfo, tblUser);

				tblDetailUserJapan.setIdUser(userInfo.getId()); // 1
				Common.setTblDetaiJapan(tblDetailUserJapan, userInfo);
				// dữ liệu codeLevel trong DB
				String codeLevelOnDatabase = daoImpl.getCodeLevelByIdUser(userInfo.getId());
				// dữ liệu codeLevel trên View
				String codeLevelOnView = tblDetailUserJapan.getCodeLevel();
				// update User
				if (tblUserDaoImpl.updatetUser(tblUser)) {
					// nếu tại DB và View đều có codeLevel
					if ((!"".equals(codeLevelOnDatabase) && !"0".equals(codeLevelOnView))) {
						// thực hiện update
						check = daoImpl.updateDetailUserJapan(tblDetailUserJapan);
					} else if ((!"".equals(codeLevelOnDatabase) && "0".equals(codeLevelOnView))) {
						// thực hiện delete nếu ban đầu có trình độ tiếng Nhật sau đó không có
						check = daoImpl.deleteDetailUserJapan(tblDetailUserJapan.getIdUser());
					} else {
						// thực hiện insert nếu ban đầu ko có, sau đó có
						check = daoImpl.insertDetailUserJapan(tblDetailUserJapan);
					}
				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			check = false;
		} finally {
			closeConnect();
		}
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#updatetPasswordUser(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean updatetPasswordUser(int id, String password) throws SQLException {
		String salt = Common.getKey();
		password = Common.getEnctypionPass(password, salt);
		boolean check = new TblUserDaoImpl().updatetPasswordUser(id, salt, password);
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#deleteUserInfo(int)
	 */
	@Override
	public boolean deleteUserInfo(int id) throws SQLException {
		boolean check = false;
		try {
			Connection conn = getConnect();
			if (conn != null) {
				TblUserDaoImpl tblUserDaoImpl = new TblUserDaoImpl(conn);
				TblDetailUserJapanDaoImpl detailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl(conn);
				conn.setAutoCommit(false);
				detailUserJapanDaoImpl.deleteDetailUserJapan(id);
				check = (tblUserDaoImpl.deleteUser(id));
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			check = false;
		}
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.TblUserLogic#getUserInfoExport(java.lang.String, int)
	 */
	@Override
	public List<UserInfo> getUserInfoExport(String fullName, int groupId) throws Exception {
		List<UserInfo> listUser = new TblUserDaoImpl().getUserInfoExport(fullName, groupId);
		return listUser;
	}
}
