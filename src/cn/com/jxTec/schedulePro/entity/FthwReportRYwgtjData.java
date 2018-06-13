package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;

/**
* @author ChenWang:
* @version 创建时间：2018年5月14日 下午3:06:59
* 类说明
*/
public class FthwReportRYwgtjData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2468808352535805397L;
	//部门名称
	private String deptName;                     
	//人员名称
	private String staffName;                    
	//脱岗次数
	private Long outPostCount;                    
	//脱岗时长(毫秒)
	private Long outPostDuration;                 
	//异常离线次数
	private Long exceptionOffLineCount;           
	//异常离线时长(毫秒)
	private Long exceptionOffLineDuration;        
	//违规滞留次数
	private Long violationStayCount;              
	//违规滞留时长(毫秒)
	private Long violationStayDuration;           
	//次数合计
	private Long totalCount;
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
	public Long getOutPostCount() {
		return outPostCount;
	}
	public void setOutPostCount(Long outPostCount) {
		this.outPostCount = outPostCount;
	}
	public Long getOutPostDuration() {
		return outPostDuration;
	}
	public void setOutPostDuration(Long outPostDuration) {
		this.outPostDuration = outPostDuration;
	}
	public Long getExceptionOffLineCount() {
		return exceptionOffLineCount;
	}
	public void setExceptionOffLineCount(Long exceptionOffLineCount) {
		this.exceptionOffLineCount = exceptionOffLineCount;
	}
	public Long getExceptionOffLineDuration() {
		return exceptionOffLineDuration;
	}
	public void setExceptionOffLineDuration(Long exceptionOffLineDuration) {
		this.exceptionOffLineDuration = exceptionOffLineDuration;
	}
	public Long getViolationStayCount() {
		return violationStayCount;
	}
	public void setViolationStayCount(Long violationStayCount) {
		this.violationStayCount = violationStayCount;
	}
	public Long getViolationStayDuration() {
		return violationStayDuration;
	}
	public void setViolationStayDuration(Long violationStayDuration) {
		this.violationStayDuration = violationStayDuration;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	} 
	
	
	
	
}
