/**
 * Copyright(C) 2017  Luvina
 * MstGroup.java, Oct 24, 2017, HaiLX
 */
package entities;

/**
 * Class chứa các trường trong bảng MstGroup
 * 
 * @author LeXuanHai MstGroup.java Oct 25, 2017
 */
public class MstGroup {
	private int groupId;
	private String groupName;

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
