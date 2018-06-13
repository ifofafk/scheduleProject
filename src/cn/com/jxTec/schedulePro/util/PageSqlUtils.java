package cn.com.jxTec.schedulePro.util;

/**
 * 分页
 * @author Administrator
 *
 */
public class PageSqlUtils {

	public static String createPageSql(String sql, Integer page, Integer pageSize) {
		
		int begin = (page - 1) * pageSize;
		int end = page * pageSize;
		StringBuffer sb = new StringBuffer();
		// sb.append(" select * from (select t.*,rownum r from
		// (").append(sql).append(") t ").append(") where r>")
		// .append(begin).append(" and r<=").append(end);

		sb.append(" select t.* from (").append(sql).append(") ").append(" where rownum>").append(begin)
				.append(" and rownum<=").append(end);
		return sb.toString();
	}
}