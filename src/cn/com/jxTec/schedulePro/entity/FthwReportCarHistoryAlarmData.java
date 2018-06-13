package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 固废系统-report - 查询车辆报警历史记录的返回实体
 * @author Administrator
 *
 */
public class FthwReportCarHistoryAlarmData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219023586580221029L;
	
//	//列名
//	private List<String> columnNames;
//	//数据
//	private List<Object> rows;
	//报警开始位置
	private String beginAddress;
	//报警结束位置
	private String endAddress;
	//车辆类型
	private String carTypeName;
	//限速阈值（公里/小时）
	private String speedLimitingValveSize;
	//里程(千米)
	private String distance;
	//图元名称
	private String workElementName;
	//作业单位
	private String companyName;
	//最高时速(公里/小时)
	private Double maxSpeed;
	//报警等级(怎么文档写的boolean)
	private Boolean alarmLevelName;
	//平均速度(公里/小时)
	private String avgSpeed;
	//报警结束时间 yyyy-MM-dd HH:mm:ss
	private String alarmEndTime;
	//车牌号
	private String carCode;
	//报警开始时间 yyyy-MM-dd HH:mm:ss
	private String alarmBeginTime;
	//累计超速时长ms
	private Long overtime;
	//司机姓名
	private String driverName;
	//司机联系方式
	private String driverPhone;
	//规定停车时长()
	private Long stipulatedTime;
//	public List<String> getColumnNames() {
//		return columnNames;
//	}
//	public void setColumnNames(List<String> columnNames) {
//		this.columnNames = columnNames;
//	}
//	public List<Object> getRows() {
//		return rows;
//	}
//	public void setRows(List<Object> rows) {
//		this.rows = rows;
//	}
	public String getBeginAddress() {
		return beginAddress;
	}
	public void setBeginAddress(String beginAddress) {
		this.beginAddress = beginAddress;
	}
	public String getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	public String getCarTypeName() {
		return carTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}
	public String getSpeedLimitingValveSize() {
		return speedLimitingValveSize;
	}
	public void setSpeedLimitingValveSize(String speedLimitingValveSize) {
		this.speedLimitingValveSize = speedLimitingValveSize;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getWorkElementName() {
		return workElementName;
	}
	public void setWorkElementName(String workElementName) {
		this.workElementName = workElementName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public Boolean getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(Boolean alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public String getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public String getAlarmEndTime() {
		return alarmEndTime;
	}
	public void setAlarmEndTime(String alarmEndTime) {
		this.alarmEndTime = alarmEndTime;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getAlarmBeginTime() {
		return alarmBeginTime;
	}
	public void setAlarmBeginTime(String alarmBeginTime) {
		this.alarmBeginTime = alarmBeginTime;
	}
	public Long getOvertime() {
		return overtime;
	}
	public void setOvertime(Long overtime) {
		this.overtime = overtime;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public Long getStipulatedTime() {
		return stipulatedTime;
	}
	public void setStipulatedTime(Long stipulatedTime) {
		this.stipulatedTime = stipulatedTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarmBeginTime == null) ? 0 : alarmBeginTime.hashCode());
		result = prime * result + ((alarmEndTime == null) ? 0 : alarmEndTime.hashCode());
		result = prime * result + ((alarmLevelName == null) ? 0 : alarmLevelName.hashCode());
		result = prime * result + ((avgSpeed == null) ? 0 : avgSpeed.hashCode());
		result = prime * result + ((beginAddress == null) ? 0 : beginAddress.hashCode());
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carTypeName == null) ? 0 : carTypeName.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
		result = prime * result + ((driverPhone == null) ? 0 : driverPhone.hashCode());
		result = prime * result + ((endAddress == null) ? 0 : endAddress.hashCode());
		result = prime * result + ((maxSpeed == null) ? 0 : maxSpeed.hashCode());
		result = prime * result + ((overtime == null) ? 0 : overtime.hashCode());
		result = prime * result + ((speedLimitingValveSize == null) ? 0 : speedLimitingValveSize.hashCode());
		result = prime * result + ((stipulatedTime == null) ? 0 : stipulatedTime.hashCode());
		result = prime * result + ((workElementName == null) ? 0 : workElementName.hashCode());
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
		FthwReportCarHistoryAlarmData other = (FthwReportCarHistoryAlarmData) obj;
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
		if (alarmLevelName == null) {
			if (other.alarmLevelName != null)
				return false;
		} else if (!alarmLevelName.equals(other.alarmLevelName))
			return false;
		if (avgSpeed == null) {
			if (other.avgSpeed != null)
				return false;
		} else if (!avgSpeed.equals(other.avgSpeed))
			return false;
		if (beginAddress == null) {
			if (other.beginAddress != null)
				return false;
		} else if (!beginAddress.equals(other.beginAddress))
			return false;
		if (carCode == null) {
			if (other.carCode != null)
				return false;
		} else if (!carCode.equals(other.carCode))
			return false;
		if (carTypeName == null) {
			if (other.carTypeName != null)
				return false;
		} else if (!carTypeName.equals(other.carTypeName))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		if (driverPhone == null) {
			if (other.driverPhone != null)
				return false;
		} else if (!driverPhone.equals(other.driverPhone))
			return false;
		if (endAddress == null) {
			if (other.endAddress != null)
				return false;
		} else if (!endAddress.equals(other.endAddress))
			return false;
		if (maxSpeed == null) {
			if (other.maxSpeed != null)
				return false;
		} else if (!maxSpeed.equals(other.maxSpeed))
			return false;
		if (overtime == null) {
			if (other.overtime != null)
				return false;
		} else if (!overtime.equals(other.overtime))
			return false;
		if (speedLimitingValveSize == null) {
			if (other.speedLimitingValveSize != null)
				return false;
		} else if (!speedLimitingValveSize.equals(other.speedLimitingValveSize))
			return false;
		if (stipulatedTime == null) {
			if (other.stipulatedTime != null)
				return false;
		} else if (!stipulatedTime.equals(other.stipulatedTime))
			return false;
		if (workElementName == null) {
			if (other.workElementName != null)
				return false;
		} else if (!workElementName.equals(other.workElementName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WhgfReportCarHistoryAlarmData [beginAddress=" + beginAddress + ", endAddress=" + endAddress
				+ ", carTypeName=" + carTypeName + ", speedLimitingValveSize=" + speedLimitingValveSize + ", distance="
				+ distance + ", workElementName=" + workElementName + ", companyName=" + companyName + ", maxSpeed="
				+ maxSpeed + ", alarmLevelName=" + alarmLevelName + ", avgSpeed=" + avgSpeed + ", alarmEndTime="
				+ alarmEndTime + ", carCode=" + carCode + ", alarmBeginTime=" + alarmBeginTime + ", overtime="
				+ overtime + ", driverName=" + driverName + ", driverPhone=" + driverPhone + ", stipulatedTime="
				+ stipulatedTime + "]";
	}
	
	
	
	
	
}
