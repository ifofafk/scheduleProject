package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 查询车辆报警历史记录的返回实体
 * @author Administrator
 *
 */
public class FtCarHistoryAlarmData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219023586580221029L;

	private String alarmAddress;
	private String alarmBeginTime;
	private String alarmEndTime;
	private String alarmLevel;
	private String alarmLevelId;
	private String alarmLevelName;
	private String alarmType;
	private String alarmTypeName;
	private Boolean beenDo;
	private String carId;
	private String carNo;
	private String carTypeId;
	private String carTypeName;
	private String id;
	private Double positionSpeed;
	private Double maxSpeed;
	public String getAlarmAddress() {
		return alarmAddress;
	}
	public void setAlarmAddress(String alarmAddress) {
		this.alarmAddress = alarmAddress;
	}
	public String getAlarmBeginTime() {
		return alarmBeginTime;
	}
	public void setAlarmBeginTime(String alarmBeginTime) {
		this.alarmBeginTime = alarmBeginTime;
	}
	public String getAlarmEndTime() {
		return alarmEndTime;
	}
	public void setAlarmEndTime(String alarmEndTime) {
		this.alarmEndTime = alarmEndTime;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getAlarmLevelId() {
		return alarmLevelId;
	}
	public void setAlarmLevelId(String alarmLevelId) {
		this.alarmLevelId = alarmLevelId;
	}
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmTypeName() {
		return alarmTypeName;
	}
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}
	public Boolean getBeenDo() {
		return beenDo;
	}
	public void setBeenDo(Boolean beenDo) {
		this.beenDo = beenDo;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarTypeId() {
		return carTypeId;
	}
	public void setCarTypeId(String carTypeId) {
		this.carTypeId = carTypeId;
	}
	public String getCarTypeName() {
		return carTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getPositionSpeed() {
		return positionSpeed;
	}
	public void setPositionSpeed(Double positionSpeed) {
		this.positionSpeed = positionSpeed;
	}
	public Double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarmAddress == null) ? 0 : alarmAddress.hashCode());
		result = prime * result + ((alarmBeginTime == null) ? 0 : alarmBeginTime.hashCode());
		result = prime * result + ((alarmEndTime == null) ? 0 : alarmEndTime.hashCode());
		result = prime * result + ((alarmLevel == null) ? 0 : alarmLevel.hashCode());
		result = prime * result + ((alarmLevelId == null) ? 0 : alarmLevelId.hashCode());
		result = prime * result + ((alarmLevelName == null) ? 0 : alarmLevelName.hashCode());
		result = prime * result + ((alarmType == null) ? 0 : alarmType.hashCode());
		result = prime * result + ((alarmTypeName == null) ? 0 : alarmTypeName.hashCode());
		result = prime * result + ((beenDo == null) ? 0 : beenDo.hashCode());
		result = prime * result + ((carId == null) ? 0 : carId.hashCode());
		result = prime * result + ((carNo == null) ? 0 : carNo.hashCode());
		result = prime * result + ((carTypeId == null) ? 0 : carTypeId.hashCode());
		result = prime * result + ((carTypeName == null) ? 0 : carTypeName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxSpeed == null) ? 0 : maxSpeed.hashCode());
		result = prime * result + ((positionSpeed == null) ? 0 : positionSpeed.hashCode());
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
		FtCarHistoryAlarmData other = (FtCarHistoryAlarmData) obj;
		if (alarmAddress == null) {
			if (other.alarmAddress != null)
				return false;
		} else if (!alarmAddress.equals(other.alarmAddress))
			return false;
		if (alarmBeginTime == null) {
			if (other.alarmBeginTime != null)
				return false;
		} else if (!alarmBeginTime.equals(other.alarmBeginTime))
			return false;
		if (alarmEndTime == null) {
			if (other.alarmEndTime != null)
				return false;
		} else if (!alarmEndTime.equals(other.alarmEndTime))
			return false;
		if (alarmLevel == null) {
			if (other.alarmLevel != null)
				return false;
		} else if (!alarmLevel.equals(other.alarmLevel))
			return false;
		if (alarmLevelId == null) {
			if (other.alarmLevelId != null)
				return false;
		} else if (!alarmLevelId.equals(other.alarmLevelId))
			return false;
		if (alarmLevelName == null) {
			if (other.alarmLevelName != null)
				return false;
		} else if (!alarmLevelName.equals(other.alarmLevelName))
			return false;
		if (alarmType == null) {
			if (other.alarmType != null)
				return false;
		} else if (!alarmType.equals(other.alarmType))
			return false;
		if (alarmTypeName == null) {
			if (other.alarmTypeName != null)
				return false;
		} else if (!alarmTypeName.equals(other.alarmTypeName))
			return false;
		if (beenDo == null) {
			if (other.beenDo != null)
				return false;
		} else if (!beenDo.equals(other.beenDo))
			return false;
		if (carId == null) {
			if (other.carId != null)
				return false;
		} else if (!carId.equals(other.carId))
			return false;
		if (carNo == null) {
			if (other.carNo != null)
				return false;
		} else if (!carNo.equals(other.carNo))
			return false;
		if (carTypeId == null) {
			if (other.carTypeId != null)
				return false;
		} else if (!carTypeId.equals(other.carTypeId))
			return false;
		if (carTypeName == null) {
			if (other.carTypeName != null)
				return false;
		} else if (!carTypeName.equals(other.carTypeName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxSpeed == null) {
			if (other.maxSpeed != null)
				return false;
		} else if (!maxSpeed.equals(other.maxSpeed))
			return false;
		if (positionSpeed == null) {
			if (other.positionSpeed != null)
				return false;
		} else if (!positionSpeed.equals(other.positionSpeed))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FtCarHistoryAlarmData [alarmAddress=" + alarmAddress + ", alarmBeginTime=" + alarmBeginTime
				+ ", alarmEndTime=" + alarmEndTime + ", alarmLevel=" + alarmLevel + ", alarmLevelId=" + alarmLevelId
				+ ", alarmLevelName=" + alarmLevelName + ", alarmType=" + alarmType + ", alarmTypeName=" + alarmTypeName
				+ ", beenDo=" + beenDo + ", carId=" + carId + ", carNo=" + carNo + ", carTypeId=" + carTypeId
				+ ", carTypeName=" + carTypeName + ", id=" + id + ", positionSpeed=" + positionSpeed + ", maxSpeed="
				+ maxSpeed + "]";
	}
	
	
	
	
	
}
