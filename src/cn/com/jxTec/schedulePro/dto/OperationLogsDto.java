package cn.com.jxTec.schedulePro.dto;

import java.io.Serializable;
/**
 * 
 * 2017年11月6日
 */
public class OperationLogsDto implements Serializable{

	private static final long serialVersionUID = 1L;
	/**登陆人**/
	private String name;
	/**登陆ip**/
	private String ip;
	/**登陆开始时间**/
	private String startTime;
	/**登陆结束时间**/
	private String endTime;
	/**操作类型 1 :新增操作 2: 删除操作 3:修改操作**/
	private String lx;

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
	

}
