package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.com.jxTec.schedulePro.entity.FthwReportLDZYRBData;
import cn.com.jxTec.schedulePro.entity.FthwReportRYkqtjData;
import cn.com.jxTec.schedulePro.entity.FthwReportRYlctjData;
import cn.com.jxTec.schedulePro.entity.FthwReportRYsostjData;
import cn.com.jxTec.schedulePro.entity.FthwReportRYwgtjData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明 硚口区报表
 */
public class FthwReportQjyTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 1.1 人员管理-违规统计
	 */
	public void getFthwReportRYWGTJTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 报表编码 必填，固定值RY_WGTJ人员管理 -违规统计
		String rptCode = "RY_WGTJ";
		String key = "key=incomtest";// 密钥
		// 选填 人员名,模糊匹配
		String staffName = "";

		// 日期 必填，yyyy-MM-dd 文档写的"startDate":"2018-04-10","endDate":"2018-04-10"
		Date nowDate = new Date();
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(nowDate);
		// cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		// String startDate = sdf.format(cal.getTime());//开始时间
		String endDate = sdf.format(nowDate);// 结束时间

		String result = "没有结果";

		List<FthwReportRYwgtjData> fthwReportRYwgtjList = null;// 违规统计

		String beforeStr = "staffName=" + staffName + "&" + "rptCode=" + rptCode + "&" + "startDate=" + endDate + "&"
				+ "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		String paramValue = "{\"staffName\":\"" + staffName + "\"," + "\"rptCode\":\"" + rptCode + "\","
				+ "\"startDate\":\"" + endDate + "\"," + "\"endDate\":\"" + endDate + "\"," + "\"sign\":\"" + sign
				+ "\"}";

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
		if (je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			fthwReportRYwgtjList = g.fromJson(jetemp, new TypeToken<List<FthwReportRYwgtjData>>() {
			}.getType());

		}

		if (fthwReportRYwgtjList != null && !fthwReportRYwgtjList.isEmpty()) {
			int fthwReportRYwgtjListSize = fthwReportRYwgtjList.size();
			String[] sqls = new String[fthwReportRYwgtjListSize];// 最终sql

			FthwReportRYwgtjData one = null;
			for (int i = 0; i < fthwReportRYwgtjListSize; i++) {
				one = fthwReportRYwgtjList.get(i);

				sqls[i] = " insert into data_futai_rywgtj_report ("
						+ " staff_name,dept_name,out_post_count,out_post_duration, "
						+ " exception_offLine_count,exception_offLine_duration, "
						+ " violation_stay_count,violation_stay_duration,total_count,create_time " + " ) values ('"
						+ one.getStaffName() + "','" + one.getDeptName() + "'," + one.getOutPostCount() + ","
						+ one.getExceptionOffLineCount() + "," + one.getExceptionOffLineDuration() + ","
						+ one.getViolationStayCount() + " ," + one.getViolationStayDuration() + ","
						+ one.getTotalCount() + "," + one.getTotalCount() + ",now())";
			}
			if (sqls.length > 0) {
				jdbcTemplate.batchUpdate(sqls);
			}
		}

		System.out.println("环卫-人员管理违规统计-执行完毕!" + " 耗时:  " + (System.currentTimeMillis() - startLong) / 1000 + "秒");
	}

	/**
	 * 1.2 里程统计
	 */
	public void getFthwReportRYLCTJTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 报表编码 必填
		String rptCode = "RY_LCTJ";
		String key = "key=incomtest";// 密钥
		// 选填 人员名,模糊匹配
		String staffName = "";

		// 日期 必填，yyyy-MM-dd 文档写的"startDate":"2018-04-10","endDate":"2018-04-10"
		Date nowDate = new Date();
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(nowDate);
		// cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		// String startDate = sdf.format(cal.getTime());//开始时间
		String endDate = sdf.format(nowDate);// 结束时间

		String result = "没有结果";

		List<FthwReportRYlctjData> fthwReportRYlctjList = null;// 里程统计

		String beforeStr = "staffName=" + staffName + "&" + "rptCode=" + rptCode + "&" + "startDate=" + endDate + "&"
				+ "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		String paramValue = "{\"staffName\":\"" + staffName + "\"," + "\"rptCode\":\"" + rptCode + "\","
				+ "\"startDate\":\"" + endDate + "\"," + "\"endDate\":\"" + endDate + "\"," + "\"sign\":\"" + sign
				+ "\"}";

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
		if (je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			fthwReportRYlctjList = g.fromJson(jetemp, new TypeToken<List<FthwReportRYlctjData>>() {
			}.getType());

		}

		if (fthwReportRYlctjList != null && !fthwReportRYlctjList.isEmpty()) {
			int fthwReportRYlctjListSize = fthwReportRYlctjList.size();
			String[] sqls = new String[fthwReportRYlctjListSize];// 最终sql

			FthwReportRYlctjData one = null;
			for (int i = 0; i < fthwReportRYlctjListSize; i++) {
				one = fthwReportRYlctjList.get(i);

				sqls[i] = " insert into data_futai_rylctj_report (" + " staff_name,dept_name,total_mileage,create_time "
						+ " ) values ('" + one.getStaffName() + "','" + one.getDeptName() + "'," + one.getTotalMileage()
						+ ",now())";
			}
			if (sqls.length > 0) {
				jdbcTemplate.batchUpdate(sqls);
			}
		}

		System.out.println("环卫-人员里程管理统计-执行完毕!" + " 耗时:  " + (System.currentTimeMillis() - startLong) / 1000 + "秒");
	}

	/**
	 * 1.3 考勤统计
	 */
	public void getFthwReportRYKQTJTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 报表编码 必填
		String rptCode = "RY_KQTJ";
		String key = "key=incomtest";// 密钥
		// 选填 人员名,模糊匹配
		String staffName = "";

		// 日期 必填，yyyy-MM-dd 文档写的"startDate":"2018-04-10","endDate":"2018-04-10"
		Date nowDate = new Date();
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(nowDate);
		// cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		// String startDate = sdf.format(cal.getTime());//开始时间
		String endDate = sdf.format(nowDate);// 结束时间

		String result = "没有结果";

		List<FthwReportRYkqtjData> fthwReportRYkqtjList = null;// 考勤统计

		String beforeStr = "staffName=" + staffName + "&" + "rptCode=" + rptCode + "&" + "startDate=" + endDate + "&"
				+ "endDate=" + endDate;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		String paramValue = "{\"staffName\":\"" + staffName + "\"," + "\"rptCode\":\"" + rptCode + "\","
				+ "\"startDate\":\"" + endDate + "\"," + "\"endDate\":\"" + endDate + "\"," + "\"sign\":\"" + sign
				+ "\"}";

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
		if (je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			fthwReportRYkqtjList = g.fromJson(jetemp, new TypeToken<List<FthwReportRYkqtjData>>() {
			}.getType());

		}

		if (fthwReportRYkqtjList != null && !fthwReportRYkqtjList.isEmpty()) {
			int fthwReportRYkqtjListSize = fthwReportRYkqtjList.size();
			String[] sqls = new String[fthwReportRYkqtjListSize];// 最终sql

			FthwReportRYkqtjData one = null;
			for (int i = 0; i < fthwReportRYkqtjListSize; i++) {
				one = fthwReportRYkqtjList.get(i);
				// 插入考勤统计sql
				sqls[i] = " insert into data_futai_rykqtj_report (" + " parent_dept_name,staff_name,attendance_date, "
						+ " dept_name,shift_name,shift_workelement_names,start_time,end_time, "
						+ " status_name,missing_times,late_times,early_times,create_time " + " ) values ('"
						+ one.getParentDeptName() + "','" + one.getStaffName() + "',str_to_date('"
						+ one.getAttendanceDate() + "','%Y-%m-%d'),'" + one.getDeptName() + "','" + one.getShiftName()
						+ "','" + one.getShiftWorkElementNames() + "',str_to_date('" + one.getStartTime()
						+ "','%Y-%m-%d %H:%i:%s'),str_to_date('" + one.getEndTime() + "','%Y-%m-%d %H:%i:%s'),'"
						+ one.getStatusName() + "'," + one.getMissingTimes() + " ," + one.getLateTimes() + " ,"
						+ one.getEarlyTimes() + " ,now())";
			}
			if (sqls.length > 0) {
				jdbcTemplate.batchUpdate(sqls);
			}
		}

		System.out.println("环卫-人员考勤统计-执行完毕!" + " 耗时:  " + (System.currentTimeMillis() - startLong) / 1000 + "秒");
	}

	/**
	 * 1.4 SOS短信发送次数统计
	 */
	public void getFthwReportRYSOSTJTask() {
		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 报表编码 必填
		String rptCode = "RY_SOS";
		String key = "key=incomtest";// 密钥
		// 选填 人员名,模糊匹配
		String staffName = "";

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		String result = "没有结果";

		List<FthwReportRYsostjData> fthwReportRYsostjList = null;// 人员SOS统计

		String beforeStr = "staffName=" + staffName + "&" + "rptCode=" + rptCode + "&" + "year=" + year + "&" + "month="
				+ month;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		String paramValue = "{\"staffName\":\"" + staffName + "\"," + "\"rptCode\":\"" + rptCode + "\"," + "\"year\":\""
				+ year + "\"," + "\"month\":\"" + month + "\"," + "\"sign\":\"" + sign + "\"}";

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
		if (je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			fthwReportRYsostjList = g.fromJson(jetemp, new TypeToken<List<FthwReportRYsostjData>>() {}.getType());
		}

		if (fthwReportRYsostjList != null && !fthwReportRYsostjList.isEmpty()) {
			int fthwReportRYsostjListSize = fthwReportRYsostjList.size();
			String[] sqls = new String[fthwReportRYsostjListSize];// 最终sql

			FthwReportRYsostjData one = null;
			for (int i = 0; i < fthwReportRYsostjListSize; i++) {
				one = fthwReportRYsostjList.get(i);
				// 插入人员SOS统计sql

				sqls[i] = " insert into data_futai_rysos_report (dept_name,staff_name,staff_phone,send_count,create_time "
						+ " ) values ('" + one.getDeptName() + "','" + one.getStaffName() + "','" + one.getStaffPhone()
						+ "'," + one.getSendCount() + ",now())";
			}
			if (sqls.length > 0) {
				jdbcTemplate.batchUpdate(sqls);
			}
		}

		System.out.println("环卫-人员SOS统计-执行完毕!" + " 耗时:  " + (System.currentTimeMillis() - startLong) / 1000 + "秒");
	}

	//清扫作业
	
	/**
	 * 路段作业日报表
	 * 1.4 ZYQS_LDZYRBB
	 */
	public void getFthwReportLDZYRBBTJTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 报表编码 必填
		String rptCode = "ZYQS_LDZYRBB";
		String key = "key=incomtest";// 密钥
		// 时间 yyyy-MM-dd
		String date = sdf.format(new Date());

		String result = "没有结果";

		List<FthwReportLDZYRBData> fthwReportLDZYRBList = null;// 路段作业日报

		String beforeStr = "date=" + date + "&" + "rptCode=" + rptCode;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();

		String paramValue = "{\"date\":\"" + date + "\"," + "\"rptCode\":\"" + rptCode + "\"," + "\"sign\":\"" + sign + "\"}";

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
		if (je_temp2 != null && !je_temp2.isJsonNull()) {
			JsonArray jetemp = je_temp2.getAsJsonArray();
			fthwReportLDZYRBList = g.fromJson(jetemp, new TypeToken<List<FthwReportLDZYRBData>>() {}.getType());
		}

		if (fthwReportLDZYRBList != null && !fthwReportLDZYRBList.isEmpty()) {
			int fthwReportLDZYRBListSize = fthwReportLDZYRBList.size();
			String[] sqls = new String[fthwReportLDZYRBListSize];// 最终sql

			FthwReportLDZYRBData one = null;
			for (int i = 0; i < fthwReportLDZYRBListSize; i++) {
				one = fthwReportLDZYRBList.get(i);
				

//				sqls[i] = " insert into data_futai_rysos_report (dept_name,staff_name,staff_phone,send_count,create_time "
//						+ " ) values ('" + one.get + "','" + one.get + "','" + one.getStaffPhone()
//						+ "'," + one.getSendCount() + ",now())";
			}
			if (sqls.length > 0) {
				jdbcTemplate.batchUpdate(sqls);
			}
		}

		System.out.println("环卫-人员SOS统计-执行完毕!" + " 耗时:  " + (System.currentTimeMillis() - startLong) / 1000 + "秒");
	}
	
	
	
	public static void main(String[] args) throws SQLException {
		FthwReportQjyTask test = new FthwReportQjyTask();
		test.getFthwReportLDZYRBBTJTask();

	}

}
