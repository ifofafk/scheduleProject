package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 车辆实时位置的返回实体
 * @author Administrator
 *
 */
public class FtCarRealtimePosData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5853542404430414651L;
	
	private String carId;
	private Double latitudeDone;
	private Double longitudeDone;
	private String carCode;
	private Double longitude;
	private Double latitude;
	private String carStatus;//车辆状态
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Double getLatitudeDone() {
		return latitudeDone;
	}
	public void setLatitudeDone(Double latitudeDone) {
		this.latitudeDone = latitudeDone;
	}
	public Double getLongitudeDone() {
		return longitudeDone;
	}
	public void setLongitudeDone(Double longitudeDone) {
		this.longitudeDone = longitudeDone;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
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
		FtCarRealtimePosData other = (FtCarRealtimePosData) obj;
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
		return "FtCarRealtimePosData [carId=" + carId + ", latitudeDone=" + latitudeDone + ", longitudeDone="
				+ longitudeDone + ", carCode=" + carCode + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", carStatus=" + carStatus + "]";
	}
	
	

}
