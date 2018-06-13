package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 硚口环卫-report - 查询车辆油耗单位分析
 * @author Administrator
 *
 */
public class FthwReportOilAnalysisData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219023586580221029L;
	
//	//列名
//	private List<String> columnNames;
//	//数据
//	private List<Object> rows;
	
	//车牌号
	private String carCode;
	//司机姓名
	private String driver;
	//单位
	private String companyName;
	//总油耗
	private Double sumOilUse;
	//车型
	private String carGradeName;
	//车类型
	private String carClassesName;
	//总时长
	private Double sumTime;
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getSumOilUse() {
		return sumOilUse;
	}
	public void setSumOilUse(Double sumOilUse) {
		this.sumOilUse = sumOilUse;
	}
	public String getCarGradeName() {
		return carGradeName;
	}
	public void setCarGradeName(String carGradeName) {
		this.carGradeName = carGradeName;
	}
	public String getCarClassesName() {
		return carClassesName;
	}
	public void setCarClassesName(String carClassesName) {
		this.carClassesName = carClassesName;
	}
	public Double getSumTime() {
		return sumTime;
	}
	public void setSumTime(Double sumTime) {
		this.sumTime = sumTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carClassesName == null) ? 0 : carClassesName.hashCode());
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carGradeName == null) ? 0 : carGradeName.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((sumOilUse == null) ? 0 : sumOilUse.hashCode());
		result = prime * result + ((sumTime == null) ? 0 : sumTime.hashCode());
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
		FthwReportOilAnalysisData other = (FthwReportOilAnalysisData) obj;
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
		if (carGradeName == null) {
			if (other.carGradeName != null)
				return false;
		} else if (!carGradeName.equals(other.carGradeName))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (sumOilUse == null) {
			if (other.sumOilUse != null)
				return false;
		} else if (!sumOilUse.equals(other.sumOilUse))
			return false;
		if (sumTime == null) {
			if (other.sumTime != null)
				return false;
		} else if (!sumTime.equals(other.sumTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WhgfReportOilAnalysisData [carCode=" + carCode + ", driver=" + driver + ", companyName=" + companyName
				+ ", sumOilUse=" + sumOilUse + ", carGradeName=" + carGradeName + ", carClassesName=" + carClassesName
				+ ", sumTime=" + sumTime + "]";
	}

	
	
	
	
	
	
	
}
