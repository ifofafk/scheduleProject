package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.dto.CarHistoryAlarmDTO;
import cn.com.jxTec.schedulePro.dto.CarHistoryOilDataParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarHistoryParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarRealtimeOilParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarRealtimeParamsDTO;
import cn.com.jxTec.schedulePro.entity.FtAlarmTypesData;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryAlarmData;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryOilData;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryPosData;
import cn.com.jxTec.schedulePro.entity.FtCarInfo;
import cn.com.jxTec.schedulePro.entity.FtCarRealtimeOilData;
import cn.com.jxTec.schedulePro.entity.FtCarRealtimePosData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月1日 下午3:23:47 类说明 定时本期伏泰环卫车辆接口
 * 
 *          Version 2.0
 */
public class FthwCarTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取所有车辆信息 (会影响到后面查 车辆实时轨迹 的任务) 每天晚上11点55
	 * 目前对应qkcg_demo库需要更新specialty_sys_list、specialty_sys_gps_device_list
	 * 
	 * @throws SQLException
	 * 注意：如果车的数量增加了，参考固废车辆，需要将一次http分多次
	 */
	public void getFthwCarList() throws SQLException {
		long startLong = System.currentTimeMillis();
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarList.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";
		String sign = MD5Utils.string2MD5(key).toUpperCase();
		String paramValue = "{\"sign\":\"" + sign + "\"}";

		String result = "没有结果";
		try {
			result = HttpClientUtil.executeGetMethod4API(requestUrl, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("没有结果");
		}

		Gson g = new Gson();
		List<FtCarInfo> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtCarInfo>>() {
			}.getType());
			
		}

		//1. 做插入或者更新判断获取本系统所有车牌号信息
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=1 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));
		
		String cphSql1 = " select gps_device_id from specialty_sys_list where type='环卫系统车辆' ";
		List<String> ftCars1 = jdbcTemplate.queryForList(cphSql1, String.class);
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			FtCarInfo sourceCar = null;
			
			String sql = "";// 更新data_futai_car_info
			String sql1 = "";// 更新specialty_sys_list
			
			for (int i = 0; i < list.size(); i++) {
				sourceCar = list.get(i);
				if(sourceCar == null){
					continue;//继续下一轮
				}
				sql = " insert into data_futai_carInfo (car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name,create_time,update_time,sys_type) "
						+ " values ('" + list.get(i).getCarId() + "','" + list.get(i).getCarCode() + "','"
						+ list.get(i).getGroupCode() + "','" + list.get(i).getCarClassesId() + "','"
						+ list.get(i).getCarClassesCode() + "','" + list.get(i).getCarClassesName()
						+ "',NOW(),NOW(),1)";
				
				sql1 = " insert into specialty_sys_list (createtime,sites_or_unit,type,title,gps_device_id,cust_attr_2)  "
						+ " values (now(),1,'环卫系统车辆','" + list.get(i).getCarCode() + "','" + list.get(i).getCarCode() + "','"
						+ list.get(i).getCarClassesName()+"') ";
				
//				if (ftCars != null && ftCars.size() > 0) {
					for (FtCarInfo cphTemp : ftCars) {
						if ((cphTemp.getCarCode()).equals(list.get(i).getCarCode())) {
//							sql = " update data_futai_carInfo set group_code='" + list.get(i).getGroupCode()
//									+ "',car_classes_id='" + list.get(i).getCarClassesId() + "',car_classes_code='"
//									+ list.get(i).getCarClassesCode() + "',car_classes_name='"
//									+ list.get(i).getCarClassesName() + "',update_time=now() where car_code='" + cphTemp.getCarCode() + "'";
							sql = "";
						}
					}
//				}
				
//				if(ftCars1 != null && ftCars1.size() > 0){
					for (String cphTemp1 : ftCars1) {
						if (StringUtils.isNotBlank(cphTemp1) && cphTemp1.equals(list.get(i).getCarCode())) {
//							sql1 = " update specialty_sys_list set createtime=now(),type='环卫系统车辆',title='" 
//									+ cphTemp1 + "',cust_attr_2='" + list.get(i).getCarClassesName() + "' "
//									+ " where gps_device_id='" +cphTemp1+ "' ";
							sql1 ="";
						}
					}
//				}
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
				if(StringUtils.isNotBlank(sql1)){
					sqls.add(sql1);
				}
				
			}
			
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			
			
		}
		System.out.println("硚口环卫-车辆信息执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	
	
	// 2.根据carIds(逗号隔开)查询当前所有车的当前位置数据
	public void getFthwCarPositionTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarRealtimePosData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";

		// 查出本系统车辆信息
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=1 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));
		
		String cphSql1 = " select gps_device_id "
				+ " from specialty_sys_gps_device_list where gps_device_type='环卫车辆实时gps' ";
		List<String> realTimeCars1 = jdbcTemplate.queryForList(cphSql1, String.class);
		
		
		StringBuffer carIdsb = new StringBuffer();
		String carIds = "";
		if (StringUtils.isBlank(carIds)) {
			for (FtCarInfo one : ftCars) {
				carIdsb.append(one.getCarId());
				carIdsb.append(",");
			}
			carIds = carIdsb.toString();
			carIds = carIds.substring(0, carIds.length() - 1);
		}
		
		
		String mapType = "aMap";// 高德地图标志， bMap百度地图
		String key = "key=incomtest";// 测试key

		CarRealtimeParamsDTO carRealtimeParamsDTO = new CarRealtimeParamsDTO();
		carRealtimeParamsDTO.setCarIds(carIds);
		// 选填，bMap:转成百度地图坐标,aMap:转换成高德地图坐标
		carRealtimeParamsDTO.setMapType(mapType);
		String beforeStr = "carIds=" + carIds + "&" + "mapType=" + mapType;
		
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		carRealtimeParamsDTO.setSign(sign);

		String paramValue = carRealtimeParamsDTO.toString();
		String result = "没有结果";
		
		//修改为post请求进行测试
		Map<String, String> param = new HashMap<String, String>();
		param.put("parameters", paramValue);
		
		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		Gson g = new Gson();
		List<FtCarRealtimePosData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtCarRealtimePosData>>() {
			}.getType());
			
		}

		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			String sql = "";
			FtCarRealtimePosData ftCarRealtimePosData = null;
			for (int i = 0; i < list.size(); i++) {
				ftCarRealtimePosData = list.get(i);
				// 1.存入数据库 执行jdbc任务,将所有车辆信息存入specialty_gps_device_list表
				sql = " insert into specialty_sys_gps_device_list (createtime,"
						+ " last_update_time,gps_device_type,gps_device_id,gps_device_title,gps_device_notes,pos_jingdu,pos_weidu) "
						+ " values (NOW(),NOW(),'环卫车辆实时gps','" + ftCarRealtimePosData.getCarCode()
						+ "','" + ftCarRealtimePosData.getCarStatus() + "',CONCAT('futai::getCarRealtimePosData@',date_format(now(),'%Y-%c-%d %h:%i:%s')),"
						+ ftCarRealtimePosData.getLongitudeDone() + "," + ftCarRealtimePosData.getLatitudeDone() + ")";

				if (realTimeCars1.size() > 0) {
					for (String cphTemp : realTimeCars1) {
						if (cphTemp.equals(ftCarRealtimePosData.getCarCode())) {
							// 此车牌在动态元素里有的则更新
							sql = " update specialty_sys_gps_device_list set last_update_time=now(), " + " pos_jingdu="
									+ ftCarRealtimePosData.getLongitudeDone() + ",pos_weidu="
									+ ftCarRealtimePosData.getLatitudeDone() + ",gps_device_title='"
									+ ftCarRealtimePosData.getCarStatus() + "' where gps_device_id='"
									+ cphTemp + "'";
							//空表的时候要注意屏蔽这个for循环
							
						}
					}
				}
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
		}
		System.out.println("硚口环卫-车辆实际轨迹-执行完毕! " + Thread.currentThread()+"@" + LocalTime.now().withNano(0) +" 。耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	/**
	 * 3. 根据时间查询某台车历史轨迹
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @param carId
	 *            车辆id，若为空则查询所有
	 * @throws SQLException
	 */
	// 每天跑一次就可以跑出历史数据的
	public void getFthwCarHistoryTask() throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startLong = System.currentTimeMillis();
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryPosData.smvc";
		String paramKey = "parameters=";
		String mapType = "aMap";
		String key = "key=incomtest";
		String result = "没有结果";// 返回结果

		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startTime = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endTime = sdf.format(nowDate);

		CarHistoryParamsDTO carHistoryParamsDTO = new CarHistoryParamsDTO();// 生成参数使用
		// yyyy-MM-dd HH:mm:ss. 不填写startTime endTime默认当天
		carHistoryParamsDTO.setStartTime(startTime);
		carHistoryParamsDTO.setEndTime(endTime);
		carHistoryParamsDTO.setMapType(mapType);

		

		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=1 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));

		List<String> sqls = new ArrayList<>();// 最终sqls集合

		// 查询每台车指定时间历史轨迹插入数据库
		for (int i = 0; i < ftCars.size(); i++) {
			carHistoryParamsDTO.setCarId(ftCars.get(i).getCarId());
			String beforeStr = "carId=" + ftCars.get(i).getCarId() + "&" + "startTime=" + startTime + "&" + "endTime="
					+ endTime + "&" + "mapType=" + mapType;
			// bMap:转成百度地图坐标,aMap:转换成高德地图坐标
			String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
			carHistoryParamsDTO.setSign(sign);

			String paramValue = carHistoryParamsDTO.toString();
			try {
				result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(result);
			}
			
			Gson g = new Gson();
			List<FtCarHistoryPosData> list = null;
			JsonElement je_temp1 = new JsonParser().parse(result);
			JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
			if(je_temp2 != null && !je_temp2.isJsonNull()){
				JsonArray jetemp = je_temp2.getAsJsonArray();
				list = g.fromJson(jetemp, new TypeToken<List<FtCarHistoryPosData>>() {
				}.getType());
				
			}

			// 查出了车辆的历史轨迹，存入表gps_device_move_log
			if (list != null && !list.isEmpty()) {
				for (int j = 0; j < list.size(); j++) {
					sqls.add(" insert into gps_device_move_log (gps_device_id,pos_jingdu,pos_weidu,create_time) "
							+ "values ('" + list.get(j).getCarCode() + "'," + list.get(j).getLongitude() + ","
							+ list.get(j).getLatitude() + ",NOW()) ");
				}
			}
		}
		if(sqls.size() > 0){
			jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
		}
		System.out.println("硚口环卫-查询车历史轨迹-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	/**
	 * 4. 查询车辆当前油耗
	 * 
	 * @param carIds
	 *            车辆ids（逗号隔开） 若为空则查所有
	 * @throws SQLException
	 */
	public void getFthwCarRealtimeOilTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarRealtimeOilData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";

		// 查出本系统存储的伏泰所有车辆信息
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=1 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));
		//查出实时油耗表的车辆
		String realtimeOilsql = " select car_id,car_code,oil_level from data_futai_car_realtime_oil  ";
		List<FtCarRealtimeOilData> realtimeOilCars = jdbcTemplate.query(realtimeOilsql, new Object[] {},
				new BeanPropertyRowMapper<FtCarRealtimeOilData>(FtCarRealtimeOilData.class));
		
		StringBuffer carIdsb = new StringBuffer();
		String carIds = "";
		if (StringUtils.isBlank(carIds)) {
			for (FtCarInfo one : ftCars) {
				carIdsb.append(one.getCarId());
				carIdsb.append(",");
			}
			carIds = carIdsb.toString();
			carIds = carIds.substring(0, carIds.length() - 1);
		}

		CarRealtimeOilParamsDTO carRealtimeOilParamsDTO = new CarRealtimeOilParamsDTO();
		carRealtimeOilParamsDTO.setCarIds(carIds);
		String beforeStr = "carIds=" + carIds;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		carRealtimeOilParamsDTO.setSign(sign);

		String paramValue = carRealtimeOilParamsDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		Gson g = new Gson();
		List<FtCarRealtimeOilData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtCarRealtimeOilData>>() {
			}.getType());
			
		}
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();// 最终sql集合
			String sql = "";
			
			FtCarRealtimeOilData sourceData = null;
			for (int i = 0; i < list.size(); i++) {
				sourceData = list.get(i);
				String oilLevel = sourceData.getOilLevel()!=null?String.format("%.2f", sourceData.getOilLevel()) : "0";
				sql = " insert into data_futai_car_realtime_oil (car_id,car_code,oil_level,create_time,update_time) " + " values ('"
						+ sourceData.getCarId() + "','" + sourceData.getCarCode() + "'," + oilLevel
						+ ",NOW(),NOW()) ";
				for (FtCarRealtimeOilData two : realtimeOilCars) {
					// 库存车辆车牌号等于实时油耗车辆车牌号
					if (two.getCarCode().equals(sourceData.getCarCode())) {
						sql = " update data_futai_car_realtime_oil set oil_level=" + oilLevel
								+ " ,update_time=now() where car_code='" + two.getCarCode() + "'";
					}
				}
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
		}
		System.out.println("硚口环卫-查询车辆当前油耗-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

	}

	/**
	 * 5. 根据时间查询某台车的历史油耗
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @param carIds
	 *            车辆ids 若空则查所有
	 * @throws SQLException
	 * !!!!!!!!数据量太大。我差5台车昨天记录有1728条，请求5次加数据io操作，耗时24s!!!!!!
	 */
	public void getFthwCarHistoryOilTask() throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startLong = System.currentTimeMillis();
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryOilData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";

		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startTime = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endTime = sdf.format(nowDate);
		String result = "没有结果";

		CarHistoryOilDataParamsDTO carHistoryOilDataParamsDTO = new CarHistoryOilDataParamsDTO();
		// yyyy-MM-dd HH:mm:ss. 不填写startTime endTime默认当天
		carHistoryOilDataParamsDTO.setStartTime(startTime);
		carHistoryOilDataParamsDTO.setEndTime(endTime);

		// 真实环境用数据库查出的结果替换
		// String carIds =
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";
		

		List<String> sqls = new ArrayList<>();// 最终sql集合

		// 查出本系统存储的伏泰所有车辆信息
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo "
				+ " where sys_type=1 limit 1,5 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));
		
		// 查询每台车指定时间历史轨迹
		for (FtCarInfo one : ftCars) {
			carHistoryOilDataParamsDTO.setCarId(one.getCarId());
			String beforeStr = "carId=" + one.getCarId() + "&" + "startTime=" + startTime + "&" + "endTime=" + endTime;
			String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
			carHistoryOilDataParamsDTO.setSign(sign);

			String paramValue = carHistoryOilDataParamsDTO.toString();

			try {
				result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(result);
			}
			
			Gson g = new Gson();
			List<FtCarHistoryOilData> list = null;
			JsonElement je_temp1 = new JsonParser().parse(result);
			JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
			if(je_temp2 != null && !je_temp2.isJsonNull()){
				JsonArray jetemp = je_temp2.getAsJsonArray();
				list = g.fromJson(jetemp, new TypeToken<List<FtCarHistoryOilData>>() {
				}.getType());
				
			}

			if (list != null && !list.isEmpty()) {
				
				for (int j = 0; j < list.size(); j++) {
					Date d = new Date(list.get(j).getEquipmentTime());//时间是long
					String oilLevel = list.get(j).getOilLevel()!=null?String.format("%.2f", list.get(j).getOilLevel()) : "0";
					if(list.get(j).getOilLevel() != null && list.get(j).getOilLevel().doubleValue() != 0){
						sqls.add(" insert into data_futai_car_history_oil (car_id,car_code,equipment_time,oil_level,create_time) "
								+ "values ('" + one.getCarId() + "','" + one.getCarCode() + "',str_to_date('" + String.format("%tF %tT",d,d)
								+ "','%Y-%m-%d %H:%i:%s')," +oilLevel+ ",NOW()) ");
					}
				}
			}
		}
		if(sqls.size() > 0){
			jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
		}
		System.out.println("硚口环卫-查询车的历史油耗-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

	}

	/**
	 * 获得报警类别
	 */
	
	public void getAlarmTypesTask() {
		long startLong = System.currentTimeMillis();
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getAlarmTypesData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";
		String sign = MD5Utils.string2MD5(key).toUpperCase();
		String paramValue = "{\"sign\":\"" + sign + "\"}";

		String result = "没有结果";
		try {
			result = HttpClientUtil.executeGetMethod4API(requestUrl, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("没有结果");
		}

		Gson g = new Gson();
		List<FtAlarmTypesData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtAlarmTypesData>>() {
			}.getType());
			
		}

		if (list != null && !list.isEmpty()) {
			String[] sqls = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				sqls[i] = " insert into data_futai_alarm_type (alarm_code,alarm_name,create_time) " + " values ('"
						+ list.get(i).getCode() + "','" + list.get(i).getName() + "',NOW())";
			}
			
			if(sqls.length > 0){
				jdbcTemplate.batchUpdate(sqls);
			}
		}
		System.out.println("硚口环卫-报警类别-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

	}

	/**
	 * 已录入数据
	 * 报警历史记录
	 */
	public void getCarHistoryAlarmTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startLong = System.currentTimeMillis();
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryAlarmData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";

		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startTime = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endTime = sdf.format(nowDate);

		Integer page = 1;
		Integer rows = 1000;

		CarHistoryAlarmDTO carHistoryAlarmDTO = new CarHistoryAlarmDTO();
		carHistoryAlarmDTO.setPage(page);
		// yyyy-MM-dd HH:mm:ss. 不填写startTime endTime默认当天
		carHistoryAlarmDTO.setStartTime(startTime);
		carHistoryAlarmDTO.setEndTime(endTime);
		carHistoryAlarmDTO.setRows(rows);
		String beforeStr = "page=" + page + "&" + "startTime=" + startTime + "&" + "endTime=" + endTime + "&" + "rows="
				+ rows;
		// String beforeStr = "carId=" + carId + "&" + "mapType=" + mapType;
		// 选填，bMap:转成百度地图坐标,aMap:转换成高德地图坐标
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		carHistoryAlarmDTO.setSign(sign);

		String paramValue = carHistoryAlarmDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		Gson g = new Gson();
		List<FtCarHistoryAlarmData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtCarHistoryAlarmData>>() {
			}.getType());
			
		}

		if (list != null && !list.isEmpty()) {
			FtCarHistoryAlarmData alarm = null;
			String[] sqls = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				alarm = list.get(i);

				sqls[i] = " insert into data_futai_alarm_history (alarm_address,alarm_begin_time,alarm_end_time,"
						+ " alarm_level,alarm_level_name,alarm_type,alarm_type_name,been_do,car_id,"
						+ " car_no,car_type_name,position_speed,max_speed,futai_id,create_time) " + " values ('"
						+ alarm.getAlarmAddress() + "','" + alarm.getAlarmBeginTime() + "','" + alarm.getAlarmEndTime()
						+ "','" + alarm.getAlarmLevel() + "','" + alarm.getAlarmLevelName() + "','"
						+ alarm.getAlarmType() + "','" + alarm.getAlarmTypeName() + "'," + alarm.getBeenDo() + ",'"
						+ alarm.getCarId() + "','" + alarm.getCarNo() + "','" + alarm.getCarTypeName() + "',"
						+ alarm.getPositionSpeed() + "," +alarm.getMaxSpeed()+ ",'" + alarm.getId() + "',NOW())";
			}
			if(sqls.length > 0){
				jdbcTemplate.batchUpdate(sqls);
			}
		}
		System.out.println("硚口环卫-报警历史记录-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

	}



	// 测试main()
	/**
	 * 依赖注入的jdbctemplate.用静态main方法测试，无法实例化jdbctemplate？
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		FthwCarTask a = new FthwCarTask();
		a.getFthwCarPositionTask();
		
	}

}
