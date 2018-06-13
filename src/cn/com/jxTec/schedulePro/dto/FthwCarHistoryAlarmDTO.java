package cn.com.jxTec.schedulePro.dto;
/**
 * 硚口环卫-查询报警报表记录
 * @author wangchen
 *
 */
public class FthwCarHistoryAlarmDTO {

	//用户id  必填
	private String userId;
	//报表编码    必填，固定值GPS_ALARM
	private String rptCode;
	//日期           必填，yyyy-MM-dd
	private String dateValue_EQ;
	//车辆ID  选填，多个用逗号隔开
	private String carId_IN;
	
	//报警类型   必填，
	/*overSpeed:超速汇总报表,overLineSpeed:分段超速,overStopTime:停车超时,
	overLine:越线,overAreaIn:禁入越界,overAreaOut:禁出越界,overAreaSpeed:分区超速*/
	private String alarmType_EQ;
	
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

	public String getDateValue_EQ() {
		return dateValue_EQ;
	}

	public void setDateValue_EQ(String dateValue_EQ) {
		this.dateValue_EQ = dateValue_EQ;
	}

	public String getCarId_IN() {
		return carId_IN;
	}

	public void setCarId_IN(String carId_IN) {
		this.carId_IN = carId_IN;
	}

	public String getAlarmType_EQ() {
		return alarmType_EQ;
	}

	public void setAlarmType_EQ(String alarmType_EQ) {
		this.alarmType_EQ = alarmType_EQ;
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
		return "{\"userId\":\"" + userId + "\",\"rptCode\":\"" + rptCode + "\",\"dateValue_EQ\":\"" + dateValue_EQ + "\",\"alarmType_EQ\":\"" + alarmType_EQ + "\",\"sign\":\"" + sign + "\"}";
	}

	//之前参数有carId_IN的 参数拼接
//	@Override
//	public String toString() {
//		return "{\"userId\":\"" + userId + "\",\"rptCode\":\"" + rptCode + "\",\"dateValue_EQ\":\"" + dateValue_EQ + "\",\"carId_IN\":\""
//				+ carId_IN + "\",\"alarmType_EQ\":\"" + alarmType_EQ + "\",\"sign\":\"" + sign + "\"}";
//	}
	
}
