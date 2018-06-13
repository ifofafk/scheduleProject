package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * 智能公厕实时数据 人流/臭气 实体
 * @author Administrator
 *
 */
public class FtInteWcRealtimeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4683662637744572381L;
	//数据值
	private String value;
	//公厕名称
	private String gcName;
	//公厕编码
	private String gcCode;
	//开始时间
	private Long startDate;
	//结束时间
	private Long endDate;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGcName() {
		return gcName;
	}
	public void setGcName(String gcName) {
		this.gcName = gcName;
	}
	public String getGcCode() {
		return gcCode;
	}
	public void setGcCode(String gcCode) {
		this.gcCode = gcCode;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((gcCode == null) ? 0 : gcCode.hashCode());
		result = prime * result + ((gcName == null) ? 0 : gcName.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		FtInteWcRealtimeData other = (FtInteWcRealtimeData) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (gcCode == null) {
			if (other.gcCode != null)
				return false;
		} else if (!gcCode.equals(other.gcCode))
			return false;
		if (gcName == null) {
			if (other.gcName != null)
				return false;
		} else if (!gcName.equals(other.gcName))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
}
