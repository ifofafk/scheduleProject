package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.dto.InteWcPersonNumDTO;
import cn.com.jxTec.schedulePro.entity.FtInteWcOdor;
import cn.com.jxTec.schedulePro.entity.FtInteWcPersonNum;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * 智能公厕-报表-相关接口调用
 * 
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明
 */
public class FthwReportInteWCTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * ========== 报表 √============ 
	 * 智能公厕-人流量报表
	 */
	public void getFthwWcPersonNumTask() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/washRoomRptInfo/getPersonNum.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startDate = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endDate = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";
		InteWcPersonNumDTO wcPersonNumDTO = new InteWcPersonNumDTO();// 请求参数
		String beforeStr = "userId=" + userId + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		wcPersonNumDTO.setUserId(userId);
		wcPersonNumDTO.setStartDate(startDate);
		wcPersonNumDTO.setEndDate(endDate);
		wcPersonNumDTO.setSign(sign);
		String paramValue = wcPersonNumDTO.toString();

		
		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}
		Gson g = new Gson();
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("rows");
		
		List<FtInteWcPersonNum> ftInteWcPersonNumList = null;
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			// 返回结果
			ftInteWcPersonNumList = g.fromJson(jetemp, new TypeToken<List<FtInteWcPersonNum>>() {
			}.getType());
		}

		if (ftInteWcPersonNumList != null && !ftInteWcPersonNumList.isEmpty()) {
			
			//插入数据
			int ftInteWcPersonNumListSize = ftInteWcPersonNumList.size();
			List<String> sqls = new ArrayList<>();// 最终sql
			
			for (int i = 0; i < ftInteWcPersonNumListSize; i++) {
				sqls.add(" insert into data_futai_wc_report (report_name,sum_caculate_value,squat_num,"
						+ " classes_name,manage_unit_name,name,address,code,wc_id,create_time,data_time,avg_value) "
						+ " values ('人流量统计报表'," + ftInteWcPersonNumList.get(i).getSumCaculateValue() + "," 
						+ ftInteWcPersonNumList.get(i).getSquatNum() + ",'" + ftInteWcPersonNumList.get(i).getClassesName() + "','"
						+ ftInteWcPersonNumList.get(i).getManageUnitName() + "','" + ftInteWcPersonNumList.get(i).getName() + "','" 
						+ ftInteWcPersonNumList.get(i).getAddress() + "','" + ftInteWcPersonNumList.get(i).getCode() + "','"
						+ ftInteWcPersonNumList.get(i).getId() + "', now(), now()," + ftInteWcPersonNumList.get(i).getSumCaculateValue()/24 + ")");
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("智能公厕-人流量统计报表==执行完毕");
		}
	}

	/*
	 * ========== 报表 √============ 
	 * 智能公厕-臭气值统计报表   请求参数一模一样
	 * 
	 */
	public void getFthwWcOdorTask() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/washRoomRptInfo/getOdor.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 3);
		String startDate = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endDate = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";
		InteWcPersonNumDTO wcPersonNumDTO = new InteWcPersonNumDTO();// 请求参数
		String beforeStr = "userId=" + userId + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		wcPersonNumDTO.setUserId(userId);
		wcPersonNumDTO.setStartDate(startDate);
		wcPersonNumDTO.setEndDate(endDate);
		wcPersonNumDTO.setSign(sign);
		String paramValue = wcPersonNumDTO.toString();

		Gson g = new Gson();
		List<FtInteWcOdor> ftInteWcOdorList = null;// 返回结果

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("FIRST").getAsJsonObject()
				.get("rows");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			ftInteWcOdorList = g.fromJson(jetemp, new TypeToken<List<FtInteWcOdor>>() {
			}.getType());
		}
		//警告数据
		List<Map<String,Object>> ftInteWcAlarmList = null;
		JsonElement je_temp_alarms = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("SECOND").getAsJsonObject()
				.get("rows");
		if(je_temp_alarms != null && !je_temp_alarms.isJsonNull()){
			JsonArray jetemp_alarms = je_temp_alarms.getAsJsonArray();
			ftInteWcAlarmList = g.fromJson(jetemp_alarms, new TypeToken<List<Map<String,Object>>>() {
			}.getType());
			
		}



		if (ftInteWcOdorList != null && !ftInteWcOdorList.isEmpty()) {
			
			int ftInteWcOdorListSize = ftInteWcOdorList.size();
			List<String> sqls = new ArrayList<>();// 最终sql
			String sql = "";
			int alarmCounts = 0;//警告数默认0
			for (int i = 0; i < ftInteWcOdorListSize; i++) {
				//对应公厕的警告数
				if(ftInteWcAlarmList != null && !ftInteWcAlarmList.isEmpty()){
					for(Map<String,Object> m : ftInteWcAlarmList){
						if(StringUtils.isNotBlank(ftInteWcOdorList.get(i).getId()) 
								&& ftInteWcOdorList.get(i).getId().equals(m.get("f_baseInfo_id"))){
							alarmCounts= m.get("count") != null?(int) Math.ceil((Double)m.get("count")) : 0;
						}
					}
				}
				
				// 注意code对应specialty_sys_list的cust_attr_2 type=1标识人流 0是臭气
				sql = " insert into data_futai_wc_report (report_name,classes_name,"
						+ " manage_unit_name,name,address,code,wc_id,avg_value,max_value,create_time,data_time,alarm_count) "
						+ " values ('臭气值统计报表','" + ftInteWcOdorList.get(i).getClassesName() + "','"
						+ ftInteWcOdorList.get(i).getManageUnitName() + "','" + ftInteWcOdorList.get(i).getName() + "','" 
						+ ftInteWcOdorList.get(i).getAddress() + "','" + ftInteWcOdorList.get(i).getCode() + "','" 
						+ ftInteWcOdorList.get(i).getId() + "'," + ftInteWcOdorList.get(i).getaValue() + "," 
						+ ftInteWcOdorList.get(i).getmValue() + ", now(), now()," + alarmCounts + ")";
				
				
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("智能公厕-臭气值统计报表=执行完毕");
		}

	}

	
	/*
	 * ========== 报表 √============ 
	 * 智能公厕-温度统计报表 请求参数一模一样
	 * 因为伏泰传感器还未装上，先保持数据一致
	 */
	public void getFthwWcTemperatureTask() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/washRoomRptInfo/getTemperature.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startDate = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endDate = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";
		InteWcPersonNumDTO wcPersonNumDTO = new InteWcPersonNumDTO();// 请求参数
		String beforeStr = "userId=" + userId + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		wcPersonNumDTO.setUserId(userId);
		wcPersonNumDTO.setStartDate(startDate);
		wcPersonNumDTO.setEndDate(endDate);
		wcPersonNumDTO.setSign(sign);
		String paramValue = wcPersonNumDTO.toString();

		Gson g = new Gson();
		List<FtInteWcOdor> ftInteWcOdorList = null;// 返回结果

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("FIRST").getAsJsonObject()
				.get("rows");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			ftInteWcOdorList = g.fromJson(jetemp, new TypeToken<List<FtInteWcOdor>>() {
			}.getType());
			
		}
		
		//警告数据
		List<Map<String,Object>> ftInteWcAlarmList = null;
		JsonElement je_temp_alarms = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("SECOND").getAsJsonObject()
				.get("rows");
		if(je_temp_alarms != null && !je_temp_alarms.isJsonNull()){
			JsonArray jetemp_alarms = je_temp_alarms.getAsJsonArray();
			ftInteWcAlarmList = g.fromJson(jetemp_alarms, new TypeToken<List<Map<String,Object>>>() {
			}.getType());
			
		}


		if (ftInteWcOdorList != null && !ftInteWcOdorList.isEmpty()) {
			
			int ftInteWcOdorListSize = ftInteWcOdorList.size();
			List<String> sqls = new ArrayList<>();// 最终sql
			String sql = "";
			int alarmCounts = 0;//警告数默认0
			
			for (int i = 0; i < ftInteWcOdorListSize; i++) {
				//对应公厕的警告数
				if(ftInteWcAlarmList != null && !ftInteWcAlarmList.isEmpty()){
					for(Map<String,Object> m : ftInteWcAlarmList){
						if(StringUtils.isNotBlank(ftInteWcOdorList.get(i).getId()) 
								&& ftInteWcOdorList.get(i).getId().equals(m.get("f_baseInfo_id"))){
//							alarmCounts= m.get("count") != null? (Integer)m.get("count") : 0;
							alarmCounts= m.get("count") != null?(int) Math.ceil((Double)m.get("count")) : 0;
						}
					}
				}
				
				// 注意code对应specialty_sys_list的cust_attr_2 type=1标识人流 0是臭气
				sql = " insert into data_futai_wc_report (report_name,classes_name,"
						+ " manage_unit_name,name,address,code,wc_id,avg_value,max_value,create_time,data_time,alarm_count) "
						+ " values ('温度统计报表','" + ftInteWcOdorList.get(i).getClassesName() + "','"
						+ ftInteWcOdorList.get(i).getManageUnitName() + "','" + ftInteWcOdorList.get(i).getName() + "','" 
						+ ftInteWcOdorList.get(i).getAddress() + "','" + ftInteWcOdorList.get(i).getCode() + "','" 
						+ ftInteWcOdorList.get(i).getId() + "'," + ftInteWcOdorList.get(i).getaValue() + "," 
						+ ftInteWcOdorList.get(i).getmValue() + ", now(), now()," + alarmCounts + ") ";
				
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("智能公厕-温度统计报表=执行完毕");
		}

	}

	
	
	/*
	 * ========== 报表 √============ 
	 * 智能公厕-湿度统计报表 请求参数一模一样
	 * 
	 */
	public void getFthwWcHumidityTask() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/washRoomRptInfo/getHumidity.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String startDate = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String endDate = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";
		InteWcPersonNumDTO wcPersonNumDTO = new InteWcPersonNumDTO();// 请求参数
		String beforeStr = "userId=" + userId + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		wcPersonNumDTO.setUserId(userId);
		wcPersonNumDTO.setStartDate(startDate);
		wcPersonNumDTO.setEndDate(endDate);
		wcPersonNumDTO.setSign(sign);
		String paramValue = wcPersonNumDTO.toString();

		Gson g = new Gson();
		List<FtInteWcOdor> ftInteWcOdorList = null;// 返回结果

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("FIRST").getAsJsonObject()
				.get("rows");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			ftInteWcOdorList = g.fromJson(jetemp, new TypeToken<List<FtInteWcOdor>>() {
			}.getType());
		}
		
		//警告数据
		List<Map<String,Object>> ftInteWcAlarmList = null;
		JsonElement je_temp_alarms = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("SECOND").getAsJsonObject()
				.get("rows");
		if(je_temp_alarms != null && !je_temp_alarms.isJsonNull()){
			JsonArray jetemp_alarms = je_temp_alarms.getAsJsonArray();
			ftInteWcAlarmList = g.fromJson(jetemp_alarms, new TypeToken<List<Map<String,Object>>>() {
			}.getType());
			
		}


		if (ftInteWcOdorList != null && !ftInteWcOdorList.isEmpty()) {
			
			int ftInteWcOdorListSize = ftInteWcOdorList.size();
			List<String> sqls = new ArrayList<>();// 最终sql
			String sql = "";
			int alarmCounts = 0;//警告数默认0
			
			for (int i = 0; i < ftInteWcOdorListSize; i++) {
				//对应公厕的警告数
				if(ftInteWcAlarmList != null && !ftInteWcAlarmList.isEmpty()){
					for(Map<String,Object> m : ftInteWcAlarmList){
						if(StringUtils.isNotBlank(ftInteWcOdorList.get(i).getId()) 
								&& ftInteWcOdorList.get(i).getId().equals(m.get("f_baseInfo_id"))){
//							alarmCounts= m.get("count") != null? (Integer)m.get("count") : 0;
							alarmCounts= m.get("count") != null?(int) Math.ceil((Double)m.get("count")) : 0;
						}
					}
				}
				
				// 注意code对应specialty_sys_list的cust_attr_2 type=1标识人流 0是臭气
				sql = " insert into data_futai_wc_report (report_name,classes_name,"
						+ " manage_unit_name,name,address,code,wc_id,avg_value,max_value,create_time,data_time,alarm_count) "
						+ " values ('湿度统计报表','" + ftInteWcOdorList.get(i).getClassesName() + "','"
						+ ftInteWcOdorList.get(i).getManageUnitName() + "','" + ftInteWcOdorList.get(i).getName() + "','" 
						+ ftInteWcOdorList.get(i).getAddress() + "','" + ftInteWcOdorList.get(i).getCode() + "','" 
						+ ftInteWcOdorList.get(i).getId() + "'," + ftInteWcOdorList.get(i).getaValue() + "," 
						+ ftInteWcOdorList.get(i).getmValue() + ", now(), now(), " + alarmCounts + ")";
				
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			System.out.println("智能公厕-湿度统计报表=执行完毕");
		}

	}

	
	// 测试
	public static void main(String[] args) {

	}

}
