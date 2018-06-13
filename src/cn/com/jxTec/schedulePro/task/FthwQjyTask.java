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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.entity.FtQjyInfo;
import cn.com.jxTec.schedulePro.entity.FtQjyRealtimeData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * 硚口城管V1.0
 * 				获取清洁员信息
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明
 * 参考硚口城管
 */
public class FthwQjyTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取清洁员信息
	 * 
	 * @throws SQLException
	 */
	public void getFthwQjyInfoList() throws SQLException {
		
		long startLong = System.currentTimeMillis();
		
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/qjyInfo/getStaffList.smvc";
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
		List<FtQjyInfo> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtQjyInfo>>() {
			}.getType());
			
		}
		
		//1.查询本系统清洁员
		String qjySql = " select futai_staff_id staffId,futai_code staffCode,name staffName, gender,phone from think_user_bak where duty='环卫清洁员' ";
		List<FtQjyInfo> qjyList = jdbcTemplate.query(qjySql, new Object[] {},
				new BeanPropertyRowMapper<FtQjyInfo>(FtQjyInfo.class));
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			FtQjyInfo sourceQjy = null;
			
			String sql = "";// 更新data_futai_car_info
			
			for (int i = 0; i < list.size(); i++) {
				sourceQjy = list.get(i);
				if(sourceQjy == null){
					continue;//继续下一轮
				}

				sql = " insert into think_user_bak (account,password,name,gender,"
					+ " duty,create_time, update_time,phone,futai_code,futai_staff_id,status) "
					+ " values ('"+sourceQjy.getStaffName()+"','123456789','"+sourceQjy.getStaffName()
					+ "','"+sourceQjy.getGender() + "','环卫清洁员',now(),now(),'"+sourceQjy.getPhone()
					+ "','"+sourceQjy.getStaffCode()+"','" + sourceQjy.getStaffId()+ "',0)";
				
				if (qjyList != null && qjyList.size() > 0) {
					for (FtQjyInfo qjy : qjyList) {
						if (qjy.getStaffId().equals(sourceQjy.getStaffId())) {
//							sql = " update think_user_bak set futai_code,name, staffName, gender,phone "
//									+ " where futai_staff_id='" + qjy.getStaffId() + "'";
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
		System.out.println("硚口环卫-查询清洁员信息执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	
	
	/**
	 * 获取清洁员实时位置相关信息
	 * 
	 * @throws SQLException
	 */
	public void getFthwQjyRealtime() throws SQLException {
		
		long startLong = System.currentTimeMillis();
		
		String staffIds = "";//伏泰清洁员id  可,拼接
		String workElementIds = "";//作业区段ID   可,拼接
		
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/qjyInfo/getStaffRealtimeData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";
		
		String sign = MD5Utils.string2MD5("staffIds=" + staffIds + "&workElementIds=" + workElementIds + "&" + key).toUpperCase();
		String paramValue = "{\"staffIds\":\"" + staffIds + "\","
				+ "\"workElementIds\":\"" + workElementIds + "\","
				+ "\"sign\":\"" + sign + "\"}";

		String result = "没有结果";
		try {
			result = HttpClientUtil.executeGetMethod4API(requestUrl, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("没有结果");
		}

		//解析后的源数据
		Gson g = new Gson();
		List<FtQjyRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtQjyRealtimeData>>() {
			}.getType());
			
		}
		
		//1.查询本系统清洁员
//		String qjySql = " select futai_staff_id staffId,futai_code staffCode,name staffName, gender,phone from think_user_bak where duty='环卫清洁员' ";
//		List<FtQjyInfo> qjyList = jdbcTemplate.query(qjySql, new Object[] {},
//				new BeanPropertyRowMapper<FtQjyInfo>(FtQjyInfo.class));
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			FtQjyRealtimeData sourceQjy = null;
			
			String sql = "";
			
			for (int i = 0; i < list.size(); i++) {
				sourceQjy = list.get(i);
				if(sourceQjy == null){
					continue;//继续下一轮
				}

				
				sql = " insert into data_futai_qjy_position_info (staff_id,staff_name,staff_phone,"
						+ " work_type_code,work_type_name,dept_id,dept_name,device_type_code,"
						+ " device_type_name,gps_time,lngdone,latdone,address,steps,heart_rate,"
						+ " shift_id,shift_name,shift_start_time,shift_end_time,"
						+ " shift_work_elementnames,onwork_element_id,onwork_element_name,"
						+ " status_code,status_name,create_time ) values "
						+ " ('" + sourceQjy.getStaffId() + "','"+ sourceQjy.getStaffName() + "','" + sourceQjy.getStaffPhone()
						+ "','" + sourceQjy.getWorkTypeCode() + "','" + sourceQjy.getWorkTypeName() + "','" + sourceQjy.getDeptId() 
						+ "','" + sourceQjy.getDeptName() + "','" + sourceQjy.getDeviceTypeCode() + "','" + sourceQjy.getDeviceTypeName() 
						+ "'," + sourceQjy.getGpsTime() + "," + sourceQjy.getLngDone() + "," + sourceQjy.getLatDone() 
						+ ",'" + sourceQjy.getAddress() + "'," + sourceQjy.getSteps() + "," + sourceQjy.getHeartRate() 
						+ ",'" + sourceQjy.getShiftId() + "','" + sourceQjy.getShiftName() + "'," + sourceQjy.getShiftStartTime() 
						+ "," + sourceQjy.getShiftEndTime() + ",'" + sourceQjy.getShiftWorkElementNames() + "','" + sourceQjy.getOnWorkElementId() 
						+ "','" + sourceQjy.getOnWorkElementName() + "','" + sourceQjy.getStatusCode() + "','" + sourceQjy.getStatusName() + "',now())";
				
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			
		}
		System.out.println("硚口环卫-查询清洁员实时位置执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}

	/**
	 * 获取清洁员历史位置相关信息
	 * 
	 * @throws SQLException
	 */
	public void getFthwQjyHistory() throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = System.currentTimeMillis();
		
		String staffIds = "";//伏泰清洁员id  可,拼接
		
		// 开始日期   必填，毫秒数
		Date endTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(endTime);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		// 结束日期    必填，毫秒数
		Date startTime = cal.getTime();
		// 必填，false，则查排班内的；传true，则查所有的（排班内+排班外）
		boolean isShowAll = false;
		
		String requestUrl = "http://219.140.178.212:9391/cloud/qk/api/np/v1/qjyInfo/getStaffHistoryData.smvc";
		String paramKey = "parameters=";
		String key = "key=incomtest";
		
		String sign = MD5Utils.string2MD5("staffIds=" + staffIds + "&isShowAll=" + isShowAll
				+ "&startTime=" + startTime.getTime()  + "&endTime=" + endTime.getTime() + "&" + key).toUpperCase();
		String paramValue = "{\"staffIds\":\"" + staffIds + "\","
				+ "\"isShowAll\":\"" + isShowAll + "\","
				+ "\"startTime\":\"" + startTime.getTime() + "\","
				+ "\"endTime\":\"" + endTime.getTime() + "\","
				+ "\"sign\":\"" + sign + "\"}";

		String result = "没有结果";
		try {
			result = HttpClientUtil.executeGetMethod4API(requestUrl, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("没有结果");
		}

		//解析后的源数据    复用清洁员实时位置的实体
		Gson g = new Gson();
		List<FtQjyRealtimeData> list = null;
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data");
		if(je_temp2 != null && !je_temp2.isJsonNull()){
			JsonArray jetemp = je_temp2.getAsJsonArray();
			list = g.fromJson(jetemp, new TypeToken<List<FtQjyRealtimeData>>() {
			}.getType());
			
		}
		
		//1.查询本系统清洁员
//		String qjySql = " select futai_staff_id staffId,futai_code staffCode,name staffName, gender,phone from think_user_bak where duty='环卫清洁员' ";
//		List<FtQjyInfo> qjyList = jdbcTemplate.query(qjySql, new Object[] {},
//				new BeanPropertyRowMapper<FtQjyInfo>(FtQjyInfo.class));
		
		if (list != null && !list.isEmpty()) {
			List<String> sqls = new ArrayList<>();
			FtQjyRealtimeData sourceQjy = null;
			
			String sql = "";
			
			for (int i = 0; i < list.size(); i++) {
				sourceQjy = list.get(i);
				if(sourceQjy == null){
					continue;//继续下一轮
				}

				sql = " insert into data_futai_qjy_position_info (staff_id,staff_name,staff_phone,"
						+ " work_type_code,work_type_name,dept_id,dept_name,device_type_code,"
						+ " device_type_name,gps_time,lngdone,latdone,address,steps,heart_rate,"
						+ " shift_id,shift_name,shift_start_time,shift_end_time,"
						+ " shift_work_elementnames,onwork_element_id,onwork_element_name,"
						+ " status_code,status_name,create_time ) values ('" 
						+ sourceQjy.getStaffId() + "','"+ sourceQjy.getStaffName() + "','" + sourceQjy.getStaffPhone() + "','" 
						+ sourceQjy.getWorkTypeCode() + "','" + sourceQjy.getWorkTypeName() + "','" + sourceQjy.getDeptId() + "','" 
						+ sourceQjy.getDeptName() + "','" + sourceQjy.getDeviceTypeCode() + "','" + sourceQjy.getDeviceTypeName() + "'," 
						+ sourceQjy.getGpsTime() + "," + sourceQjy.getLngDone() + "," + sourceQjy.getLatDone() + ",'" 
						+ sourceQjy.getAddress() + "'," + sourceQjy.getSteps() + "," + sourceQjy.getHeartRate() + ",'" 
						+ sourceQjy.getShiftId() + "','" + sourceQjy.getShiftName() + "'," + sourceQjy.getShiftStartTime() + "," 
						+ sourceQjy.getShiftEndTime() + ",'" + sourceQjy.getShiftWorkElementNames() + "','" + sourceQjy.getOnWorkElementId() + "','" 
						+ sourceQjy.getOnWorkElementName() + "','" + sourceQjy.getStatusCode() + "','" + sourceQjy.getStatusName() + "',now())";
				
				if(StringUtils.isNotBlank(sql)){
					sqls.add(sql);
				}
			}
			
			if(sqls.size() > 0){
				jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
			}
			
		}
		System.out.println("硚口环卫-" + sdf.format(startTime) + "至" + sdf.format(endTime) + "清洁员历史位置执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
	}
	
	
	
	public static void main(String[] args) throws SQLException {
		FthwQjyTask test = new FthwQjyTask();
		test.getFthwQjyHistory();
		
	}


}
