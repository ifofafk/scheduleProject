package cn.com.jxTec.schedulePro.dto;
/**
 * 硚口环卫-智能公厕-查询公厕臭气/人流当前值
 * @author wangchen
 *
 */
public class WcRealTimeParamsDTO {

	//查询的类型   必填，PersonNum：人流数据；OdorValue：臭气数据
	private String dataType;
	//开始时间       选填，yyyy-MM-dd HH:mm:ss
	private String startDate;
	//结束时间      选填，yyyy-MM-dd HH:mm:ss
	private String endDate;
	
	//生成签名
	private String sign;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getStartTime() {
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




	@Override
	public String toString() {
		return "{\"dataType\":\"" + dataType + "\",\"startDate\":\"" + startDate 
				+ "\",\"endDate\":\"" + endDate  + "\",\"sign\":\"" + sign + "\"}";
	}

	
}
