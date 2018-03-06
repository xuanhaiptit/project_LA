/**
 * Copyright(C) 2017  Luvina
 * 
 * MstGroupLogicImpl.java, Oct, 31, 2017, HaiLX
 */
package Logic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.impl.MstGroupDaoImpl;
import Logic.MstGroupLogic;
import entities.MstGroup;

/**
 * @author LeXuanHai MstGroupLogicImpl.java Oct 25, 2017
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.MstGroupLogic#getAllMstGroup()
	 */
	@Override

	public List<MstGroup> getAllMstGroup() {
		List<MstGroup> listGroup = new ArrayList<>();
		MstGroupDaoImpl mstGroupLogicImpl = new MstGroupDaoImpl();
		listGroup = mstGroupLogicImpl.getAllMstGroup();
		return listGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.MstGroupLogic#checkExistedGroup(int)
	 */
	@Override
	public boolean checkExistedGroup(int groupId) throws SQLException {
		MstGroupDaoImpl mstGroupDaoImpl = new MstGroupDaoImpl();
		String groupName = mstGroupDaoImpl.getGroupName(groupId);
		if (groupName.isEmpty()) {
			return false;
		}
		return true;
	}

}
