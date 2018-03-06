/**
 * Copyright(C) 2017  Luvina
 * 
 * MstGroupDao.java, Oct 25, 2017, HaiLX
 */
package Dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.MstGroupDao;
import entities.MstGroup;

/**
 * class xử lí dữ liệu của bảng Group trong DB
 * 
 * @author LeXuanHai MstGroupDaoImpl.java Oct 25, 2017
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	PreparedStatement statement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.MstGroupDao#getAllMstGroup()
	 */
	// ok
	@Override
	public List<MstGroup> getAllMstGroup()  {
		List<MstGroup> listGroup = new ArrayList<>();
		try {
			conn = getConnect();
			if (conn != null) {
				String sql = "Select * from mst_group;";
				statement = conn.prepareStatement(sql);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					MstGroup mstGroup = new MstGroup();
					mstGroup.setGroupId(rs.getInt(1));
					mstGroup.setGroupName(rs.getString(2));
					listGroup.add(mstGroup);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		} finally {
			closeConnect();
		}
		return listGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.MstGroupDao#getGroupName(int)
	 */
	// ok
	@Override
	public String getGroupName(int groupId) throws SQLException {
		String groupName = "";
		try {
			if (conn != null) {
				String sql = "Select * from mst_group WHERE group_id = ?";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, groupId);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					groupName = rs.getString(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			closeConnect();
		}
		return groupName;
	}
}
