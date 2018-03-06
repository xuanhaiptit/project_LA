/**
 * Copyright(C) 2017  Luvina
 * 
 * TblUserDaoImpl.java, Oct 25, 2017, HaiLX
 */
package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.TblUserDao;
import entities.TblUser;
import entities.UserInfo;
import utils.Common;

/**
 * Class kế thừa class TblUserDao
 * 
 * @author LeXuanHai TblUserDaoImpl.java Oct 24, 2017
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	PreparedStatement statement;

	/**
	 * Hàm khởi tạo TblUserDaoImpl không
	 * 
	 */
	public TblUserDaoImpl() {
	}

	/**
	 * Hàm khởi tạo TblUserDaoImpl có tham số
	 * 
	 * @param conn
	 *            Connection truyền vào
	 */
	public TblUserDaoImpl(Connection conn) {
		this.conn = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getUserByLoginId(java.lang.String)
	 */
	// ok
	@Override
	public TblUser getUserByLoginId(String loginName) throws Exception {
		TblUser user = new TblUser();
		try {
			conn = getConnect();
			String sql = "select login_name, password, role, salt from tbl_user where login_name = ?;";
			if (conn != null) {
				statement = conn.prepareStatement(sql);
				statement.setString(1, loginName);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					int dem = 1;
					user.setLoginId(rs.getString(dem++));
					user.setPassword(rs.getString(dem++));
					user.setRole(rs.getInt(dem++));
					user.setSalt(rs.getString(dem++));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getSalt(java.lang.String)
	 */
	@Override
	public String getSalt(String loginName) throws Exception {
		String salt = "";
		try {
			conn = getConnect();
			String sql = "select salt from tbl_user where login_name = ?;";
			if (conn != null) {
				statement = conn.prepareStatement(sql);
				statement.setString(1, loginName);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					salt = rs.getString(1);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return salt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupID, String fullName) throws Exception {
		int totalUsers = 0;
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT count( tUser.user_id) ");
				sql.append(" FROM tbl_user tUser inner join mst_group mGroup on tUser.group_id = mGroup.group_id ");
				sql.append(
						" left join ( tbl_detail_user_japan dJapan inner join mst_japan mJapan on dJapan.code_level = mJapan.code_level ) ");
				sql.append(" on tUser.user_id = dJapan.user_id where tUser.role = ?  and  full_name LIKE ? ");
				if (groupID != 0) {
					sql.append(" and mGroup.group_id = ? ");
				}
				statement = conn.prepareStatement(sql.toString());
				int temp = 1;
				statement.setInt(temp++, Common.getRoleMember());
				statement.setString(temp++, fullName);
				if (groupID != 0) {
					statement.setInt(temp++, groupID);
				}
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					totalUsers = rs.getInt(1);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return totalUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getListUsers(int, int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	// ok
	@Override
	public List<UserInfo> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		List<UserInfo> listUser = new ArrayList<>();
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append(
						"SELECT tUser.user_id, tUser.full_name, tUser.birthday,  mGroup.group_name, tUser.email, tUser.tel, mJapan.name_level, dJapan.end_date, dJapan.total ");
				sql.append(" FROM tbl_user tUser inner join mst_group mGroup on tUser.group_id = mGroup.group_id ");
				sql.append(
						" left join ( tbl_detail_user_japan dJapan inner join mst_japan mJapan on dJapan.code_level = mJapan.code_level ) ");
				sql.append(" on tUser.user_id = dJapan.user_id where tUser.role = ? and  full_name LIKE ? ");
				if (groupId != 0) {
					sql.append(" and mGroup.group_id = ? ");
				}
				String str = "";
				if ("".equals(sortType)) {
					str = " LIMIT ? OFFSET ? ;";
				}
				if ("fullName".equals(sortType)) {
					str = " order by full_name " + sortByFullName + " , name_level " + sortByCodeLevel + ", end_date "
							+ sortByEndDate + " LIMIT ? OFFSET ?;";
				} else if ("nameLevel".equals(sortType)) {
					str = " order by name_level " + sortByCodeLevel + " , full_name " + sortByFullName + ", end_date "
							+ sortByEndDate + " LIMIT ? OFFSET ?;";
				} else if ("endDate".equals(sortType)) {
					str = " order by end_date " + sortByEndDate + " , full_name " + sortByFullName + ", name_level "
							+ sortByCodeLevel + " LIMIT ? OFFSET ?;";
				}
				sql.append(str);

				int temp = 1;
				statement = conn.prepareStatement(sql.toString());
				statement.setInt(temp++, Common.getRoleMember());
				statement.setString(temp++, "%" + fullName + "%");
				if (groupId != 0) {
					statement.setInt(temp++, groupId);
				}
				statement.setInt(temp++, limit);
				statement.setInt(temp++, offset);

				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					UserInfo user = new UserInfo();
					int dem = 1;
					user.setId(rs.getInt(dem++));
					user.setFullName(rs.getString(dem++));
					user.setBirthday(rs.getDate(dem++));
					user.setGroup(rs.getString(dem++));
					user.setEmail(rs.getString(dem++));
					user.setTel(rs.getString(dem++));
					user.setCodeLevel(rs.getString(dem++));
					user.setEndDate(rs.getDate(dem++));
					user.setTotal(String.valueOf(rs.getInt(dem++)));
					listUser.add(user);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return listUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getUserByEmail(java.lang.Integer, java.lang.String)
	 */
	@Override
	public TblUser getUserByEmail(Integer userId, String email) throws SQLException {
		TblUser tblUser = null;
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM tbl_user WHERE email = ?");
				if (userId > 0) {
					sql.append("and user_id = ?");
				}
				int i = 0;
				statement = conn.prepareStatement(sql.toString());
				statement.setString(++i, email);
				if (userId > 0) {
					statement.setInt(++i, userId);
				}
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					tblUser = new TblUser();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return tblUser;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getUserByLoginName(java.lang.Integer, java.lang.String)
	 */
	@Override
	public TblUser getUserByLoginName(Integer userId, String loginName) throws SQLException {
		TblUser tblUser = null;
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM tbl_user WHERE login_name = ?");
				if (userId > 0) {
					sql.append("and user_id = ?");
				}
				int i = 0;
				statement = conn.prepareStatement(sql.toString());
				statement.setString(++i, loginName);
				if (userId > 0) {
					statement.setInt(++i, userId);
				}
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					tblUser = new TblUser();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#insertUser(Entities.TblUser)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		try {
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append(
						"INSERT INTO tbl_user (group_id, login_name, password, full_name, full_name_kana, email, tel, birthday, role, salt) ");
				sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				int i = 1;
				statement = conn.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
				statement.setInt(i++, tblUser.getGroupId());
				statement.setString(i++, tblUser.getLoginId());
				statement.setString(i++, tblUser.getPassword());
				statement.setString(i++, tblUser.getFullName());
				statement.setString(i++, tblUser.getFullNameKana());
				statement.setString(i++, tblUser.getEmail());
				statement.setString(i++, tblUser.getTel());
				statement.setDate(i++, tblUser.getBirthday());
				statement.setInt(i++, tblUser.getRole());
				statement.setString(i++, tblUser.getSalt());

				statement.execute();
				ResultSet rs = statement.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next()) {
					generatedKey = rs.getInt(1);
				}
				return generatedKey;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#checkExitIdUser(int)
	 */
	@Override
	public boolean checkExistIdUser(int id) throws Exception {
		int temp = 0;
		try {
			conn = getConnect();
			if (conn != null) {
				String sql = "SELECT count(user_id) FROM tbl_user where user_id = ? and role = ? ;";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, id);
				statement.setInt(2, Common.getRoleMember());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					temp = rs.getInt(1);
				}
				if (temp == 0) { // không tìm thấy thì return true
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getUserInfo(int)
	 */
	@Override
	public UserInfo getUserInfo(int id) throws SQLException {
		UserInfo userInfo = new UserInfo();
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append(
						"SELECT tUser.user_id, tUser.group_id, tUser.login_name, tUser.full_name, tUser.full_name_kana, tUser.birthday,  tUser.email, tUser.tel, mJapan.code_level, dJapan.start_date, dJapan.end_date, dJapan.total ");
				sql.append(" FROM tbl_user tUser inner join mst_group mGroup on tUser.group_id = mGroup.group_id ");
				sql.append(
						" left join ( tbl_detail_user_japan dJapan inner join mst_japan mJapan on dJapan.code_level = mJapan.code_level ) ");
				sql.append(" on tUser.user_id = dJapan.user_id where tUser.user_id = ? ");

				statement = conn.prepareStatement(sql.toString());
				statement.setInt(1, id);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					int dem = 1;
					userInfo.setId(rs.getInt(dem++));
					userInfo.setGroup(String.valueOf(rs.getInt(dem++)));
					userInfo.setLoginName(rs.getString(dem++));
					userInfo.setFullName(rs.getString(dem++));
					userInfo.setKanaName(rs.getString(dem++));
					userInfo.setBirthday(rs.getDate(dem++));
					userInfo.setEmail(rs.getString(dem++));
					userInfo.setTel(rs.getString(dem++));
					userInfo.setCodeLevel(rs.getString(dem++));
					userInfo.setStartDate(rs.getDate(dem++));
					userInfo.setEndDate(rs.getDate(dem++));
					userInfo.setTotal(String.valueOf(rs.getInt(dem++)));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return userInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#updatetUser(Entities.TblUser)
	 */
	@Override
	public boolean updatetUser(TblUser tblUser) throws SQLException {
		try {
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE tbl_user ");
				sql.append("SET group_id=?, login_name =?, full_name =?, ");
				sql.append("full_name_kana=?, email=?, tel=?, birthday=? ");
				sql.append("WHERE user_id=?;");
				statement = conn.prepareStatement(sql.toString());
				int temp = 1;
				statement.setInt(temp++, tblUser.getGroupId()); // 1
				statement.setString(temp++, tblUser.getLoginId()); // 2
				statement.setString(temp++, tblUser.getFullName()); // 3
				statement.setString(temp++, tblUser.getFullNameKana()); // 4
				statement.setString(temp++, tblUser.getEmail());// 5
				statement.setString(temp++, tblUser.getTel()); // 6
				statement.setDate(temp++, tblUser.getBirthday()); // 7
				statement.setInt(temp++, tblUser.getId()); // 8
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#updatetPasswordUser(java.lang.String)
	 */
	@Override
	public boolean updatetPasswordUser(int id, String salt, String password) throws SQLException {
		try {
			conn = getConnect();
			if (conn != null) {
				String sql = "UPDATE tbl_user SET password=?, salt=? WHERE user_id=?;";

				statement = conn.prepareStatement(sql);
				int temp = 1;
				statement.setString(temp++, password);
				statement.setString(temp++, salt);
				statement.setInt(temp++, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#deleteUserInfo(int)
	 */
	@Override
	public boolean deleteUser(int id) throws SQLException {
		try {
			if (conn != null) {
				String sql = "DELETE FROM tbl_user WHERE user_id = ?;";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.TblUserDao#getUserInfoExport(java.lang.String, int)
	 */
	@Override
	public List<UserInfo> getUserInfoExport(String fullName, int groupId) throws Exception {
		List<UserInfo> listUser = new ArrayList<>();
		try {
			conn = getConnect();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append(
						"SELECT tUser.user_id, tUser.login_name, tUser.full_name, tUser.birthday,  mGroup.group_name, tUser.email, tUser.tel, mJapan.name_level, dJapan.end_date, dJapan.total ");
				sql.append(" FROM tbl_user tUser inner join mst_group mGroup on tUser.group_id = mGroup.group_id ");
				sql.append(
						" left join ( tbl_detail_user_japan dJapan inner join mst_japan mJapan on dJapan.code_level = mJapan.code_level ) ");
				sql.append(" on tUser.user_id = dJapan.user_id where tUser.role = ?  and  full_name LIKE ? ");
				if (groupId != 0) {
					sql.append(" and mGroup.group_id = ? ");
				}
				int temp = 1;
				statement = conn.prepareStatement(sql.toString());
				statement.setInt(temp++, Common.getRoleMember());
				statement.setString(temp++, "%" + fullName + "%");
				if (groupId != 0) {
					statement.setInt(temp++, groupId);
				}
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					UserInfo info = new UserInfo();
					int dem = 1;
					info.setId(rs.getInt(dem++));
					info.setLoginName(rs.getString(dem++));
					info.setFullName(rs.getString(dem++));
					info.setBirthday(rs.getDate(dem++));
					info.setGroup(rs.getString(dem++));
					info.setEmail(rs.getString(dem++));
					info.setTel(rs.getString(dem++));
					info.setCodeLevel(rs.getString(dem++));
					info.setEndDate(rs.getDate(dem++));
					info.setTotal(String.valueOf(rs.getInt(dem++)));
					listUser.add(info);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return listUser;
	}
}
