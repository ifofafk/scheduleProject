package cn.com.jxTec.schedulePro.dto;

/**
 * 查询车辆当前油耗
 * @author Administrator
 *
 */
public class CarRealtimeOilParamsDTO {

	
	private String carIds;
	private String sign;
	public String getCarIds() {
		return carIds;
	}
	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "{\"carIds\":\"" + carIds + "\",\"sign\":\"" + sign + "\"}";
	}
	
	
	
	
	
}
