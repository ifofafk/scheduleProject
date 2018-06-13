package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 智能公厕实体
 * @author Administrator
 *
 */
public class FtInteWcInfo implements Serializable {

	
/**
	 * 
	 */
	private static final long serialVersionUID = -295640644654670367L;
//	//公厕id
//	private String id;
//	//公厕编码
//	private String code;
//	//公厕名称
//	private String name;
//	//类型名称
//	private String classesName;
//	//公厕地址
//	private String address;
//	//管理单位
//	private String manageUnitName;
//	//平均浓度
//	private Float aValue;
//	//最高浓度
//	private Float mValue;
//	//报警个数（针对一个公厕的）
//	private Integer count;
	
	//公厕编码
	private String jcssCode;
	//公厕名称
	private String jcssName;
	//描述
	private String description;
	//经纬度： 经纬度之间以,分隔，经度在前、纬度在后
	private String dePositionStr;
	public String getJcssCode() {
		return jcssCode;
	}
	public void setJcssCode(String jcssCode) {
		this.jcssCode = jcssCode;
	}
	public String getJcssName() {
		return jcssName;
	}
	public void setJcssName(String jcssName) {
		this.jcssName = jcssName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDePositionStr() {
		return dePositionStr;
	}
	public void setDePositionStr(String dePositionStr) {
		this.dePositionStr = dePositionStr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dePositionStr == null) ? 0 : dePositionStr.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((jcssCode == null) ? 0 : jcssCode.hashCode());
		result = prime * result + ((jcssName == null) ? 0 : jcssName.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "FtInteWcInfo [jcssCode=" + jcssCode + ", jcssName=" + jcssName + ", description=" + description
				+ ", dePositionStr=" + dePositionStr + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FtInteWcInfo other = (FtInteWcInfo) obj;
		if (dePositionStr == null) {
			if (other.dePositionStr != null)
				return false;
		} else if (!dePositionStr.equals(other.dePositionStr))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (jcssCode == null) {
			if (other.jcssCode != null)
				return false;
		} else if (!jcssCode.equals(other.jcssCode))
			return false;
		if (jcssName == null) {
			if (other.jcssName != null)
				return false;
		} else if (!jcssName.equals(other.jcssName))
			return false;
		return true;
	}

	
	
	
}
