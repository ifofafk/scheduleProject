package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.dto.WcRealTimeParamsDTO;
import cn.com.jxTec.schedulePro.entity.FtInteWcInfo;
import cn.com.jxTec.schedulePro.entity.FtInteWcRealtimeData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * 智能公厕相关接口调用: 智能公厕信息
 *				       智能公厕实时监测-人流值
 *				       智能公厕实时监测-臭气值
 *				       智能公厕历史记录-人流值
 *				       智能公厕历史记录-臭气值
 * 
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明
 */
public class FthwWCTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * !!先不跑出所有公厕，实际只有8个是智能公厕。!!
	 * 获取所有智能公厕信息 (会影响到后面查实时 的任务) 每天凌晨
	 * 目前对应qkcg_demo库需要更新 specialty_sys_list、specialty_sys_gps_device_list
	 * 
	 * @throws SQLException
	 * 注意：如果车的数量增加了，参考固废车辆，需要将一次http分多次
	 */
	public void getFthwWCList() throws SQLException {
		
		long startLong = System.currentTimeMillis();
		
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/wcInfo/getWcList.smvc";
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

		Gson g = new Gson();
		List<FtInteWcInfo> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			//解析好的智能公厕信息列表
			list = g.fromJson(jetemp, new TypeToken<List<FtInteWcInfo>>() {
			}.getType());
			
		}

		//1.查询本系统智能公厕编号
		String gcSql = " select cust_attr_2 from specialty_sys_list where type='智能公厕' ";
		List<String> gcCodes = jdbcTemplate.queryForList(gcSql, String.class);
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			FtInteWcInfo sourceWc = null;
			
			String sql = "";// 更新data_futai_car_info
			
			for (int i = 0; i < list.size(); i++) {
				sourceWc = list.get(i);
				if(sourceWc == null){
					continue;//继续下一轮
				}
				
				String jingdu = sourceWc.getDePositionStr()!=null?sourceWc.getDePositionStr().split(",")[0]: "0";
				String weidu = sourceWc.getDePositionStr()!=null?sourceWc.getDePositionStr().split(",")[1]: "0";

				sql = " insert into specialty_sys_list (createtime,sites_or_unit,type,title,sites_jingdu,sites_weidu,cust_attr_2,cust_attr_10) "
						+ " values (NOW(),0,'智能公厕','"+sourceWc.getJcssName()+"'," + jingdu + "," + weidu 
						+ ",'"+sourceWc.getJcssCode()+"','"+sourceWc.getDescription()+"')";
				
				
				if (gcCodes != null && gcCodes.size() > 0) {
					for (String gcCode : gcCodes) {
						if (gcCode.equals(sourceWc.getJcssCode())) {
						
//							sql = " update specialty_sys_list set cust_attr_10='"
//									+ sourceWc.getDescription() + "',update_time=now() where cust_attr_2='" + gcCode + "'";
							sql = "";
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
		System.out.println("硚口环卫-查询公厕信息执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	

	
	
	/**
	 * 硚口环卫-查询公厕人流当前值
	 * specialty_wc_list_history
	 * @param carIds
	 * @throws SQLException
	 * 时效性：目前耗时5-8s  联想E450
	 */
	public void getWhhwWcNowPersonNumTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/wcInfo/getWashRoomRealtimeData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";// 测试key
		String dataType = "PersonNum";
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startDate = sdf.format(cal.getTime());
	
		String endDate = sdf.format(nowDate);
		
		String beforeStr = "dataType=" + dataType + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		WcRealTimeParamsDTO wcRealTimeParamsDTO = new WcRealTimeParamsDTO();
		wcRealTimeParamsDTO.setDataType(dataType);
		wcRealTimeParamsDTO.setStartDate(startDate);
		wcRealTimeParamsDTO.setEndDate(endDate);
		wcRealTimeParamsDTO.setSign(sign);
		String paramValue = wcRealTimeParamsDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}
		
		Gson g = new Gson();
		List<FtInteWcRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtInteWcRealtimeData>>() {
			}.getType());
		}
		
		// 做插入或者更新判断获取本系统所有公厕编号
		String wcCodeSql = " select cust_attr_2 from specialty_sys_list where type='智能公厕' ";
		List<String> wcCodeList = jdbcTemplate.queryForList(wcCodeSql, String.class);
	
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			String sql = "";
			String sql1 = "";//历史记录
			FtInteWcRealtimeData ftInteWcRealtimeData = null;
			for (int i = 0; i < list.size(); i++) {
				ftInteWcRealtimeData = list.get(i);
				
			
//				
				/*
				 *	cust_attr_1	序号
				 *	cust_attr_2	编号
				 *	cust_attr_3	臭气值
				 *	cust_attr_4	人流量
				 */
				sql = " insert into specialty_sys_list (createtime,sites_or_unit,type,title,cust_attr_2,cust_attr_4) values "
						+ " (NOW(),0,'智能公厕','" + ftInteWcRealtimeData.getGcName() + "','" 
						+ ftInteWcRealtimeData.getGcCode() + "','" + ftInteWcRealtimeData.getValue() + "人次/天')";
				
				
//				//第一种方案，无限存history
				//截止时间字符串
//				Date d = new Date(ftInteWcRealtimeData.getEndDate());
//				String endDateStr = String.format("%tF %tT",d,d);
//				sql1 = " insert into specialty_wc_list_history (wc_id,wc_title,type,wc_float,data_time,create_time) values "
//						+ " ('"+ftInteWcRealtimeData.getGcCode()+"','"+ftInteWcRealtimeData.getGcName()+"',1,"
//						+ ftInteWcRealtimeData.getValue() + ",str_to_date('" + endDateStr
//						+ "','%Y-%m-%d %H:%i:%s'),now()) ";

//				//第二种方案实时查询算  人次/小时，
				if(wcCodeList != null && !wcCodeList.isEmpty()){
					for(String wcCode : wcCodeList) {
						if(wcCode.equals(ftInteWcRealtimeData.getGcCode())){
							sql = " update specialty_sys_list set cust_attr_4='" + ftInteWcRealtimeData.getValue()
									+ "人次/天',update_time=now() where type='智能公厕' and cust_attr_2='" +wcCode+ "' ";
						}
					}
				}
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
		
		System.out.println("硚口环卫-查询公厕人流当前值-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		
	}

	/**
	 * 硚口环卫-查询公厕臭气当前值
	 * 
	 * @param carIds
	 * @throws SQLException
	 * 时效性：目前耗时5-8s  联想E450
	 */
	public void getWhhwWcNowOdorTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/wcInfo/getWashRoomRealtimeData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";// 测试key
		String dataType = "OdorValue";
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startDate = sdf.format(cal.getTime());
		String endDate = sdf.format(nowDate);
		String beforeStr = "dataType=" + dataType + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		WcRealTimeParamsDTO wcRealTimeParamsDTO = new WcRealTimeParamsDTO();
		wcRealTimeParamsDTO.setDataType(dataType);
		wcRealTimeParamsDTO.setStartDate(startDate);
		wcRealTimeParamsDTO.setEndDate(endDate);
		wcRealTimeParamsDTO.setSign(sign);
		String paramValue = wcRealTimeParamsDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}
		
		Gson g = new Gson();
		List<FtInteWcRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtInteWcRealtimeData>>() {
			}.getType());
			
		}
		// 做插入或者更新判断获取本系统所有公厕编号
		String wcCodeSql = " select cust_attr_2 from specialty_sys_list where type='智能公厕' ";
		List<String> wcCodeList = jdbcTemplate.queryForList(wcCodeSql, String.class);
	
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			String sql = "";
			FtInteWcRealtimeData ftInteWcRealtimeData = null;
			for (int i = 0; i < list.size(); i++) {
				ftInteWcRealtimeData = list.get(i);
				
				/*
				 *	cust_attr_1	序号
				 *	cust_attr_2	编号
				 *	cust_attr_3	臭气值
				 *	cust_attr_4	人流量
				 */
				sql = " insert into specialty_sys_list (createtime,sites_or_unit,type,title,cust_attr_2,cust_attr_3) values "
						+ " (NOW(),0,'智能公厕','" + ftInteWcRealtimeData.getGcName() + "','" 
						+ ftInteWcRealtimeData.getGcCode() + "','" + ftInteWcRealtimeData.getValue() + "')";
				
				//第一种方案，无限存history
//				Date d = new Date(ftInteWcRealtimeData.getEndDate());//时间是long
//				sql = " insert into specialty_wc_list_history (wc_id,wc_title,type,wc_float,data_time,create_time) values "
//						+ " ('"+ftInteWcRealtimeData.getGcCode()+"','"+ftInteWcRealtimeData.getGcName()+"',0,"
//						+ ftInteWcRealtimeData.getValue() + ",str_to_date('" + String.format("%tF %tT",d,d)
//						+ "','%Y-%m-%d %H:%i:%s'),now()) ";
				
//				//第二种方案，实时臭气值
				if(wcCodeList != null && !wcCodeList.isEmpty()){
					for(String wcCode : wcCodeList) {
						if(wcCode.equals(ftInteWcRealtimeData.getGcCode())){
							sql = " update specialty_sys_list set cust_attr_3='" + ftInteWcRealtimeData.getValue()
									+ "',update_time=now() where type='智能公厕' and cust_attr_2='" +wcCode+ "' ";
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
		System.out.println("硚口环卫-查询公厕臭气当前值-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		
	}
	
	
	///////目前历史数据只有一张表specialty_wc_list_history可用////////////////////////////////////////////////////////////////////////////////
	/**
	 * 硚口环卫-查询公厕人流历史值
	 * 
	 * @param carIds
	 * @throws SQLException
	 * 时效性：目前耗时5-8s  联想E450
	 */
	public void getWhhwWcHistoryPersonNumTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/wcInfo/getWashRoomHistoryData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";// 测试key
		String dataType = "PersonNum";
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 3);
		String startDate = sdf.format(cal.getTime());
		String endDate = sdf.format(nowDate);
		String beforeStr = "dataType=" + dataType + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		WcRealTimeParamsDTO wcRealTimeParamsDTO = new WcRealTimeParamsDTO();
		wcRealTimeParamsDTO.setDataType(dataType);
		wcRealTimeParamsDTO.setStartDate(startDate);
		wcRealTimeParamsDTO.setEndDate(endDate);
		wcRealTimeParamsDTO.setSign(sign);
		String paramValue = wcRealTimeParamsDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}
		
		//调用伏泰接口返回的数据
		Gson g = new Gson();
		List<FtInteWcRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtInteWcRealtimeData>>() {
			}.getType());
		}
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			String sql = "";
			FtInteWcRealtimeData ftInteWcRealtimeData = null;
			for (int i = 0; i < list.size(); i++) {
				ftInteWcRealtimeData = list.get(i);
				Date d = new Date(ftInteWcRealtimeData.getEndDate());//时间是long
				sql = " insert into specialty_wc_list_history (wc_id,wc_title,type,wc_float,data_time,create_time) values "
						+ " ('"+ftInteWcRealtimeData.getGcCode()+"','"+ftInteWcRealtimeData.getGcName()+"',1,"
						+ ftInteWcRealtimeData.getValue() + ",str_to_date('" + String.format("%tF %tT",d,d)
						+ "','%Y-%m-%d %H:%i:%s'),now()) ";

				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
		}
		System.out.println("硚口环卫-查询公厕人流值历史记录-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		
	}
	
	
	/**
	 * 硚口环卫-查询公厕臭气历史值
	 * 
	 * @param carIds
	 * @throws SQLException
	 * 时效性：目前耗时5-8s  联想E450
	 */
	public void getWhhwWcHistoryOdorTask() throws SQLException {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/wcInfo/getWashRoomHistoryData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";// 测试key
		String dataType = "OdorValue";
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 3);
		String startDate = sdf.format(cal.getTime());
		String endDate = sdf.format(nowDate);
		String beforeStr = "dataType=" + dataType + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		WcRealTimeParamsDTO wcRealTimeParamsDTO = new WcRealTimeParamsDTO();
		wcRealTimeParamsDTO.setDataType(dataType);
		wcRealTimeParamsDTO.setStartDate(startDate);
		wcRealTimeParamsDTO.setEndDate(endDate);
		wcRealTimeParamsDTO.setSign(sign);
		String paramValue = wcRealTimeParamsDTO.toString();
		String result = "没有结果";

		try {
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}
		
		//调用伏泰接口返回的数据
		Gson g = new Gson();
		List<FtInteWcRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtInteWcRealtimeData>>() {
			}.getType());
		}
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			String sql = "";
			FtInteWcRealtimeData ftInteWcRealtimeData = null;
			for (int i = 0; i < list.size(); i++) {
				ftInteWcRealtimeData = list.get(i);
				Date d = new Date(ftInteWcRealtimeData.getEndDate());//时间是long
				//无限存history
				sql = " insert into specialty_wc_list_history (wc_id,wc_title,type,wc_float,data_time,create_time) values "
						+ " ('"+ftInteWcRealtimeData.getGcCode()+"','"+ftInteWcRealtimeData.getGcName()+"',0,"
						+ ftInteWcRealtimeData.getValue() + ",str_to_date('" + String.format("%tF %tT",d,d)
						+ "','%Y-%m-%d %H:%i:%s'),now()) ";
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
		}
		System.out.println("硚口环卫-查询公厕臭气值历史记录-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		
	}
	
	public static void main(String[] args) throws SQLException {
//		FthwWCTask a = new FthwWCTask();
		//公厕信息列表
//		a.getFthwWCList();
		//公厕实时人流量
//		a.getWhhwWcNowPersonNumTask();
		//公厕实时臭气值
//		a.getWhhwWcNowOdorTask();
	
	}

}
