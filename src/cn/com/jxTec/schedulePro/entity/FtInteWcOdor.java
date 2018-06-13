package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 智能公厕臭气值报表
 * 温度统计报表
 * 湿度统计报表
 * @author Administrator
 *
 */
public class FtInteWcOdor implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1494454186330139946L;
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
	//平均浓度
	private Float aValue;
	//最高浓度
	private Float mValue;
	//报警个数（针对一个公厕的）
	private Integer count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassesName() {
		return classesName;
	}
	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManageUnitName() {
		return manageUnitName;
	}
	public void setManageUnitName(String manageUnitName) {
		this.manageUnitName = manageUnitName;
	}
	public Float getaValue() {
		return aValue;
	}
	public void setaValue(Float aValue) {
		this.aValue = aValue;
	}
	public Float getmValue() {
		return mValue;
	}
	public void setmValue(Float mValue) {
		this.mValue = mValue;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aValue == null) ? 0 : aValue.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((classesName == null) ? 0 : classesName.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mValue == null) ? 0 : mValue.hashCode());
		result = prime * result + ((manageUnitName == null) ? 0 : manageUnitName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FtInteWcOdor other = (FtInteWcOdor) obj;
		if (aValue == null) {
			if (other.aValue != null)
				return false;
		} else if (!aValue.equals(other.aValue))
			return false;
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
		if (mValue == null) {
			if (other.mValue != null)
				return false;
		} else if (!mValue.equals(other.mValue))
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
		return true;
	}
	@Override
	public String toString() {
		return "FtInteWcOdor [id=" + id + ", code=" + code + ", name=" + name + ", classesName=" + classesName
				+ ", address=" + address + ", manageUnitName=" + manageUnitName + ", aValue=" + aValue + ", mValue="
				+ mValue + ", count=" + count + "]";
	}
	
	
	
}
