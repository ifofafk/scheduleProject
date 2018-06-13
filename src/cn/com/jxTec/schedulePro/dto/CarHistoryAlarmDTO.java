package cn.com.jxTec.schedulePro.dto;
/**
 * 分页查询报警历史记录
 * @author wangchen
 *
 */
public class CarHistoryAlarmDTO {

	private Integer page;
	private String startTime;
	private String endTime;
	private Integer rows;
	
	//生成签名
	private String sign;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "{\"page\":" + page + ",\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\",\"rows\":"
				+ rows + ",\"sign\":\"" + sign + "\"}";
	}

	
}
