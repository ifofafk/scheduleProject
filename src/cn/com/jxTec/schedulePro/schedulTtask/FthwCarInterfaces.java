package cn.com.jxTec.schedulePro.schedulTtask;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.internal.LinkedTreeMap;

import cn.com.jxTec.schedulePro.dto.CarHistoryAlarmDTO;
import cn.com.jxTec.schedulePro.dto.CarHistoryOilDataParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarHistoryParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarRealtimeOilParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarRealtimeParamsDTO;
import cn.com.jxTec.schedulePro.entity.FtAlarmTypesData;
import cn.com.jxTec.schedulePro.entity.FthwReportCarHistoryAlarmData;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryOilData;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryPosData;
import cn.com.jxTec.schedulePro.entity.FtCarInfo;
import cn.com.jxTec.schedulePro.entity.FtCarRealtimeOilData;
import cn.com.jxTec.schedulePro.entity.FtCarRealtimePosData;
import cn.com.jxTec.schedulePro.entity.FtResult;
import cn.com.jxTec.schedulePro.util.GsonUtils;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
* @author ChenWang:
* @version 创建时间：2018年5月1日 下午3:23:47
* 类说明   定时本期伏泰环卫车辆接口
*/
public class FthwCarInterfaces {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取所有车辆信息 (会影响到后面查 车辆实时轨迹 的任务)
	 * 每天晚上11点55运行
	 * 目前对应qkcg_demo库需要更新specialty_sys_list、specialty_sys_gps_device_list
	 * problem：FtResult<T> gson无法将内部的T转化成指定的List<T.class> 而我也无法写FtResult
	 * <FtCarInfo> ftResult = g.getBean(result, FtResult<FtCarInfo>.class) ;
	 * 
	 * @throws SQLException
	 */
//	@Scheduled(0 55 23 * * ?)
	public void getFthwCarList() throws SQLException {
		JdbcTemplate jdbc = new JdbcTemplate();
		// 做插入或者更新判断获取本系统所有车牌号信息
		List<String> cphArray = getOurCarInfoList(jdbcTemplate).get("cphArray");

		// 最终sql
//		List<String> sqls = new ArrayList<String>();

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
		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult ftResult = g.getBean(result, FtResult.class);

		ArrayList list = ftResult.getData();
		if (list != null && !list.isEmpty()) {
			String[] sqls = new String[list.size()];
			
			
			// com.google.gson.internal.LinkedTreeMap 只能遍历这个LinkedTreeMap了。
			Object o = null;
			LinkedTreeMap two = null;
			Iterator ite = null;
			Entry e = null;
			FtCarInfo ftCar = null;
			
			String sql = "";//更新car_info
			
			for (int i = 0; i < list.size(); i++) {

				o = list.get(i);
				two = (LinkedTreeMap) o;
				ite = two.entrySet().iterator();

				ftCar = new FtCarInfo();
				while (ite.hasNext()) {
					e = (Map.Entry) ite.next();
					if ("carId".equals(e.getKey())) {
						ftCar.setCarId(e.getValue().toString());
					}
					if ("carCode".equals(e.getKey())) {
						ftCar.setCarCode(e.getValue().toString());
					}
					if ("groupCode".equals(e.getKey())) {
						ftCar.setGroupCode(e.getValue().toString());
					}
					if ("carClassesId".equals(e.getKey())) {
						ftCar.setCarClassesId(e.getValue().toString());
					}
					if ("carClassesCode".equals(e.getKey())) {
						ftCar.setCarClassesCode(e.getValue().toString());
					}
					if ("carClassesName".equals(e.getKey())) {
						ftCar.setCarClassesName(e.getValue().toString());
					}
				}
				
				sql = " insert into data_futai_carInfo (car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name,create_time,update_time) "
						+ " values ('" + ftCar.getCarId() + "','" + ftCar.getCarCode() + "','" + ftCar.getGroupCode()
						+ "','" + ftCar.getCarClassesId() + "','" + ftCar.getCarClassesCode() + "','"
						+ ftCar.getCarClassesName() + "',NOW(),NOW())";
				if (cphArray.size() > 0) {
					for (String cphTemp : cphArray) {
						if (cphTemp.equals(ftCar.getCarCode())) {
							sql = " update data_futai_carInfo set group_code='" + ftCar.getGroupCode()
									+ "',car_classes_id='" + ftCar.getCarClassesId() + "',car_classes_code='"
									+ ftCar.getCarClassesCode() + "',car_classes_name='" + ftCar.getCarClassesName()
									+ "',update_time=now() ";
						}
					}
//					sqls.add(sql);
					sqls[i] = sql;
				}
			}

//			jdbcTemplate.batchUpdate(sqls);
		}
	}
	
	

	// 2.根据carIds(逗号隔开)查询当前所有车的当前位置数据
	public void getFthwCarPositionTask() throws SQLException {
//		// 数据库连接
//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
//		jdbcTemplate.getConnection();

//		List<String> sqls = new ArrayList<String>();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarRealtimePosData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";

		//查出本系统车辆信息
		String carIds = "";//生成签名需要
		StringBuffer carIdsb = new StringBuffer();
		List<String> cphArray = getOurCarInfoList(jdbcTemplate).get("cphArray");
		List<String> carIdArray = getOurCarInfoList(jdbcTemplate).get("carIdArray");
		for(String one : carIdArray){
			carIdsb.append(one);
			carIdsb.append(",");
		}
		carIds = carIdsb.toString();
		carIds = carIds.substring(0, carIds.length() - 1);
		
		String mapType = "aMap";
		String key = "key=incomtest";
		CarRealtimeParamsDTO carRealtimeParamsDTO = new CarRealtimeParamsDTO();
		carRealtimeParamsDTO.setCarIds(carIds);
		carRealtimeParamsDTO.setMapType(mapType);
		String beforeStr = "carIds=" + carIds + "&" + "mapType=" + mapType;

		// 选填，bMap:转成百度地图坐标,aMap:转换成高德地图坐标
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		carRealtimeParamsDTO.setSign(sign);

		String paramValue = carRealtimeParamsDTO.toString();
		System.out.println(paramValue);
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FtCarRealtimePosData> ftResult = g.getBean(result, FtResult.class);
		ArrayList<FtCarRealtimePosData> list = ftResult.getData();

		// 此处如何解决com.google.gson.internal.LinkedTreeMap
		if (list != null && !list.isEmpty()) {
			String[] sqls = new String[list.size()];
			
			// com.google.gson.internal.LinkedTreeMap 只能遍历这个LinkedTreeMap了。
			Object o = null;
			LinkedTreeMap two = null;
			Iterator ite = null;
			Entry e = null;
			FtCarRealtimePosData ftCarRealtimePosData = null;
			String sql = "";

			int testNum = 0;// 测试计数
			for (int i = 0; i < list.size(); i++) {

				// 1.存入数据库 执行jdbc任务,将所有车辆信息存入specialty_gps_device_list表
				o = list.get(i);
				two = (LinkedTreeMap) o;

				ite = two.entrySet().iterator();

				ftCarRealtimePosData = new FtCarRealtimePosData();
				while (ite.hasNext()) {
					e = (Map.Entry) ite.next();
					if ("carId".equals(e.getKey())) {
						ftCarRealtimePosData.setCarId(e.getValue() != null ? e.getValue().toString() : "");
					}
					if ("latitudeDone".equals(e.getKey())) {
						ftCarRealtimePosData.setLatitudeDone(e.getValue() != null ? (Double) e.getValue() : 0);
					}
					if ("longitudeDone".equals(e.getKey())) {
						ftCarRealtimePosData.setLongitudeDone(e.getValue() != null ? (Double) e.getValue() : 0);
					}
					if ("carCode".equals(e.getKey())) {
						ftCarRealtimePosData.setCarCode(e.getValue() != null ? e.getValue().toString() : "");
					}
				}

				sql = " insert into specialty_sys_gps_device_list (createtime,"
						+ "last_update_time,gps_device_type,gps_device_id,gps_device_notes,pos_jingdu,pos_weidu) "
						+ "values (NOW(),NOW(),'车辆实时gps','" + ftCarRealtimePosData.getCarCode()
						+ "',CONCAT('futai::getCarRealtimePosData@',date_format(now(),'%Y-%c-%d %h:%i:%s')),"
						+ ftCarRealtimePosData.getLongitudeDone() + "," + ftCarRealtimePosData.getLatitudeDone() + ")";
				///////////

				if (cphArray.size() > 0) {
					for (String cphTemp : cphArray) {
						testNum++;
						if (cphTemp.equals(ftCarRealtimePosData.getCarCode())) {
							// 此车牌在动态元素里有的则更新
							sql = " update specialty_sys_gps_device_list set last_update_time=now(), " + " pos_jingdu="
									+ ftCarRealtimePosData.getLongitudeDone() + ",pos_weidu="
									+ ftCarRealtimePosData.getLatitudeDone() + " where gps_device_id='"
									+ ftCarRealtimePosData.getCarCode() + "'";
							System.out.println("第" + testNum + "条，车牌已存在更新位置");
						} 
					}
					sqls[i] = sql;
//					sqls.add(sql);

				}

			}
			
//			jdbcTemplate.batchData(sqls);
			//jdbcTemplate.batchUpdate(sqls);

		}
	}

	// 3根据时间查询某台车历史轨迹
	public void getFthwCarHistoryTask(String carCode) {
		// 数据库连接
//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
//		jdbcTemplate.getConnection();
		
		
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryPosData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// String carIds =
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";
		
		//根据车牌号查carId
		
		String carId = "e92f5a6e21fd411f844012041f9f2ec4";
		String startTime = "2018-04-26 13:13:44";
		String endTime = "2018-04-27 13:13:44";

		// String startTime = "";
		// String endTime = "";
		String mapType = "aMap";

		String key = "key=incomtest";

		CarHistoryParamsDTO carHistoryParamsDTO = new CarHistoryParamsDTO();
		carHistoryParamsDTO.setCarId(carId);
		// yyyy-MM-dd HH:mm:ss. 不填写startTime endTime默认当天
		carHistoryParamsDTO.setStartTime(startTime);
		carHistoryParamsDTO.setEndTime(endTime);
		carHistoryParamsDTO.setMapType(mapType);
		String beforeStr = "carId=" + carId + "&" + "startTime=" + startTime + "&" + "endTime=" + endTime + "&"
				+ "mapType=" + mapType;
		
		// bMap:转成百度地图坐标,aMap:转换成高德地图坐标
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		carHistoryParamsDTO.setSign(sign);

		String paramValue = carHistoryParamsDTO.toString();
		System.out.println(paramValue);
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FtCarHistoryPosData> ftResult = g.getBean(result, FtResult.class);

		System.out.println(ftResult);
	}

	// 4查询车辆s当前油耗
	public void getFthwCarRealtimeOilTask() {
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarRealtimeOilData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// String carIds =
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";
		String carIds = "e92f5a6e21fd411f844012041f9f2ec4";

		String key = "key=incomtest";

		CarRealtimeOilParamsDTO carRealtimeOilParamsDTO = new CarRealtimeOilParamsDTO();
		carRealtimeOilParamsDTO.setCarIds(carIds);
		String beforeStr = "carIds=" + carIds;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		carRealtimeOilParamsDTO.setSign(sign);

		String paramValue = carRealtimeOilParamsDTO.toString();
		System.out.println(paramValue);
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FtCarRealtimeOilData> ftResult = g.getBean(result, FtResult.class);
		System.out.println(ftResult);
	}

	/*
	 * 查询某台车的历史油耗
	 */
	public void getFthwCarHistoryOilTask() {
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryOilData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// String carIds =
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";
		String carId = "e92f5a6e21fd411f844012041f9f2ec4";
		String startTime = "2018-04-26 13:13:44";
		String endTime = "2018-04-27 13:13:44";
		String key = "key=incomtest";

		CarHistoryOilDataParamsDTO carHistoryOilDataParamsDTO = new CarHistoryOilDataParamsDTO();
		carHistoryOilDataParamsDTO.setCarId(carId);
		// yyyy-MM-dd HH:mm:ss. 不填写startTime endTime默认当天
		carHistoryOilDataParamsDTO.setStartTime(startTime);
		carHistoryOilDataParamsDTO.setEndTime(endTime);
		String beforeStr = "carId=" + carId + "&" + "startTime=" + startTime + "&" + "endTime=" + endTime;
		// String beforeStr = "carId=" + carId + "&" + "mapType=" + mapType;
		// 选填，bMap:转成百度地图坐标,aMap:转换成高德地图坐标
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		carHistoryOilDataParamsDTO.setSign(sign);

		String paramValue = carHistoryOilDataParamsDTO.toString();
		System.out.println(paramValue);
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FtCarHistoryOilData> ftResult = g.getBean(result, FtResult.class);
		System.out.println(ftResult);
	}

	/**
	 * 获得报警类别
	 */
	public void getAlarmTypesTask() {
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
		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FtAlarmTypesData> ftResult = g.getBean(result, FtResult.class);

		List<FtAlarmTypesData> list = ftResult.getData();
		if (list != null && !list.isEmpty()) {
			StringBuffer temp = new StringBuffer();
			String name = null;
			// 此处循环还需要编辑

			// com.google.gson.internal.LinkedTreeMap 只能遍历这个LinkedTreeMap了。
			for (FtAlarmTypesData one : list) {
				name = one.getName();
				temp.append(name);
				temp.append(",");
			}
			System.out.println("========" + temp);

		}

	}

	/**
	 * 查询报警历史记录
	 */
	public void getCarHistoryAlarmTask() {
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/carInfo/getCarHistoryAlarmData.smvc";
		String paramKey = "parameters=";
		// 真实环境用数据库查出的结果替换
		// String carIds =
		// "e92f5a6e21fd411f844012041f9f2ec4,d942e483b5194c0daced069aca5a80c5,cf11936ad3c641a1be92009894bfd1a2,657740ef19dc4c2a9b0310d0e25bad86,d8fb3bebebe7461bb33f7b16f4e4c8c1,c26c79bb1d1e4fedabc2de1160fda152,d629f86eaa8b449e8c1d8cd141882a12,5abf5b3e6dc34429adc4b8cb6c6cf2ce,e9bdf8d9de2742479ff1f4b3f48e8ff8,e3a64676ac3f4ce586b0387c4c3e76ee,824decabb34446689ef0d322c87e8d01,48300d21a3314f16b6aab37fd955a03f,505470f5188548de8843a62cfb747d0b,8fb6a5fa40454052b0ba5c33354a1581,78cee89fb4a04cc3bf4927defe87ead1,86db7704e4764c27adefe6a6abd46648,b12b0293c20041768bcdd3da311652d1,0d2fabaa31114df893d22cfd34bb7084,a32ea39fd188446b87bd32b7060c32f7,1c3363bb8e5b4bdeb643a615a354a226,02842b87f8d441509c7a0879d2fb0d99,1789e64412684600af812b6babcae6a4,4110ed715538445faad1275bffdef55b,45c7df5bcbe449b5a3dee6410fb53cb2,aebd47e5ba1a41cd87c9ee956c65a431,43d9a34a57b74261ad1766a8644bd357,094a3e4a080a47928e742c07762d562c,7076d335f3074f39914e138875f51dbe,e6a28c57f17743a3a6307af69e3eb296,f492164ba9ed48dea97b7bab35b11623,db88807133e54368913d729e3b1b934c,dc5099c28c7a4bf086fbacd6a7bf269c,ed33e750bf774acd99e22f2a5c6fb64f,cea299296f3a48b29e249d836f8feeaf,14119719b91f493b90ea51a757fa9615,633065ebeba6486bbc1516d289ff9cc7,5523e83c2e1747f98d79cff55979049e,8fbf247fb70645949c28958fb25278b0,90545d3863b348c6996ebea873a074ef,cd32c53852ab4bf8b7e118b1cbcf4ac0,7a5d65a2359d47cd95170b380629313b,adc38d0b83eb491f9b019ae0d8c47694,1fd2be57565e4f5f9acb82d51d141a85,f653b746728247b38f356ac6a15315fa,80687800f85e477a8fb4f4aefd1b1007,0201b92b4e514f69b4b5ea1171666e6f,16d65fb55bdd4710847180400c00962c,198c7d8387284f9faabaf1116a58481c,fd473abc95214116affc5d03b6a949d7,800b5489f86547228865780e57ebcfb5,cb6d8ec756614409919256112750a08d,9ea995b57a564dab96ff742388dc1525,2af7417c3a324da5979c83b4f7dd52ad,7d35d54bfc844b619becc146ee81ec0d,1feee2cc608c4808b6ea131e09e98320,77d329cece394a84be85dbec23dcc767,0fe435057ea541b883eef31e9cda88a2,c1527eff48bd49dcb51e7c52bdf00b6b,588128f29c634b66aa9572c04e6234bb,87234df9c753497c93b1058a513f63f8,f2326ae60edc40199bdcd4b46b32b42b,f3cf1f5590d7452199ddc3a7562523f7,33f7fd7b86cc4e18ad2c4ff607789593,afb8159ea97348d6aa0ebb61b72b8782,1e72b8b3abd24ae79dc713c1339ae861,269448efd2b54b199f5bf104d71153e1,94d5f68833fc4ccc999934459b68d9c8,2b475186199d449faaac11936ad580c4,d0fbedcedae145118facf0e2726fae41,dc6e76e291204032b49f7230591ce8a0,04ddd24ab04c4d82abbc93d1174acc94,e8f440e178614585a40364d9e0fee6b0,3f0af044f365401a9b3d06ea3829c7b3,04419f4a52954695898437916e8fdf96,b1f96545576a4c6a87548f16f06d0ae6,53dcf277b0d64f109128fb90597919a1,e885eea1ed134039ae44a537644dcb80,2d3a4e15c34b43e885ce9448d17b4d38,37bb7932f58f46639ea43e2dfea3effd,4eef3ec8f6c544529e83b475bf86a610,ea67bbaf3e9c4eafaf054c6d12508f9d,88d95e4add774b4fb35d6529aa470c48,f3a9d0e0de934cefb2622f6ab3e60a80,270a246cdd8b418d93d29fec0a108dd4,1b7da000d307403dac72be8f0bdb9557,33134f0a8e5541b287cb38e5d2998f28,77dd628d10534f008f25cf1a5f7799c8,09d4007a6eda4cc08b2a386c54859c42,be06c41eef834b7c864e5bf0934dbb4a,5c4d3cb71e1344129e6cc1619a5dc312";
		Integer page = 1;
		String startTime = "2018-04-26 13:13:44";
		String endTime = "2018-04-27 13:13:44";
		Integer rows = 10;

		String key = "key=incomtest";

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
		System.out.println(paramValue);
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		System.out.println(result);

		GsonUtils g = GsonUtils.getInstance();
		FtResult<FthwReportCarHistoryAlarmData> ftResult = g.getBean(result, FtResult.class);

		System.out.println(ftResult);
	}

	

	public static Map<String,List<String>> getOurCarInfoList(JdbcTemplate jdbcTemplate) throws SQLException {
		// 1.先查出本系统所有车辆车牌信息
		String cphSql = " select car_id,car_code from data_futai_carInfo ";
		
		List<String> cphArray = new ArrayList<>();
		List<String> carIdArray = new ArrayList<>();
		Map<String,List<String>> result = new HashMap<>();

		List<Map<String,Object>> list =jdbcTemplate.queryForList(cphSql);
		if(list != null && !list.isEmpty()){
			for(int i=0; i< list.size(); i++){
				cphArray.add(list.get(i).get("car_code").toString());
				carIdArray.add(list.get(i).get("car_id").toString());
				;
			}
		}
		
		result.put("cphArray", cphArray);
		result.put("carIdArray", carIdArray);
		
		System.out.println("目前本系统车辆信息有：" + cphArray.size() + "条");
		return result;
	}

	
}
