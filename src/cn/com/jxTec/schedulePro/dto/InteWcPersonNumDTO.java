package cn.com.jxTec.schedulePro.dto;
/**
 * 智能公厕-人流量报表请求参数实体
 * @author wangchen
 *
 */
public class InteWcPersonNumDTO {

	//用户id  必填
	private String userId;
	//开始日期          必填，yyyy-MM-dd
	private String startDate;
	//结束日期          必填，yyyy-MM-dd
	private String endDate;
	//生成签名
	private String sign;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}



	//参看postman测试示例，只有5个参数参与拼接
	@Override
	public String toString() {
		return "{\"userId\":\"" + userId  + "\",\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\",\"sign\":\"" + sign + "\"}";
	}

}
