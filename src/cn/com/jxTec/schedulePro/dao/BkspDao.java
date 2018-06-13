package cn.com.jxTec.schedulePro.dao;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface BkspDao {
	/**
	 * 查询自已待审批智控名单
	 * 
	 * @param
	 * @return
	 */
//	UserCenterPage getMeRequestZkData(BkListQueryDTO data, User user, Integer page, Integer pageSize);

	Map<String, Object> queryByZkno(String zkno);

	// 单个布控的状态修改
	int singleShenPi(HttpSession httpSession, String zkno, String state, Integer spjb);

	void saveWifi(String zkno);

	void insertSPBLog(String spdSql);

}
