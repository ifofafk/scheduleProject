package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * getCarRealtimeOilData接口的返回实体
 * 车辆实时油耗的返回实体
 * @author wangchen
 *
 */
public class FtCarRealtimeOilData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1983318276531180080L;
	private String carId;
	private String carCode;
	private Double oilLevel;
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
	public Double getOilLevel() {
		return oilLevel;
	}
	public void setOilLevel(Double oilLevel) {
		this.oilLevel = oilLevel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carId == null) ? 0 : carId.hashCode());
		result = prime * result + ((oilLevel == null) ? 0 : oilLevel.hashCode());
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
		FtCarRealtimeOilData other = (FtCarRealtimeOilData) obj;
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
		if (oilLevel == null) {
			if (other.oilLevel != null)
				return false;
		} else if (!oilLevel.equals(other.oilLevel))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FtCarRealtimeOilData [carId=" + carId + ", carCode=" + carCode + ", oilLevel=" + oilLevel + "]";
	}
	
	
	
}
