package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 智能公厕人流量报表
 * @author Administrator
 *
 */
public class FtInteWcPersonNum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219023586580221029L;
	
	//公厕id
	private String id;
	//公厕编码
	private String code;
	//公厕名称
	private String name;
	//类型名称
	private String classesName;
	//公厕地址
	private String address;
	//管理单位
	private String manageUnitName;
	//坑位数（个）
	private Integer squatNum;
	//人流总量（人）
	private Integer sumCaculateValue;
	//报警个数（针对一个公厕的）
	private Integer count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		
		return code != null? code : "";
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name != null? name : "";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassesName() {
		return classesName != null? classesName : "";
	}
	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}
	public String getAddress() {
		return address != null? address : "";
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManageUnitName() {
		return manageUnitName != null? manageUnitName : "";
	}
	public void setManageUnitName(String manageUnitName) {
		this.manageUnitName = manageUnitName;
	}
	public Integer getSquatNum() {
		return squatNum != null? squatNum : 0;
	}
	public void setSquatNum(Integer squatNum) {
		this.squatNum = squatNum;
	}
	public Integer getSumCaculateValue() {
		return sumCaculateValue != null? sumCaculateValue : 0;
	}
	public void setSumCaculateValue(Integer sumCaculateValue) {
		this.sumCaculateValue = sumCaculateValue;
	}
	public Integer getCount() {
		return count != null? count : 0;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((classesName == null) ? 0 : classesName.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((manageUnitName == null) ? 0 : manageUnitName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((squatNum == null) ? 0 : squatNum.hashCode());
		result = prime * result + ((sumCaculateValue == null) ? 0 : sumCaculateValue.hashCode());
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
		FtInteWcPersonNum other = (FtInteWcPersonNum) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (classesName == null) {
			if (other.classesName != null)
				return false;
		} else if (!classesName.equals(other.classesName))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (manageUnitName == null) {
			if (other.manageUnitName != null)
				return false;
		} else if (!manageUnitName.equals(other.manageUnitName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (squatNum == null) {
			if (other.squatNum != null)
				return false;
		} else if (!squatNum.equals(other.squatNum))
			return false;
		if (sumCaculateValue == null) {
			if (other.sumCaculateValue != null)
				return false;
		} else if (!sumCaculateValue.equals(other.sumCaculateValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FtInteWcPersonNum [id=" + id + ", code=" + code + ", name=" + name + ", classesName=" + classesName
				+ ", address=" + address + ", manageUnitName=" + manageUnitName + ", squatNum=" + squatNum
				+ ", sumCaculateValue=" + sumCaculateValue + ", count=" + count + "]";
	}
	
	
	
	
	
}
