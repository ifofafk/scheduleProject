package cn.com.jxTec.schedulePro.dto;

/**
 * 查询车辆历史油耗
 * @author wangchen
 *
 */
public class CarHistoryOilDataParamsDTO {

	private String carId;
	private String startTime;
	private String endTime;
	//生成签名
	private String sign;
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "{\"carId\":\"" + carId + "\",\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime
				+ "\",\"sign\":\"" + sign + "\"}";

	}
	
	
	
	
}
