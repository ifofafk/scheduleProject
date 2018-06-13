package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;
/**
 * 查询车辆历史位置返回实体
 * @author Administrator
 *
 */
public class FtCarHistoryPosData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5853542404430414651L;
	
	private String carId;
	private String equipmentTime;
	private Integer latitudeDone;//偏转后纬度
	private Integer longitudeDone;//偏转后经度
	//车牌号
	private String carCode;
	private Integer longitude;//偏转前经度
	private Integer latitude;//偏转前纬度
	private String carStatus;//车辆状态
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getEquipmentTime() {
		return equipmentTime;
	}
	public void setEquipmentTime(String equipmentTime) {
		this.equipmentTime = equipmentTime;
	}
	public Integer getLatitudeDone() {
		return latitudeDone;
	}
	public void setLatitudeDone(Integer latitudeDone) {
		this.latitudeDone = latitudeDone;
	}
	public Integer getLongitudeDone() {
		return longitudeDone;
	}
	public void setLongitudeDone(Integer longitudeDone) {
		this.longitudeDone = longitudeDone;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public Integer getLongitude() {
		return longitude;
	}
	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}
	public Integer getLatitude() {
		return latitude;
	}
	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}
	public String getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carId == null) ? 0 : carId.hashCode());
		result = prime * result + ((carStatus == null) ? 0 : carStatus.hashCode());
		result = prime * result + ((equipmentTime == null) ? 0 : equipmentTime.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((latitudeDone == null) ? 0 : latitudeDone.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((longitudeDone == null) ? 0 : longitudeDone.hashCode());
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
		FtCarHistoryPosData other = (FtCarHistoryPosData) obj;
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
		if (carStatus == null) {
			if (other.carStatus != null)
				return false;
		} else if (!carStatus.equals(other.carStatus))
			return false;
		if (equipmentTime == null) {
			if (other.equipmentTime != null)
				return false;
		} else if (!equipmentTime.equals(other.equipmentTime))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (latitudeDone == null) {
			if (other.latitudeDone != null)
				return false;
		} else if (!latitudeDone.equals(other.latitudeDone))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (longitudeDone == null) {
			if (other.longitudeDone != null)
				return false;
		} else if (!longitudeDone.equals(other.longitudeDone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FtCarHistoryPosData [carId=" + carId + ", equipmentTime=" + equipmentTime + ", latitudeDone="
				+ latitudeDone + ", longitudeDone=" + longitudeDone + ", carCode=" + carCode + ", longitude="
				+ longitude + ", latitude=" + latitude + ", carStatus=" + carStatus + "]";
	}
	
	
	
	
	
	

}
