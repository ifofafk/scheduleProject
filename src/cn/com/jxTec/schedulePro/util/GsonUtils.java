package cn.com.jxTec.schedulePro.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.entity.FtCarInfo;

/**
 * json字符串和java对象转换
 * @author Administrator
 *
 */
public class GsonUtils {
	private static GsonUtils ourInstance = new GsonUtils();

	public static GsonUtils getInstance() {
		return ourInstance;
	}

	private Gson gson;

	private GsonUtils() {
		gson = new Gson();
	}

	public <T> T getBean(String data, Class<T> tClass) {
		T t = null;
		try {
			t = gson.fromJson(data, tClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 返回list
	 * @param data  解析jsonArray 或jsonObject 转化为pojo
	 * @param tClass
	 * @return  List 或 List<0>
	 */
	public <T> List<T> getBean(JsonElement data, Class<T> tClass) {
		List<T> list = null;
		try {
			if(data.isJsonNull()){
				return null;
			}
			if(data.isJsonArray()){
				list = gson.fromJson(data.getAsJsonArray(), new TypeToken<List<T>>() {}.getType());
			}else{
				list = new ArrayList<>();
				list.add(gson.fromJson(data, tClass));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String toJSON(Object object) {
		String jsonData = "";
		try {
			jsonData = gson.toJson(object);
		} catch (Exception e) {
			jsonData = "";
			e.printStackTrace();
		}
		return jsonData;
	}
	
	public static void main(String[] args) {
//		String testStr = "{\"result\": 0,\"msg\": \"获取车辆列表信息成功\",\"data\": [{\"carClassesCode\": \"COLLECT\",\"carCode\": \"鄂AP3770\",\"identityID\": \"鄂AP3770\",\"carClassesId\": \"4524c662691147e580993f91ba3a475a\",\"carClassesName\": \"其他\",\"carId\": \"005042077c934a76acef1176bc3da8df\"}],\"exception\": null}";
//		List<FtCarInfo> list = null;
//		JsonElement je_temp1 = new JsonParser().parse(testStr);
//		
//		
		GsonUtils gsontest = GsonUtils.getInstance();
//		list = gsontest.getBean(je_temp1.getAsJsonObject().get("data"), FtCarInfo.class);
//		System.out.println(list);
		
		
		//
		String testStr = "{\"carClassesCode\": \"COLLECT\",\"carCode\": \"鄂AP3770\",\"identityID\": \"鄂AP3770\",\"carClassesId\": \"4524c662691147e580993f91ba3a475a\",\"carClassesName\": \"其他\",\"carId\": \"005042077c934a76acef1176bc3da8df\"}";
		List<FtCarInfo> list = null;
		JsonElement je_temp1 = new JsonParser().parse(testStr);
		
		
		list = gsontest.getBean(je_temp1, FtCarInfo.class);
		System.out.println(list);
		
		
		
	}
	
}
