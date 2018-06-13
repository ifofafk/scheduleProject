package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.dto.CarHistoryParamsDTO;
import cn.com.jxTec.schedulePro.dto.CarRealtimeParamsDTO;
import cn.com.jxTec.schedulePro.entity.FtCarHistoryPosData;
import cn.com.jxTec.schedulePro.entity.FtCarInfo;
import cn.com.jxTec.schedulePro.entity.FtCarRealtimePosData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;
import cn.com.jxTec.schedulePro.util.PageUtils;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明
 * 武汉固废车辆相关接口调用
 */
public class WhgfCarTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 武汉固废车辆信息 每天晚上11点50运行 还包含一些报表接口
	 * 
	 * @throws SQLException
	 */
	public void getWhgfCarList() throws SQLException {
		long startLong = System.currentTimeMillis();
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/whgfCarInfo/getCarList.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";
		String xzqhName = "硚口区";

		String sign = MD5Utils.md5("xzqhName=" + xzqhName + "&" + key,"gbk").toUpperCase();
		String paramValue = "{\"xzqhName\":\"" + xzqhName + "\",\"sign\":\"" + sign + "\"}";
		String result = "没有结果";

		// 做插入或者更新判断获取   固废系统 所有车牌号信息
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=2 ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));


		String cphSql1 = " select gps_device_id from specialty_sys_list where type='固废系统车辆' ";
		List<String> ftCars1 = jdbcTemplate.queryForList(cphSql1, String.class);
		
		
		try {
			result = HttpClientUtil.executeGetMethod4API(requestUrl, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("没有结果");
		}

		Gson g = new Gson();
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonArray jetemp = je_temp1.getAsJsonObject().get("data").getAsJsonArray();
		List<FtCarInfo> list = g.fromJson(jetemp, new TypeToken<List<FtCarInfo>>() {
		}.getType());

		if (list != null && !list.isEmpty()) {
			
			List<String> sqls = new ArrayList<>();
			FtCarInfo sourceCar = null;

			String sql = "";// 更新car_info
			String sql1 = "";// 更新specialty_sys_list
			
			for (int i = 0; i < list.size(); i++) {
				sourceCar = list.get(i);
				if(sourceCar == null){
					continue;//继续下一轮
				}
				
				sql = " insert into data_futai_carInfo (car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name,create_time,update_time,sys_type) "
						+ " values ('" + sourceCar.getCarId() + "','" + sourceCar.getCarCode() + "','" + sourceCar.getGroupCode()
						+ "','" + sourceCar.getCarClassesId() + "','" + sourceCar.getCarClassesCode() + "','"
						+ sourceCar.getCarClassesName() + "',NOW(),NOW(),2)";
				sql1 = " insert into specialty_sys_list (createtime,sites_or_unit,type,title,gps_device_id,cust_attr_2)  "
						+ " values (now(),1,'固废系统车辆','" + sourceCar.getCarCode() + "','" + sourceCar.getCarCode() + "','"
						+ sourceCar.getCarClassesName()+"') ";
				
//				if (ftCars != null && !ftCars.isEmpty()) {//本系统的固废车辆
					for (FtCarInfo cphTemp : ftCars) {
						//找到一条相同就不插入
						if ((cphTemp.getCarCode()).equals(sourceCar.getCarCode())) {
//							sql = " update data_futai_carInfo set group_code='" + sourceCar.getGroupCode()
//									+ "',car_classes_id='" + sourceCar.getCarClassesId() + "',car_classes_code='"
//									+ sourceCar.getCarClassesCode() + "',car_classes_name='" + sourceCar.getCarClassesName()
//									+ "',update_time=now() where car_code='" + cphTemp.getCarCode() + "'";
							sql = "";
						}
					}
//				}
//				if(ftCars1 != null && !ftCars1.isEmpty()){
					for (String cphTemp1 : ftCars1) {
						if (StringUtils.isNotBlank(cphTemp1) && cphTemp1.equals(sourceCar.getCarCode())) {
//							sql1 = " update specialty_sys_list set createtime=now(),type='固废系统车辆',title='" 
//									+ cphTemp1 + "',cust_attr_2='" + sourceCar.getCarClassesName() + "' "
//									+ " where gps_device_id='" +cphTemp1+ "' ";
							sql1 = "";
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
			System.out.println("拼装sql耗时： " + (System.currentTimeMillis()-startLong)/1000 + "秒");
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("固废车辆信息查询执行完毕! 耗时： " + (System.currentTimeMillis()-startLong)/1000 + "秒");
			
		}
	}

	
	/**
	 * 固废系统车辆实时位置查询
	 * 
	 * @param carIds
	 * @throws SQLException
	 * 时效性：目前耗时5-8s  联想E450
	 */
	public void getWhgfCarPositionTask() throws SQLException {
		long startLong = System.currentTimeMillis();
	
		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/whgfCarInfo/getCarRealtimePosData.smvc";
		String paramKey = "parameters=";
		String mapType = "aMap";// 高德地图标志， bMap百度地图
		String key = "key=incomtest";// 测试key
		
		// 做插入或者更新判断获取本系统所有固废系统车牌号
		String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name "
				+ " from data_futai_carInfo where sys_type=2 ";
		
		List<FtCarInfo> whgfCars = jdbcTemplate.query(cphSql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));
		
		String cphSql1 = " select gps_device_id from specialty_sys_list where type='固废系统车辆' ";
		List<String> ftCars1 = jdbcTemplate.queryForList(cphSql1, String.class);
		
		//http请求地址过长导致404，拆分一下。一个carId 23位   1130多个
		if(whgfCars != null && !whgfCars.isEmpty()){
			
			PageUtils<FtCarInfo> pageutils = new PageUtils<>();
			List<List<FtCarInfo>> totalList = pageutils.paging(whgfCars, 200);//totalList.size()次请求,一次500个carId
			
			
			List<String> sqls = new ArrayList<>();
			for(List<FtCarInfo> page_one : totalList){
				StringBuffer carIdsb = new StringBuffer();
				String carIds = "";
				//拼接这个分页的carIds
				for(FtCarInfo page_two : page_one){
					carIdsb.append(page_two.getCarId());
					carIdsb.append(",");
				}
				carIds = carIdsb.toString();
				carIds = carIds.substring(0, carIds.length() - 1);

				CarRealtimeParamsDTO carRealtimeParamsDTO = new CarRealtimeParamsDTO();
				carRealtimeParamsDTO.setCarIds(carIds);
				carRealtimeParamsDTO.setMapType(mapType);
				String beforeStr = "carIds=" + carIds + "&" + "mapType=" + mapType;

				// 选填，bMap:转成百度地图坐标,aMap:转换成高德地图坐标
				String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
				carRealtimeParamsDTO.setSign(sign);

				String paramValue = carRealtimeParamsDTO.toString();
				String result = "没有结果";

				try {
					result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException(result);
				}

				Gson g = new Gson();
				JsonElement je_temp1 = new JsonParser().parse(result);
				JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
				List<FtCarRealtimePosData> list = null;
				if(je_temp2 != null && !je_temp2.isJsonNull()){
					JsonArray jetemp = je_temp2.getAsJsonArray();
					list = g.fromJson(jetemp, new TypeToken<List<FtCarRealtimePosData>>() {}.getType());
				}
				
				
				if (list != null && !list.isEmpty()) {
					
					//为演示而添加的数量限制代码-------- start -----
//					List<FtCarRealtimePosData> ysList =  new ArrayList<>();
//					if(list.size() > 100){
//						ysList = list.subList(1, 100);
//					}
					//为演示而添加的数量限制代码-------- end -------
					

					FtCarRealtimePosData ftCarRealtimePosData = null;
					String sql = "";

					for (int i = 0; i < list.size(); i++) {
//					for (int i = 0; i < ysList.size(); i++) {
						ftCarRealtimePosData = list.get(i);
					
						//限制经纬度在硚口区内，只做一个矩形的区域，实际大小超过硚口区（防止超出武汉市）
						
						//-------如果在区域外，则舍弃这条记录--------
						if((ftCarRealtimePosData.getLongitudeDone()<114.163361 | ftCarRealtimePosData.getLongitudeDone()>114.291401) 
								|| (ftCarRealtimePosData.getLatitudeDone()<30.56697 | ftCarRealtimePosData.getLatitudeDone()>30.636771)){
							continue;
						}
						//insert跑一次就行，可以在收集固废车辆时将车牌号写入specialty_sys_gps_device_list
						sql = " insert into specialty_sys_gps_device_list (createtime,"
								+ " last_update_time,gps_device_type,gps_device_id,gps_device_notes,pos_jingdu,pos_weidu) "
								+ " values (NOW(),NOW(),'固废系统车辆实时gps','" + ftCarRealtimePosData.getCarCode()
								+ "',CONCAT('futai::whgfCarInfo/getCarRealtimePosData@',date_format(now(),'%Y-%c-%d %h:%i:%s')),"
								+ ftCarRealtimePosData.getLongitudeDone() + "," + ftCarRealtimePosData.getLatitudeDone() + ")";
//						if (page_one.size() > 0) {
//							for (FtCarInfo cphTemp : page_one) {
//								if ((cphTemp.getCarCode()).equals(ftCarRealtimePosData.getCarCode())) {
//									// 此车牌在动态元素里有的则更新
//									sql = " update specialty_sys_gps_device_list set last_update_time=now(), gps_device_type='固废系统车辆实时gps',pos_jingdu="
//											+ ftCarRealtimePosData.getLongitudeDone() + ",pos_weidu="
//											+ ftCarRealtimePosData.getLatitudeDone() + " where gps_device_id='"
//											+ ftCarRealtimePosData.getCarCode() + "'";
//								}
//							}
//						}
						
						if (ftCars1.size() > 0) {
							for (String cphTemp : ftCars1) {
								if (cphTemp.equals(ftCarRealtimePosData.getCarCode())) {
									// 此车牌在动态元素里有的则更新
									sql = " update specialty_sys_gps_device_list set last_update_time=now(), gps_device_type='固废系统车辆实时gps',pos_jingdu="
											+ ftCarRealtimePosData.getLongitudeDone() + ",pos_weidu="
											+ ftCarRealtimePosData.getLatitudeDone() + " where gps_device_id='"
											+ cphTemp+ "'";
								}
							}
						}
						if(StringUtils.isNotBlank(sql)){
							sqls.add(sql);
						}
					}
				}
				
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("固废车辆-实时轨迹-执行完毕! " + Thread.currentThread()+"@" + LocalTime.now().withNano(0) +" 。耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

		}
		
	}

	
	
	/**
	 * 3. 根据时间查询 固废系统 某台车历史轨迹
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @param carId
	 *            车辆id，若为空则查询所有
	 * @throws SQLException
	 */
	public void getWhgfCarHistoryTask() throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startLong = System.currentTimeMillis();
		
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/whgfCarInfo/getCarHistoryPosData.smvc";
		String paramKey = "parameters=";
		String mapType = "aMap";
		String key = "key=incomtest";
		String result = "没有结果";// 返回结果

		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startTime = sdf.format(cal.getTime());
		String endTime = sdf.format(nowDate);
		
		CarHistoryParamsDTO carHistoryParamsDTO = new CarHistoryParamsDTO();// 生成参数使用
		carHistoryParamsDTO.setStartTime(startTime);
		carHistoryParamsDTO.setEndTime(endTime);
		carHistoryParamsDTO.setMapType(mapType);

		// 如果不传具体carId ,查询车辆数据库所有车辆上gps历史轨迹
		String carId = "";
		if (StringUtils.isBlank(carId)) {
			Gson g = new Gson();
			List<String> sqls = new ArrayList<>();// 最终sqls集合

			// 做插入或者更新判断获取固废系统所有车牌号
			String cphSql = " select car_id,car_code,group_code,car_classes_id,car_classes_code,car_classes_name from data_futai_carInfo where sys_type=2 ";
			List<FtCarInfo> whgfCars = jdbcTemplate.query(cphSql, new Object[] {},
					new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));

			// 查询每台车指定时间历史轨迹插入数据库
			for (int i = 0; i < whgfCars.size(); i++) {
				carId = whgfCars.get(i).getCarId();
				carHistoryParamsDTO.setCarId(carId);
				String beforeStr = "carId=" + carId + "&" + "startTime=" + startTime + "&" + "endTime=" + endTime + "&"
						+ "mapType=" + mapType;
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

				// 查出了车辆的历史轨迹，存入表gps_device_move_log
				JsonElement je_temp1 = new JsonParser().parse(result);
				JsonArray jetemp = je_temp1.getAsJsonObject().get("data").getAsJsonArray();
				ArrayList<FtCarHistoryPosData> carHistoryList = g.fromJson(jetemp, new TypeToken<List<FtCarHistoryPosData>>() {
				}.getType());
				
				if (carHistoryList != null && !carHistoryList.isEmpty()) {
					for (int j = 0; j < carHistoryList.size(); j++) {
						sqls.add(" insert into gps_device_move_log (gps_device_id,pos_jingdu,pos_weidu,create_time) "
								+ "values ('" + carHistoryList.get(j).getCarCode() + "',"
								+ carHistoryList.get(j).getLongitude() + "," + carHistoryList.get(j).getLatitude()
								+ ",NOW()) ");
					}
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("固废车辆昨天-今天所有轨迹查询执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		}

	}

	
	public static void main(String[] args) throws SQLException {
		WhgfCarTask task = new WhgfCarTask();
		task.getWhgfCarPositionTask();
	}


}
