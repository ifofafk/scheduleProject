package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
 * getCarHistoryOilData的返回实体
 * 查询车辆历史油耗的返回实体
 * @author wangchen
 *
 */
public class FtCarHistoryOilData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1214494283286877297L;
	
	private Long equipmentTime;
	private Double oilLevel;
	public Long getEquipmentTime() {
		return equipmentTime;
	}
	public void setEquipmentTime(Long equipmentTime) {
		this.equipmentTime = equipmentTime;
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
		result = prime * result + ((equipmentTime == null) ? 0 : equipmentTime.hashCode());
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
		FtCarHistoryOilData other = (FtCarHistoryOilData) obj;
		if (equipmentTime == null) {
			if (other.equipmentTime != null)
				return false;
		} else if (!equipmentTime.equals(other.equipmentTime))
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
		return "FtCarHistoryOilData [equipmentTime=" + equipmentTime + ", oilLevel=" + oilLevel + "]";
	}
	
	
	
	
}
