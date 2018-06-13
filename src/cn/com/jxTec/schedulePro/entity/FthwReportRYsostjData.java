package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年5月14日 下午3:06:59
* 类说明  人员sos短信发送次数统计
*/
public class FthwReportRYsostjData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7123371328530445742L;
	//部门名称
	private String deptName;	
	//人员名称
	private String staffName;	
	//手机
	private String staffPhone;
	//SOS短信发送次数
	private Long sendCount;
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
	public String getStaffPhone() {
		return staffPhone;
	}
	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}
	public Long getSendCount() {
		return sendCount;
	}
	public void setSendCount(Long sendCount) {
		this.sendCount = sendCount;
	}	
	
	
	
	
}
