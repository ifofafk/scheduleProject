package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年5月16日 上午9:54:07
* 类说明  清扫作业---路段作业日报
*/
public class FthwReportLDZYRBData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1809642922322564028L;
	
	//路段名称
	private String roadName;	
	//路段类型
	private String roadTypeName;
	//路段长度(公里)
	private Double roadSpace;
	//作业计划-时间段-开始时间
	private String startTime;
	//作业计划-时间段-结束时间
	private String endTime;
	//作业计划-趟数(次/天)
	private Integer times;
	//作业计划-最大速度(公里/小时)
	private Double ruleMaxSpeed;	
	//作业计划-作业里程(公里)	
	private Double ruleDriveSpace;	
	//作业车辆
	private String carCode;
	//作业完成情况-作业时间-开始时间
	private String minEquipmentTime;
	//作业完成情况-作业时间-结束时间
	private String maxEquipmentTime;
	//作业完成情况-作业速度-平均速度(公里/小时)
	private Double avgSpeed;
	//作业完成情况-作业速度-最大速度(公里/小时)
	private Double maxSpeed;
	//作业完成情况-有效趟数(次/天),作业趟数(次/天)
	private Integer validWorkTimes;	
	//作业完成情况-有效里程(公里),作业里程(公里)
	private Double validMileage;	
	//作业完成情况-实际趟数(次/天)
	private Integer realWorkTimes;
	//作业完成情况-实际里程(公里)
	private Double realMileage;
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getRoadTypeName() {
		return roadTypeName;
	}
	public void setRoadTypeName(String roadTypeName) {
		this.roadTypeName = roadTypeName;
	}
	public Double getRoadSpace() {
		return roadSpace;
	}
	public void setRoadSpace(Double roadSpace) {
		this.roadSpace = roadSpace;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Double getRuleMaxSpeed() {
		return ruleMaxSpeed;
	}
	public void setRuleMaxSpeed(Double ruleMaxSpeed) {
		this.ruleMaxSpeed = ruleMaxSpeed;
	}
	public Double getRuleDriveSpace() {
		return ruleDriveSpace;
	}
	public void setRuleDriveSpace(Double ruleDriveSpace) {
		this.ruleDriveSpace = ruleDriveSpace;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getMinEquipmentTime() {
		return minEquipmentTime;
	}
	public void setMinEquipmentTime(String minEquipmentTime) {
		this.minEquipmentTime = minEquipmentTime;
	}
	public String getMaxEquipmentTime() {
		return maxEquipmentTime;
	}
	public void setMaxEquipmentTime(String maxEquipmentTime) {
		this.maxEquipmentTime = maxEquipmentTime;
	}
	public Double getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(Double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public Double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public Integer getValidWorkTimes() {
		return validWorkTimes;
	}
	public void setValidWorkTimes(Integer validWorkTimes) {
		this.validWorkTimes = validWorkTimes;
	}
	public Double getValidMileage() {
		return validMileage;
	}
	public void setValidMileage(Double validMileage) {
		this.validMileage = validMileage;
	}
	public Integer getRealWorkTimes() {
		return realWorkTimes;
	}
	public void setRealWorkTimes(Integer realWorkTimes) {
		this.realWorkTimes = realWorkTimes;
	}
	public Double getRealMileage() {
		return realMileage;
	}
	public void setRealMileage(Double realMileage) {
		this.realMileage = realMileage;
	}
	
	
}
