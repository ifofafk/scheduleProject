package cn.com.jxTec.schedulePro.dto;
/**
 * 硚口环卫-查询车辆油耗单位分析表
 * @author wangchen
 *
 */
public class FthwOilAnalysisDTO {

	//用户id  必填
	private String userId;
	//对方分发接口使用  必填，固定值GPS_TIMECOMPARE
	private String rptCode;
	//开始日期          必填，yyyy-MM-dd
	private String dateValue_GTE;
	//结束日期          必填，yyyy-MM-dd
	private String dateValue_LTE;
	
	//生成签名
	private String sign;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRptCode() {
		return rptCode;
	}

	public void setRptCode(String rptCode) {
		this.rptCode = rptCode;
	}

	public String getDateValue_GTE() {
		return dateValue_GTE;
	}

	public void setDateValue_GTE(String dateValue_GTE) {
		this.dateValue_GTE = dateValue_GTE;
	}

	public String getDateValue_LTE() {
		return dateValue_LTE;
	}

	public void setDateValue_LTE(String dateValue_LTE) {
		this.dateValue_LTE = dateValue_LTE;
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
		return "{\"userId\":\"" + userId + "\",\"rptCode\":\"" + rptCode + "\",\"dateValue_GTE\":\"" + dateValue_GTE + "\",\"dateValue_LTE\":\"" + dateValue_LTE + "\",\"sign\":\"" + sign + "\"}";
	}

	//之前参数有carId_IN的 参数拼接
//	@Override
//	public String toString() {
//		return "{\"userId\":\"" + userId + "\",\"rptCode\":\"" + rptCode + "\",\"dateValue_EQ\":\"" + dateValue_EQ + "\",\"carId_IN\":\""
//				+ carId_IN + "\",\"alarmType_EQ\":\"" + alarmType_EQ + "\",\"sign\":\"" + sign + "\"}";
//	}
	
}
