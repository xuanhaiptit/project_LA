/**
 * Copyright(C) 2017  Luvina
 * TblDetailUserJapan.java, Oct 24, 2017, HaiLX
 */
package entities;

import java.sql.Date;

/**
 * Class chứa các trường trong bảng TblDetailUserJapan
 * 
 * @author xuanh Oct 24, 2017
 *
 */
public class TblDetailUserJapan {
	private int detaiUserJapanId;
	private int idUser;
	private String codeLevel;
	private Date startDate;
	private Date endDate;
	private int total;
	/**
	 * @return the detaiUserJapanId
	 */
	public int getDetaiUserJapanId() {
		return detaiUserJapanId;
	}
	/**
	 * @param detaiUserJapanId the detaiUserJapanId to set
	 */
	public void setDetaiUserJapanId(int detaiUserJapanId) {
		this.detaiUserJapanId = detaiUserJapanId;
	}
	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}
	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}



}
