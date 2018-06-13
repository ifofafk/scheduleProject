package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年5月14日 下午3:06:59
* 类说明  人员-里程统计
*/
public class FthwReportRYlctjData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5896286361730230474L;
	//部门名称
	private String deptName;
	//人员名称
	private String staffName;
	//总作业里程（单位米）
	private Long totalMileage;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public Long getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Long totalMileage) {
		this.totalMileage = totalMileage;
	}
	
	
	
	
	
}
