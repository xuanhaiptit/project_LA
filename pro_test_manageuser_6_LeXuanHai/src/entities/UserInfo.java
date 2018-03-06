/**
 * Copyright(C) 2017  Luvina
 * UserInfo.java, Oct 24, 2017, HaiLX
 */
package entities;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Class chứa các thuộc tính của listUserInfo
 * 
 * @author LeXuanHai UserInfo.java Oct 24, 2017
 */
public class UserInfo {
	private int id;
	private String loginName;
	private String group;
	private String fullName;
	private String kanaName;
	private Date birthday;
	private String email;
	private String tel;
	private String passWord;
	private String confirmPassWord;
	private String codeLevel;
	private Date startDate;
	private Date endDate;
	private String total;
	private ArrayList<Integer> arrBirthDay;
	private ArrayList<Integer> arrStartDate;
	private ArrayList<Integer> arrEndDate;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the kanaName
	 */
	public String getKanaName() {
		return kanaName;
	}
	/**
	 * @param kanaName the kanaName to set
	 */
	public void setKanaName(String kanaName) {
		this.kanaName = kanaName;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}
	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	/**
	 * @return the confirmPassWord
	 */
	public String getConfirmPassWord() {
		return confirmPassWord;
	}
	/**
	 * @param confirmPassWord the confirmPassWord to set
	 */
	public void setConfirmPassWord(String confirmPassWord) {
		this.confirmPassWord = confirmPassWord;
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
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the arrBirthDay
	 */
	public ArrayList<Integer> getArrBirthDay() {
		return arrBirthDay;
	}
	/**
	 * @param arrBirthDay the arrBirthDay to set
	 */
	public void setArrBirthDay(ArrayList<Integer> arrBirthDay) {
		this.arrBirthDay = arrBirthDay;
	}
	/**
	 * @return the arrStartDate
	 */
	public ArrayList<Integer> getArrStartDate() {
		return arrStartDate;
	}
	/**
	 * @param arrStartDate the arrStartDate to set
	 */
	public void setArrStartDate(ArrayList<Integer> arrStartDate) {
		this.arrStartDate = arrStartDate;
	}
	/**
	 * @return the arrEndDate
	 */
	public ArrayList<Integer> getArrEndDate() {
		return arrEndDate;
	}
	/**
	 * @param arrEndDate the arrEndDate to set
	 */
	public void setArrEndDate(ArrayList<Integer> arrEndDate) {
		this.arrEndDate = arrEndDate;
	}

	
	
}
