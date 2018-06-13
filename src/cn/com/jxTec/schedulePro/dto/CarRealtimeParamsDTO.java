package cn.com.jxTec.schedulePro.dto;
/**
 * 获取多台车的实时位置
 * @author wangchen
 *
 */
public class CarRealtimeParamsDTO {

	//以英文逗号隔开的车辆id
	private String carIds;
	
	//aMap bMap
	private String mapType;
	
	//生成签名
	private String sign;

	public String getCarIds() {
		return carIds;
	}

	public void setCarIds(String carIds) {
		this.carIds = carIds;
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
		return "{\"carIds\":\"" + carIds + "\",\"mapType\":\"" + mapType + "\",\"sign\":\"" + sign + "\"}";
	}
	
	
	
	
}
