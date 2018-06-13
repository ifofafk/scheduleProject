package cn.com.jxTec.schedulePro.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 主键锁
	 */
	private static final Object PRIMARY_LOCK = new Object();
	
	/**
	 * 当前时间添加月份数
	 * @param amount
	 * @return
	 */
	public static String getDate(int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH,amount);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	/**
	 * 获取主键
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized static long getPrimaryID() throws Exception {
		synchronized (PRIMARY_LOCK) {
			long id = 0;
			id = Calendar.getInstance().getTimeInMillis();
			return id;
		}
	}
}
