package cn.com.jxTec.schedulePro.dto;

/**
 * 用于获得某台车辆的历史轨迹数据
 * @author wangchen
 *
 */
public class CarHistoryParamsDTO {

	private String carId;
	private String startTime;
	private String endTime;
	private String mapType;
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
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
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
				+ "\",\"mapType\":\"" + mapType + "\",\"sign\":\"" + sign + "\"}";

	}
	
	
	
	
}
