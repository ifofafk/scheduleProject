package cn.com.jxTec.schedulePro.dao.imp;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.jxTec.schedulePro.entity.User;
import cn.com.jxTec.schedulePro.entity.UserCenterPage;
import cn.com.jxTec.schedulePro.util.PageSqlUtils;

public abstract class AbstractDao {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Resource(name = "qkcgJdbcTemplate")
	private JdbcTemplate qkcgJdbcTemplate;

	/**
	 * 主键锁
	 */
	private static final Object PRIMARY_LOCK = new Object();

	public void add(String sqlStr) {
		logger.info("执行操作：" + sqlStr);
		qkcgJdbcTemplate.execute(sqlStr);
	}

	public void add(String sqlStr, Object[] params, int[] types) {
		logger.info("执行操作：" + sqlStr);
		qkcgJdbcTemplate.update(sqlStr, params, types);
	}

	public void update(JdbcTemplate qkcgJdbcTemplate, String drczno) {

	}

	public void updateState(String sql) {
		qkcgJdbcTemplate.update(sql);
	}

	public int update(String updSql, Object[] param, int[] types) {
		logger.info("执行更新操作：" + updSql);
		int count = qkcgJdbcTemplate.update(updSql, param, types);
		return count;
	}

	public int delete(String delSql, Object[] param, int[] types) {
		logger.info("执行删除操作：" + delSql);
		return update(delSql, param, types);
	}

	@SuppressWarnings("rawtypes")
	public List queryForList(String sqlStr) {
		logger.info("执行操作：" + sqlStr);
		List list = qkcgJdbcTemplate.queryForList(sqlStr);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List queryForList(String sqlStr, Object[] paramArray, int[] typeArray) {
		logger.info("执行操作：" + sqlStr);
		List list = qkcgJdbcTemplate.queryForList(sqlStr, paramArray, typeArray);
		return list;
	}

	public String beforeImportSave(User userinfo) {
		return "";
	}

	// 布控结果的列表查询
	public UserCenterPage controlList(HttpSession httpSession, HttpServletRequest request, String sql, Object[] args,
			int[] argTypes) {
		// 总页数
		int page = request.getParameter("page") == null ? 1 : new Integer(request.getParameter("page"));
		//
		int pageSize = request.getParameter("pageSize") == null ? 10 : new Integer(request.getParameter("pageSize"));
		// 查询部分字段用于显示页面
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		logger.info("执行操作：" + totalSQL);
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, args, argTypes, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString, args, argTypes);
		userCenterPage.setMapList(list);
		return userCenterPage;
	}

	public UserCenterPage controlList(HttpSession httpSession, Integer page, Integer pageSize, String sql,
			Object[] args, int[] argTypes) {
		// 查询部分字段用于显示页面
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		logger.info("执行操作：" + totalSQL);
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, args, argTypes, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString, args, argTypes);
		userCenterPage.setMapList(list);
		return userCenterPage;
	}

	// 布控结果的列表查询
	public UserCenterPage controlList(HttpSession httpSession, HttpServletRequest request, Integer page,
			Integer pageSize, String sql) {

		// 查询部分字段用于显示页面
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString);
		userCenterPage.setMapList(list);
		return userCenterPage;
	}

	public UserCenterPage controlYjxxList(Integer page, Integer pageSize, String sql) {

		// 查询部分字段用于显示页面
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString);
		userCenterPage.setMapList(list);
		return userCenterPage;
	}

	// 布控结果的列表查询
	public UserCenterPage controlWifiList(HttpServletRequest request, String sql) {
		int page = request.getParameter("page") == null ? 1 : new Integer(request.getParameter("page"));
		int pageSize = request.getParameter("pageSize") == null ? 10 : new Integer(request.getParameter("pageSize"));
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString);
		userCenterPage.setMapList(list);
		return userCenterPage;
	}

	// 布控结果的列表查询
	public UserCenterPage controlList(HttpServletRequest request, String sql) {
		int page = request.getParameter("page") == null ? 1 : new Integer(request.getParameter("page"));
		int pageSize = request.getParameter("pageSize") == null ? 10 : new Integer(request.getParameter("pageSize"));
		UserCenterPage userCenterPage = UserCenterPage.getInstance(page, pageSize);
		String totalSQL = "select count(0) from (" + sql + ")";
		Integer listTotal = qkcgJdbcTemplate.queryForObject(totalSQL, Integer.class);
		userCenterPage.setAllRecordNO(listTotal);
		// 封装总页数
		Integer allPageNumber = listTotal / pageSize + (listTotal % pageSize == 0 ? 0 : 1);
		userCenterPage.setAllPageNO(allPageNumber);
		String sqlString = PageSqlUtils.createPageSql(sql, page, pageSize);
		logger.info("获取数据SQL ：" + sqlString);
		List<Map<String, Object>> list = qkcgJdbcTemplate.queryForList(sqlString);
		userCenterPage.setMapList(list);
		return userCenterPage;
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

	public String searchRyxm(String zkno) {
		String searchSql = "select ryxm,rysfzh from  t_zk_ryxxb where zkno ='" + zkno + "'";
		Map<String, Object> map = qkcgJdbcTemplate.queryForMap(searchSql);
		String ryxm = map.get("RYXM") + "";
		String sfzh = map.get("RYSFZH") + "";
		return ryxm + " 身份证号 : " + sfzh;
	}

	public String searchRedRyxm(String rlno) {
		String searchSql = "select name ,idcard from T_ZK_RedList where rlno ='" + rlno + "'";
		Map<String, Object> map = qkcgJdbcTemplate.queryForMap(searchSql);
		String ryxm = map.get("NAME") + "";
		String sfzh = map.get("IDCARD") + "";
		return ryxm + " 身份证号 : " + sfzh;
	}
}
