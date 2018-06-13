package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年4月26日 下午9:33:07
* 类说明  车辆信息实体
*/
public class FtCarInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728253150044012022L;
	
	
	private String carId;
	private String carCode;
	private String groupCode;
	private String carClassesId;
	private String carClassesCode;
	private String carClassesName;
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getCarClassesId() {
		return carClassesId;
	}
	public void setCarClassesId(String carClassesId) {
		this.carClassesId = carClassesId;
	}
	public String getCarClassesCode() {
		return carClassesCode;
	}
	public void setCarClassesCode(String carClassesCode) {
		this.carClassesCode = carClassesCode;
	}
	public String getCarClassesName() {
		return carClassesName;
	}
	public void setCarClassesName(String carClassesName) {
		this.carClassesName = carClassesName;
	}
	@Override
	public String toString() {
		return "FtCarInfo [carId=" + carId + ", carCode=" + carCode + ", groupCode=" + groupCode + ", carClassesId="
				+ carClassesId + ", carClassesCode=" + carClassesCode + ", carClassesName=" + carClassesName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carClassesCode == null) ? 0 : carClassesCode.hashCode());
		result = prime * result + ((carClassesId == null) ? 0 : carClassesId.hashCode());
		result = prime * result + ((carClassesName == null) ? 0 : carClassesName.hashCode());
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carId == null) ? 0 : carId.hashCode());
		result = prime * result + ((groupCode == null) ? 0 : groupCode.hashCode());
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
		FtCarInfo other = (FtCarInfo) obj;
		if (carClassesCode == null) {
			if (other.carClassesCode != null)
				return false;
		} else if (!carClassesCode.equals(other.carClassesCode))
			return false;
		if (carClassesId == null) {
			if (other.carClassesId != null)
				return false;
		} else if (!carClassesId.equals(other.carClassesId))
			return false;
		if (carClassesName == null) {
			if (other.carClassesName != null)
				return false;
		} else if (!carClassesName.equals(other.carClassesName))
			return false;
		if (carCode == null) {
			if (other.carCode != null)
				return false;
		} else if (!carCode.equals(other.carCode))
			return false;
		if (carId == null) {
			if (other.carId != null)
				return false;
		} else if (!carId.equals(other.carId))
			return false;
		if (groupCode == null) {
			if (other.groupCode != null)
				return false;
		} else if (!groupCode.equals(other.groupCode))
			return false;
		return true;
	}
	
	
	
	
}
