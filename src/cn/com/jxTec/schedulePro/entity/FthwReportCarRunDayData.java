package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 硚口环卫-report - 查询运行日报表记录
 * @author Administrator
 *
 */
public class FthwReportCarRunDayData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -907218396926268970L;
	
	
	//熄点火次数	 8
	private Integer trackNum;
	//作业单位	 "水车队"
	private String companyName;
	//最早点火时间	 "2018-05-03 00:08:42"
	private String earliestFireTime;
	//停车次数	 7
	private Integer stopNum;
	//最晚点火地址	 "湖北省武汉市江汉区青年路332-附6武汉小贝壳青年旅舍附近45米"
	private String lastestFireAddress;
	//油耗（升）	  15.3
	private Double allUseOil;
	//车牌号	 "鄂ARK037"
	private String carCode;
	//累计运行时间（秒）	  7463589.0
	private BigDecimal allRunTime;
	//停车时长（秒）	  77623
	private BigDecimal stopTime;
	//最早点火位置	  "湖北省武汉市江汉区利济北路246号万松街地质社区-西门附近17米"
	private String earliestFireAddress;
	//最晚点火时间	  "2018-05-03 23:50:26"
	private String lastestFireTime;
	//日期	  "2018-05-03"
	private String runDay;
	//里程	47.57
	private Double mileage;
	public Integer getTrackNum() {
		return trackNum;
	}
	public void setTrackNum(Integer trackNum) {
		this.trackNum = trackNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEarliestFireTime() {
		return earliestFireTime;
	}
	public void setEarliestFireTime(String earliestFireTime) {
		this.earliestFireTime = earliestFireTime;
	}
	public Integer getStopNum() {
		return stopNum;
	}
	public void setStopNum(Integer stopNum) {
		this.stopNum = stopNum;
	}
	public String getLastestFireAddress() {
		return lastestFireAddress;
	}
	public void setLastestFireAddress(String lastestFireAddress) {
		this.lastestFireAddress = lastestFireAddress;
	}
	public Double getAllUseOil() {
		return allUseOil;
	}
	public void setAllUseOil(Double allUseOil) {
		this.allUseOil = allUseOil;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public BigDecimal getAllRunTime() {
		return allRunTime;
	}
	public void setAllRunTime(BigDecimal allRunTime) {
		this.allRunTime = allRunTime;
	}
	public BigDecimal getStopTime() {
		return stopTime;
	}
	public void setStopTime(BigDecimal stopTime) {
		this.stopTime = stopTime;
	}
	public String getEarliestFireAddress() {
		return earliestFireAddress;
	}
	public void setEarliestFireAddress(String earliestFireAddress) {
		this.earliestFireAddress = earliestFireAddress;
	}
	public String getLastestFireTime() {
		return lastestFireTime;
	}
	public void setLastestFireTime(String lastestFireTime) {
		this.lastestFireTime = lastestFireTime;
	}
	public String getRunDay() {
		return runDay;
	}
	public void setRunDay(String runDay) {
		this.runDay = runDay;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allRunTime == null) ? 0 : allRunTime.hashCode());
		result = prime * result + ((allUseOil == null) ? 0 : allUseOil.hashCode());
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((earliestFireAddress == null) ? 0 : earliestFireAddress.hashCode());
		result = prime * result + ((earliestFireTime == null) ? 0 : earliestFireTime.hashCode());
		result = prime * result + ((lastestFireAddress == null) ? 0 : lastestFireAddress.hashCode());
		result = prime * result + ((lastestFireTime == null) ? 0 : lastestFireTime.hashCode());
		result = prime * result + ((mileage == null) ? 0 : mileage.hashCode());
		result = prime * result + ((runDay == null) ? 0 : runDay.hashCode());
		result = prime * result + ((stopNum == null) ? 0 : stopNum.hashCode());
		result = prime * result + ((stopTime == null) ? 0 : stopTime.hashCode());
		result = prime * result + ((trackNum == null) ? 0 : trackNum.hashCode());
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
		FthwReportCarRunDayData other = (FthwReportCarRunDayData) obj;
		if (allRunTime == null) {
			if (other.allRunTime != null)
				return false;
		} else if (!allRunTime.equals(other.allRunTime))
			return false;
		if (allUseOil == null) {
			if (other.allUseOil != null)
				return false;
		} else if (!allUseOil.equals(other.allUseOil))
			return false;
		if (carCode == null) {
			if (other.carCode != null)
				return false;
		} else if (!carCode.equals(other.carCode))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (earliestFireAddress == null) {
			if (other.earliestFireAddress != null)
				return false;
		} else if (!earliestFireAddress.equals(other.earliestFireAddress))
			return false;
		if (earliestFireTime == null) {
			if (other.earliestFireTime != null)
				return false;
		} else if (!earliestFireTime.equals(other.earliestFireTime))
			return false;
		if (lastestFireAddress == null) {
			if (other.lastestFireAddress != null)
				return false;
		} else if (!lastestFireAddress.equals(other.lastestFireAddress))
			return false;
		if (lastestFireTime == null) {
			if (other.lastestFireTime != null)
				return false;
		} else if (!lastestFireTime.equals(other.lastestFireTime))
			return false;
		if (mileage == null) {
			if (other.mileage != null)
				return false;
		} else if (!mileage.equals(other.mileage))
			return false;
		if (runDay == null) {
			if (other.runDay != null)
				return false;
		} else if (!runDay.equals(other.runDay))
			return false;
		if (stopNum == null) {
			if (other.stopNum != null)
				return false;
		} else if (!stopNum.equals(other.stopNum))
			return false;
		if (stopTime == null) {
			if (other.stopTime != null)
				return false;
		} else if (!stopTime.equals(other.stopTime))
			return false;
		if (trackNum == null) {
			if (other.trackNum != null)
				return false;
		} else if (!trackNum.equals(other.trackNum))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WhgfReportCarRunDayData [trackNum=" + trackNum + ", companyName=" + companyName + ", earliestFireTime="
				+ earliestFireTime + ", stopNum=" + stopNum + ", lastestFireAddress=" + lastestFireAddress
				+ ", allUseOil=" + allUseOil + ", carCode=" + carCode + ", allRunTime=" + allRunTime + ", stopTime="
				+ stopTime + ", earliestFireAddress=" + earliestFireAddress + ", lastestFireTime=" + lastestFireTime
				+ ", runDay=" + runDay + ", mileage=" + mileage + "]";
	}
	
	
	
	
}
