package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 查询清洁员信息 返回实体
 * @author Administrator
 *
 */
public class FtQjyInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1117201854678752612L;
	//员工id
	private String staffId;
	//员工编号
	private String staffCode;
	//员工姓名
	private String staffName;
	//性别
	private String gender;
	//手机号
	private String phone;
	
//	//账号名
//	private String account;
//	//密码
//	private String password;
//	//身份证号
//	private String idCard;
//	//年龄
//	private String age;
//	
//	//籍贯
//	private String birthPlace;
//	//职务
//	private String duty;
//	//部门id
//	private String departmentId;
//	//创建时间
//	private String createTime;
//	//更新时间
//	private String updateTime;
//	//状态 0不可用  1有效
//	private String status;

	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((staffCode == null) ? 0 : staffCode.hashCode());
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		result = prime * result + ((staffName == null) ? 0 : staffName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FtQjyInfo other = (FtQjyInfo) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (staffCode == null) {
			if (other.staffCode != null)
				return false;
		} else if (!staffCode.equals(other.staffCode))
			return false;
		if (staffId == null) {
			if (other.staffId != null)
				return false;
		} else if (!staffId.equals(other.staffId))
			return false;
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		return true;
	}
	

	
	
	
	
	
}
