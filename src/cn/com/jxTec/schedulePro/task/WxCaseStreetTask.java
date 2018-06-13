package cn.com.jxTec.schedulePro.task;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.jxTec.schedulePro.entity.FtCarInfo;

/**
 * @author ChenWang:
 * @version 创建时间：2018年6月11日 下午4:00:32 类说明
 */
public class WxCaseStreetTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 处理微信平台过来的案件，将案件经纬度处理成街道划分
	 */
	public void handleStreet() {
		List<String> sqls = new ArrayList<>();
		// 查询微信案件
		String sql = " select * from data_lilosoft_caseno where case_source ='微信公众号' ";
		List<FtCarInfo> ftCars = jdbcTemplate.query(sql, new Object[] {},
				new BeanPropertyRowMapper<FtCarInfo>(FtCarInfo.class));

		// 处理后为街道

		if (StringUtils.isNotBlank(sql)) {
			sqls.add(sql);
		}
		jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
	}

	/**
	 * 通过jdk的一些类判断 没有测过所有极限情况，不知道是不是完全准
	 * 
	 * @param point
	 * @param polygon
	 * @return
	 */

	public static boolean checkWithJdkPolygon(Point2D.Double point, List<Point2D.Double> polygon) {
		java.awt.Polygon p = new Polygon();
		// java.awt.geom.GeneralPath
		final int TIMES = 1000;
		for (Point2D.Double d : polygon) {
			int x = (int) d.x * TIMES;
			int y = (int) d.y * TIMES;
			p.addPoint(x, y);
		}
		int x = (int) point.x * TIMES;
		int y = (int) point.y * TIMES;
		return p.contains(x, y);
	}

	/**
	 * 写个斐波那契数列玩玩
	 * 
	 * @param max
	 *            分裂的次数
	 * @param args
	 */
	public static int fib(int max) {
		int result = 0;
		if (max <= 2) {
			result = 1;
		} else {
			result = fib(max - 1) + fib(max - 2);
		}
		return result;
	}

	public static void main(String[] args) {
		/*
		 * //测试 checkWithJdkPolygon //赋值 x y坐标 硚口区政府附近114.214876,30.582406
		 * Point2D.Double point = new Point2D.Double(30.582406, 114.214876);
		 * 
		 * 
		 * //赋值 指定区域的坐标集合 List<Point2D.Double> polygon = new ArrayList<>(); //
		 * double tempX = 30.582406; // double tempY = 114.214876; //
		 * Point2D.Double temp = null; // for(int i = 1; i<11; i++) { // tempX =
		 * tempX + 0.1*i; // tempY = tempY + 0.1*i; // temp = new
		 * Point2D.Double(tempX, tempY); // polygon.add(temp); // }
		 * 
		 * Point2D.Double temp1 = new Point2D.Double(30.58357, 114.21964);
		 * Point2D.Double temp2 = new Point2D.Double(30.582462, 114.222633);
		 * Point2D.Double temp3 = new Point2D.Double(30.582157, 114.223395);
		 * Point2D.Double temp4 = new Point2D.Double(30.579635, 114.218417);
		 * polygon.add(temp1); polygon.add(temp2); polygon.add(temp3);
		 * polygon.add(temp4);
		 * 
		 * 
		 * System.out.println(checkWithJdkPolygon(point,polygon));
		 */

		System.out.println(fib(17));
	}

}
