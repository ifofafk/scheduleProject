package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年5月14日 下午3:06:59
* 类说明  考勤统计
*/
public class FthwReportRYkqtjData implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4626428018496611502L;
	
	//父级部门名称
	private String parentDeptName;
	//人员名称
	private String staffName;
	//日期
	private String attendanceDate;
	//部门名称
	private String deptName;
	//班次名称
	private String shiftName;
	//排班区域
	private String shiftWorkElementNames;
	//开始时间
	private String startTime;
	//结束时间	
	private String endTime;
	//考勤结果
	private String statusName;
	//缺卡次数
	private Long missingTimes;
	//迟到次数
	private Long lateTimes;
	//早退次数
	private Long earlyTimes;
	public String getParentDeptName() {
		return parentDeptName;
	}
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getShiftWorkElementNames() {
		return shiftWorkElementNames;
	}
	public void setShiftWorkElementNames(String shiftWorkElementNames) {
		this.shiftWorkElementNames = shiftWorkElementNames;
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getMissingTimes() {
		return missingTimes;
	}
	public void setMissingTimes(Long missingTimes) {
		this.missingTimes = missingTimes;
	}
	public Long getLateTimes() {
		return lateTimes;
	}
	public void setLateTimes(Long lateTimes) {
		this.lateTimes = lateTimes;
	}
	public Long getEarlyTimes() {
		return earlyTimes;
	}
	public void setEarlyTimes(Long earlyTimes) {
		this.earlyTimes = earlyTimes;
	}
	
	
	
	
	
}
