package cn.com.jxTec.schedulePro.task;

import java.io.UnsupportedEncodingException;
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

import cn.com.jxTec.schedulePro.dto.FthwCarHistoryAlarmDTO;
import cn.com.jxTec.schedulePro.dto.FthwOilAnalysisDTO;
import cn.com.jxTec.schedulePro.entity.FthwReportCarHistoryAlarmData;
import cn.com.jxTec.schedulePro.entity.FthwReportCarRunDayData;
import cn.com.jxTec.schedulePro.entity.FthwReportOilAnalysisData;
import cn.com.jxTec.schedulePro.util.HttpClientUtil;
import cn.com.jxTec.schedulePro.util.MD5Utils;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月2日 下午3:34:57 类说明
 * 硚口环卫车辆-报表
 */
public class FthwReportCarTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	// -------------------------------------以下为环卫车辆的报表-----------------------------------//
	/*
	 * 报表-查询报警报表记录 
	 */
	public void getFthwReportCarHistoryAlarmTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f
		// 报表编码 必填，固定值GPS_ALARM
		String rptCode = "GPS_ALARM";
		// 日期 必填，yyyy-MM-dd .没传默认今天
		String dateValue_EQ = sdf.format(new Date());

		// 车辆ID 选填，多个用逗号隔开
		// carId_IN = carId_IN != null? carId_IN : "";

		String key = "key=incomtest";// 密钥

		// 报警类型 必填，
		/*
		 * overSpeed:超速汇总报表,overLineSpeed:分段超速,overStopTime:停车超时,
		 * overLine:越线,overAreaIn:禁入越界,overAreaOut:禁出越界,overAreaSpeed:分区超速
		 */
		List<String> alarmTypes = new ArrayList<>();
		alarmTypes.add("overSpeed");
		alarmTypes.add("overLineSpeed");
		alarmTypes.add("overStopTime");
		alarmTypes.add("overLine");
		alarmTypes.add("overAreaIn");
		alarmTypes.add("overAreaOut");
		alarmTypes.add("overAreaSpeed");

		FthwCarHistoryAlarmDTO fthwCarHistoryAlarmDTO = null;
		String result = "没有结果";

		List<FthwReportCarHistoryAlarmData> fthwhistoryAlarms = null;// 返回结果

		// 报警类型分类查询
		String alarmType_EQ = "";// 将来可以指定
		if (StringUtils.isBlank(alarmType_EQ)) {
			for (String alarmType_EQ_temp : alarmTypes) {

				fthwCarHistoryAlarmDTO = new FthwCarHistoryAlarmDTO();
				String beforeStr = "userId=" + userId + "&" + "rptCode=" + rptCode + "&" + "dateValue_EQ="
						+ dateValue_EQ + "&" + "alarmType_EQ=" + alarmType_EQ_temp;
				String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
				fthwCarHistoryAlarmDTO.setUserId(userId);
				fthwCarHistoryAlarmDTO.setRptCode(rptCode);
				fthwCarHistoryAlarmDTO.setDateValue_EQ(dateValue_EQ);

				fthwCarHistoryAlarmDTO.setAlarmType_EQ(alarmType_EQ_temp);
				fthwCarHistoryAlarmDTO.setSign(sign);
				String paramValue = fthwCarHistoryAlarmDTO.toString();

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
				if(je_temp2 != null && !je_temp2.isJsonNull()) {
					JsonArray jetemp = je_temp2.getAsJsonArray();
					fthwhistoryAlarms = g.fromJson(jetemp, new TypeToken<List<FthwReportCarHistoryAlarmData>>() {
					}.getType());
					
				}

				if (fthwhistoryAlarms != null && !fthwhistoryAlarms.isEmpty()) {
					int fthwhistoryAlarmsSize = fthwhistoryAlarms.size();
					String[] sqls = new String[fthwhistoryAlarmsSize];// 最终sql
					
					FthwReportCarHistoryAlarmData one = null;
					for (int i = 0; i < fthwhistoryAlarmsSize; i++) {
						one = fthwhistoryAlarms.get(i);
						
						sqls[i] = " insert into data_futai_alarm_history_report (beginAddress,"
								+ " endAddress,alarmBeginTime,alarmEndTime,carTypeName,speedLimitingValveSize, "
								+ " distance,workElementName,companyName,maxSpeed,alarmLevelName,avgSpeed, "
								+ " carCode,overtime,driverName,driverPhone,stipulatedTime,createtime "
								+ " ) " + " values ('"
								+ one.getBeginAddress() + "','" + one.getEndAddress() + "','" + one.getAlarmBeginTime() + "','" + one.getAlarmEndTime()
								+ "','" + one.getCarTypeName() + "','" + one.getSpeedLimitingValveSize() + "','" + one.getDistance() + "','" 
								+ one.getWorkElementName() + "','" + one.getCompanyName() + "'," + one.getMaxSpeed() + ",'" + one.getAlarmLevelName()
								+ "','" + one.getAvgSpeed() +  "','" + one.getCarCode() + "'," + one.getOvertime() + ",'" + one.getDriverName() 
								+ "','" + one.getDriverPhone() + "'," + one.getStipulatedTime() + ",now()) ";
					}
					if(sqls.length > 0){
						jdbcTemplate.batchUpdate(sqls);
					}
				}

			}
			System.out.println("环卫车辆-查询报警报表记录-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		}
	}

	/*
	 * ----报表√ 环卫车辆-查询运行日报表记录
	 */
	public void getFthwReportCarRunDayTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = System.currentTimeMillis();

		// 查询上一步获取好的carIds
		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f
		// 报表编码 必填，固定值GPS_ALARM
		String rptCode = "GPS_RUNDAYRPT";
		String key = "key=incomtest";// 密钥

		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String dateValue_GTE = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String dateValue_LTE = sdf.format(nowDate);

		String result = "没有结果";

		FthwOilAnalysisDTO fthwOilAnalysisDTO = new FthwOilAnalysisDTO();
		String beforeStr = "userId=" + userId + "&" + "rptCode=" + rptCode + "&" + "dateValue_GTE=" + dateValue_GTE
				+ "&" + "dateValue_LTE=" + dateValue_LTE;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		fthwOilAnalysisDTO.setUserId(userId);
		fthwOilAnalysisDTO.setRptCode(rptCode);
		fthwOilAnalysisDTO.setDateValue_GTE(dateValue_GTE);
		fthwOilAnalysisDTO.setDateValue_LTE(dateValue_LTE);
		fthwOilAnalysisDTO.setSign(sign);
		String paramValue = fthwOilAnalysisDTO.toString();
		System.out.println(paramValue);

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		// 返回结果
		Gson g = new Gson();
		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("rows");
		JsonArray jetemp = je_temp2.getAsJsonArray();
		List<FthwReportCarRunDayData> fthwReportCarRunDayData = g.fromJson(jetemp,
				new TypeToken<List<FthwReportCarRunDayData>>() {
				}.getType());

		if (fthwReportCarRunDayData != null && !fthwReportCarRunDayData.isEmpty()) {

			int fthwReportCarRunDayDataSize = fthwReportCarRunDayData.size();
			String[] sqls = new String[fthwReportCarRunDayDataSize];// 最终sql
			StringBuffer sql = null;
			;
			for (int i = 0; i < fthwReportCarRunDayDataSize; i++) {
				sql = new StringBuffer();
				sql.append(" insert into data_futai_report_run_day (create_time,trackNum,companyName,"
						+ " stopNum,lastestFireAddress,allUseOil,carCode,"
						+ " allRunTime,stopTime,earliestFireAddress,mileage");

				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getEarliestFireTime())) {
					sql.append(" ,earliestFireTime ");
				}
				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getLastestFireTime())) {
					sql.append(" ,lastestFireTime ");
				}
				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getRunDay())) {
					sql.append(" ,runDay ");
				}
				sql.append(")");

				sql.append(" values (now()," + fthwReportCarRunDayData.get(i).getTrackNum() + ",'"
						+ fthwReportCarRunDayData.get(i).getCompanyName() + "',"
						+ fthwReportCarRunDayData.get(i).getStopNum() + ",'"
						+ fthwReportCarRunDayData.get(i).getLastestFireAddress() + "',"
						+ fthwReportCarRunDayData.get(i).getAllUseOil() + ",'"
						+ fthwReportCarRunDayData.get(i).getCarCode() + "',"
						+ fthwReportCarRunDayData.get(i).getAllRunTime() + ","
						+ fthwReportCarRunDayData.get(i).getStopTime() + ",'"
						+ fthwReportCarRunDayData.get(i).getLastestFireAddress() + "','"
						+ fthwReportCarRunDayData.get(i).getMileage() + "'");

				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getEarliestFireTime())) {
					sql.append(",str_to_date('" + fthwReportCarRunDayData.get(i).getEarliestFireTime()
							+ "','%Y-%m-%d %H:%i:%s')");
				}
				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getLastestFireTime())) {
					sql.append(",str_to_date('" + fthwReportCarRunDayData.get(i).getLastestFireTime()
							+ "','%Y-%m-%d %H:%i:%s')");
				}
				if (StringUtils.isNotBlank(fthwReportCarRunDayData.get(i).getRunDay())) {
					sql.append(",str_to_date('" + fthwReportCarRunDayData.get(i).getRunDay() + "','%Y-%m-%d') ");
				}
				sql.append(" ) ");

				sqls[i] = sql.toString();
			}
			
			if(sqls.length > 0){
				jdbcTemplate.batchUpdate(sqls);
			}
			System.out.println("环卫车辆-查询运行日报表记录-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		}

	}

	/*
	 * 报表 环卫车辆--查询车辆百公里油耗分析表记录
	 */
	public void getFthwReportCarOilKmTask() {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f
		// 报表编码 必填，固定值GPS_TIMECOMPARE
		String rptCode = "GPS_MILEAGECOMPARE";

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String dateValue_GTE = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String dateValue_LTE = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";

		FthwOilAnalysisDTO fthwOilAnalysisDTO = new FthwOilAnalysisDTO();
		String beforeStr = "userId=" + userId + "&" + "rptCode=" + rptCode + "&" + "dateValue_GTE=" + dateValue_GTE
				+ "&" + "dateValue_LTE=" + dateValue_LTE;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		fthwOilAnalysisDTO.setUserId(userId);
		fthwOilAnalysisDTO.setRptCode(rptCode);
		fthwOilAnalysisDTO.setDateValue_GTE(dateValue_GTE);
		fthwOilAnalysisDTO.setDateValue_LTE(dateValue_LTE);
		fthwOilAnalysisDTO.setSign(sign);
		String paramValue = fthwOilAnalysisDTO.toString();
		System.out.println(paramValue);

		Gson g = new Gson();
		List<FthwReportOilAnalysisData> fthwReportOilAnalysisList = null;// 返回结果

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("rows");
		JsonArray jetemp = je_temp2.getAsJsonArray();
		fthwReportOilAnalysisList = g.fromJson(jetemp, new TypeToken<List<FthwReportOilAnalysisData>>() {
		}.getType());

		if (fthwReportOilAnalysisList != null && !fthwReportOilAnalysisList.isEmpty()) {

			int fthwReportOilAnalysisListSize = fthwReportOilAnalysisList.size();
			String[] sqls = new String[fthwReportOilAnalysisListSize];// 最终sql

			for (int i = 0; i < fthwReportOilAnalysisListSize; i++) {
				sqls[i] = " insert into data_futai_oil_report (car_code,driver,company_name,sum_oil_use,"
						+ " car_grade_name,car_classes_name,sum_time,create_time,report_name) " + " values ('"
						+ fthwReportOilAnalysisList.get(i).getCarCode() + "','"
						+ fthwReportOilAnalysisList.get(i).getDriver() + "','"
						+ fthwReportOilAnalysisList.get(i).getCompanyName() + "',"
						+ fthwReportOilAnalysisList.get(i).getSumOilUse() + ",'"
						+ fthwReportOilAnalysisList.get(i).getCarGradeName() + "','"
						+ fthwReportOilAnalysisList.get(i).getCarClassesName() + "',"
						+ fthwReportOilAnalysisList.get(i).getSumTime() + ",now(),'车辆百公里油耗分析表') ";

			}
			if(sqls.length > 0){
				jdbcTemplate.batchUpdate(sqls);
			}
			System.out.println("环卫车辆-车辆百公里油耗分析表-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");
		}

	}

	/*
	 * === 报表 √==== 环卫车辆-查询车辆油耗单位分析表
	 */
	public void getFthwFuelAnalysisTask() {
		long startLong = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String url = "http://219.140.178.212:9391/cloud/qk/api/np/v1/reportInfo/getReportData.smvc";
		String paramKey = "parameters=";
		// 用户id 必填
		String userId = "8be9ec8714bc4c909a054454a7bb5a3f";// 8be9ec8714bc4c909a054454a7bb5a3f
		// 报表编码 必填，固定值GPS_TIMECOMPARE
		String rptCode = "GPS_TIMECOMPARE";

		// 开始日期 必填，yyyy-MM-dd 没有，默认昨天 参数可变。但是Only no-arg methods may be
		// annotated with @Scheduled
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		String dateValue_GTE = sdf.format(cal.getTime());
		// 结束日期 必填，yyyy-MM-dd 默认今天
		String dateValue_LTE = sdf.format(nowDate);
		String key = "key=incomtest";// 密钥

		String result = "没有结果";

		FthwOilAnalysisDTO fthwOilAnalysisDTO = new FthwOilAnalysisDTO();
		String beforeStr = "userId=" + userId + "&" + "rptCode=" + rptCode + "&" + "dateValue_GTE=" + dateValue_GTE
				+ "&" + "dateValue_LTE=" + dateValue_LTE;
		String sign = MD5Utils.string2MD5(beforeStr + "&" + key).toUpperCase();
		fthwOilAnalysisDTO.setUserId(userId);
		fthwOilAnalysisDTO.setRptCode(rptCode);
		fthwOilAnalysisDTO.setDateValue_GTE(dateValue_GTE);
		fthwOilAnalysisDTO.setDateValue_LTE(dateValue_LTE);
		fthwOilAnalysisDTO.setSign(sign);
		String paramValue = fthwOilAnalysisDTO.toString();
		System.out.println(paramValue);

		Gson g = new Gson();
		List<FthwReportOilAnalysisData> fthwReportOilAnalysisList = null;// 返回结果

		try {
			// 获取返回值
			result = HttpClientUtil.executeGetMethod4API(url, paramKey, paramValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(result);
		}

		JsonElement je_temp1 = new JsonParser().parse(result);
		JsonElement je_temp2 = je_temp1.getAsJsonObject().get("data").getAsJsonObject().get("rows");
		JsonArray jetemp = je_temp2.getAsJsonArray();
		fthwReportOilAnalysisList = g.fromJson(jetemp, new TypeToken<List<FthwReportOilAnalysisData>>() {
		}.getType());

		if (fthwReportOilAnalysisList != null && !fthwReportOilAnalysisList.isEmpty()) {

			int fthwReportOilAnalysisListSize = fthwReportOilAnalysisList.size();
			String[] sqls = new String[fthwReportOilAnalysisListSize];// 最终sql

			for (int i = 0; i < fthwReportOilAnalysisListSize; i++) {
				sqls[i] = " insert into data_futai_oil_report (car_code,driver,company_name,sum_oil_use,"
						+ " car_grade_name,car_classes_name,sum_time,create_time,report_name) " + " values ('"
						+ fthwReportOilAnalysisList.get(i).getCarCode() + "','"
						+ fthwReportOilAnalysisList.get(i).getDriver() + "','"
						+ fthwReportOilAnalysisList.get(i).getCompanyName() + "',"
						+ fthwReportOilAnalysisList.get(i).getSumOilUse() + ",'"
						+ fthwReportOilAnalysisList.get(i).getCarGradeName() + "','"
						+ fthwReportOilAnalysisList.get(i).getCarClassesName() + "',"
						+ fthwReportOilAnalysisList.get(i).getSumTime() + ",now(),'车辆油耗单位分析表') ";
			}
			if(sqls.length > 0){
				jdbcTemplate.batchUpdate(sqls);
			}
			System.out.println("环卫车辆-车辆油耗分析报表-执行完毕!" + " 耗时:  " + (System.currentTimeMillis()-startLong)/1000 +"秒");

		}

	}


}
