package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月10日 下午4:44:50 类说明
 */
public class FtQjyRealtimeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2513733118545808497L;
	//伏泰清洁员-ID
	private String staffId;
	//伏泰清洁员-姓名
	private String staffName;
	//伏泰清洁员-电话
	private String staffPhone;
	//伏泰清洁员-用工类型编码
	private String workTypeCode;
	//伏泰清洁员-用工类型名称
	private String workTypeName;
	//伏泰清洁员-所属机构id
	private String deptId;
	//伏泰清洁员-所属机构名称
	private String deptName;
	//伏泰清洁员-设备类型编码   Bracelet=手环，Mobile=手机
	private String deviceTypeCode;
	//伏泰清洁员-设备类型名称
	private String deviceTypeName;
	//伏泰清洁员-上点时间   毫秒数
	private Long gpsTime;
	//伏泰清洁员-偏转经度
	private Double lngDone;
	//伏泰清洁员-偏转纬度
	private Double latDone;
	//伏泰清洁员-地址
	private String address;
	//伏泰清洁员-步数
	private Long steps;
	//伏泰清洁员-心率       次/min
	private Integer heartRate;
	//伏泰清洁员-班次ID
	private String shiftId;
	//伏泰清洁员-班次名称
	private String shiftName;
	//伏泰清洁员-班次开始时间    毫秒
	private Long shiftStartTime;
	//伏泰清洁员-班次结束时间   毫秒
	private Long shiftEndTime;
	//伏泰清洁员-排班区段名称集合
	private String shiftWorkElementNames;
	//伏泰清洁员-实际所在区域ID
	private String onWorkElementId;
	//伏泰清洁员-实际所在区域名称
	private String onWorkElementName;
	//伏泰清洁员-状态编码   InPost=在岗，OutPost=离岗，OffLine=离线，OnLine=在线 ，Rest=休息，SOS=SOS
	private String statusCode;
	//伏泰清洁员-状态名称
	private String statusName;
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffPhone() {
		return staffPhone;
	}
	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeviceTypeCode() {
		return deviceTypeCode;
	}
	public void setDeviceTypeCode(String deviceTypeCode) {
		this.deviceTypeCode = deviceTypeCode;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public Long getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(Long gpsTime) {
		this.gpsTime = gpsTime;
	}
	public Double getLngDone() {
		return lngDone;
	}
	public void setLngDone(Double lngDone) {
		this.lngDone = lngDone;
	}
	public Double getLatDone() {
		return latDone;
	}
	public void setLatDone(Double latDone) {
		this.latDone = latDone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getSteps() {
		return steps;
	}
	public void setSteps(Long steps) {
		this.steps = steps;
	}
	public Integer getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public Long getShiftStartTime() {
		return shiftStartTime;
	}
	public void setShiftStartTime(Long shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}
	public Long getShiftEndTime() {
		return shiftEndTime;
	}
	public void setShiftEndTime(Long shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}
	public String getShiftWorkElementNames() {
		return shiftWorkElementNames;
	}
	public void setShiftWorkElementNames(String shiftWorkElementNames) {
		this.shiftWorkElementNames = shiftWorkElementNames;
	}
	public String getOnWorkElementId() {
		return onWorkElementId;
	}
	public void setOnWorkElementId(String onWorkElementId) {
		this.onWorkElementId = onWorkElementId;
	}
	public String getOnWorkElementName() {
		return onWorkElementName;
	}
	public void setOnWorkElementName(String onWorkElementName) {
		this.onWorkElementName = onWorkElementName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + ((deviceTypeCode == null) ? 0 : deviceTypeCode.hashCode());
		result = prime * result + ((deviceTypeName == null) ? 0 : deviceTypeName.hashCode());
		result = prime * result + ((gpsTime == null) ? 0 : gpsTime.hashCode());
		result = prime * result + ((heartRate == null) ? 0 : heartRate.hashCode());
		result = prime * result + ((latDone == null) ? 0 : latDone.hashCode());
		result = prime * result + ((lngDone == null) ? 0 : lngDone.hashCode());
		result = prime * result + ((onWorkElementId == null) ? 0 : onWorkElementId.hashCode());
		result = prime * result + ((onWorkElementName == null) ? 0 : onWorkElementName.hashCode());
		result = prime * result + ((shiftEndTime == null) ? 0 : shiftEndTime.hashCode());
		result = prime * result + ((shiftId == null) ? 0 : shiftId.hashCode());
		result = prime * result + ((shiftName == null) ? 0 : shiftName.hashCode());
		result = prime * result + ((shiftStartTime == null) ? 0 : shiftStartTime.hashCode());
		result = prime * result + ((shiftWorkElementNames == null) ? 0 : shiftWorkElementNames.hashCode());
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		result = prime * result + ((staffName == null) ? 0 : staffName.hashCode());
		result = prime * result + ((staffPhone == null) ? 0 : staffPhone.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
		result = prime * result + ((steps == null) ? 0 : steps.hashCode());
		result = prime * result + ((workTypeCode == null) ? 0 : workTypeCode.hashCode());
		result = prime * result + ((workTypeName == null) ? 0 : workTypeName.hashCode());
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
		FtQjyRealtimeData other = (FtQjyRealtimeData) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (deviceTypeCode == null) {
			if (other.deviceTypeCode != null)
				return false;
		} else if (!deviceTypeCode.equals(other.deviceTypeCode))
			return false;
		if (deviceTypeName == null) {
			if (other.deviceTypeName != null)
				return false;
		} else if (!deviceTypeName.equals(other.deviceTypeName))
			return false;
		if (gpsTime == null) {
			if (other.gpsTime != null)
				return false;
		} else if (!gpsTime.equals(other.gpsTime))
			return false;
		if (heartRate == null) {
			if (other.heartRate != null)
				return false;
		} else if (!heartRate.equals(other.heartRate))
			return false;
		if (latDone == null) {
			if (other.latDone != null)
				return false;
		} else if (!latDone.equals(other.latDone))
			return false;
		if (lngDone == null) {
			if (other.lngDone != null)
				return false;
		} else if (!lngDone.equals(other.lngDone))
			return false;
		if (onWorkElementId == null) {
			if (other.onWorkElementId != null)
				return false;
		} else if (!onWorkElementId.equals(other.onWorkElementId))
			return false;
		if (onWorkElementName == null) {
			if (other.onWorkElementName != null)
				return false;
		} else if (!onWorkElementName.equals(other.onWorkElementName))
			return false;
		if (shiftEndTime == null) {
			if (other.shiftEndTime != null)
				return false;
		} else if (!shiftEndTime.equals(other.shiftEndTime))
			return false;
		if (shiftId == null) {
			if (other.shiftId != null)
				return false;
		} else if (!shiftId.equals(other.shiftId))
			return false;
		if (shiftName == null) {
			if (other.shiftName != null)
				return false;
		} else if (!shiftName.equals(other.shiftName))
			return false;
		if (shiftStartTime == null) {
			if (other.shiftStartTime != null)
				return false;
		} else if (!shiftStartTime.equals(other.shiftStartTime))
			return false;
		if (shiftWorkElementNames == null) {
			if (other.shiftWorkElementNames != null)
				return false;
		} else if (!shiftWorkElementNames.equals(other.shiftWorkElementNames))
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
		if (staffPhone == null) {
			if (other.staffPhone != null)
				return false;
		} else if (!staffPhone.equals(other.staffPhone))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		if (statusName == null) {
			if (other.statusName != null)
				return false;
		} else if (!statusName.equals(other.statusName))
			return false;
		if (steps == null) {
			if (other.steps != null)
				return false;
		} else if (!steps.equals(other.steps))
			return false;
		if (workTypeCode == null) {
			if (other.workTypeCode != null)
				return false;
		} else if (!workTypeCode.equals(other.workTypeCode))
			return false;
		if (workTypeName == null) {
			if (other.workTypeName != null)
				return false;
		} else if (!workTypeName.equals(other.workTypeName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FtQjyRealtimeData [staffId=" + staffId + ", staffName=" + staffName + ", staffPhone=" + staffPhone
				+ ", workTypeCode=" + workTypeCode + ", workTypeName=" + workTypeName + ", deptId=" + deptId
				+ ", deptName=" + deptName + ", deviceTypeCode=" + deviceTypeCode + ", deviceTypeName=" + deviceTypeName
				+ ", gpsTime=" + gpsTime + ", lngDone=" + lngDone + ", latDone=" + latDone + ", address=" + address
				+ ", steps=" + steps + ", heartRate=" + heartRate + ", shiftId=" + shiftId + ", shiftName=" + shiftName
				+ ", shiftStartTime=" + shiftStartTime + ", shiftEndTime=" + shiftEndTime + ", shiftWorkElementNames="
				+ shiftWorkElementNames + ", onWorkElementId=" + onWorkElementId + ", onWorkElementName="
				+ onWorkElementName + ", statusCode=" + statusCode + ", statusName=" + statusName + "]";
	}
	
	
	
}
