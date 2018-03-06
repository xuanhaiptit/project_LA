/**
 * Copyright(C) 2017  Luvina
 * 
 * MstJapanLogicImpl.java, Oct, 31, 2017, HaiLX
 */
package Logic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.impl.MstJapanDaoImpl;
import Logic.MstJapanLogic;
import entities.MstJapan;

/**
 * hàm xử lí dữ liệu của bảng mst_japan lấy từ MstJapanDao
 * 
 * @author xuanh
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.MstJapanLogic#getAllMstJapan()
	 */
	 
	@Override
	public List<MstJapan> getAllMstJapan() throws Exception {
		List<MstJapan> listJapan = new ArrayList<>();
		listJapan = new MstJapanDaoImpl().getAllMstJapan();
		return listJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Logic.MstJapanLogic#checkExistLevelJapan(java.lang.String)
	 */
	@Override
	public boolean checkExistLevelJapan(String codeLevel) {
		MstJapanDaoImpl mstJapan = new MstJapanDaoImpl();
		String levelJapan;
		try {
			levelJapan = mstJapan.getLevelJapan(codeLevel);
			if (levelJapan.isEmpty()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}
