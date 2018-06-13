/**
 * 
 */
package cn.com.jxTec.schedulePro.filter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.jxTec.schedulePro.entity.User;

/**
 * 描述：Session属性操作监听者
 * 
 * @author Sky
 * 
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {

	/**
	 */
	private static Log log = LogFactory.getLog(SessionAttributeListener.class);

	/**
	 * 
	 */
	public SessionAttributeListener() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent httpsessionbindingevent) {
		if (isUser(httpsessionbindingevent)) {
//			User user = (User) httpsessionbindingevent.getValue();
//			OnLineUserManager.getIntance().addOnLine(user);
//			initLoginLog(httpsessionbindingevent, user, 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent httpsessionbindingevent) {
		if (isUser(httpsessionbindingevent)) {
//			User user = (User) httpsessionbindingevent.getValue();
//			OnLineUserManager.getIntance().removeOnLine(user);
//			initLoginLog(httpsessionbindingevent, user, 2);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent httpsessionbindingevent) {
		if (isUser(httpsessionbindingevent)) {

		}
	}

	/**
	 * 是否用户属性修改：属性名称是用户并且不是系统管理员
	 * 
	 * @param httpsessionbindingevent
	 * @return
	 */
	protected boolean isUser(HttpSessionBindingEvent httpsessionbindingevent) {
		boolean result = false;
		String name = httpsessionbindingevent.getName();
		try {
			result = "userinfo".equals(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 
	 * TODO 初始化登录与退出信息
	 * 
	 * @author Jul 5, 2012 3:14:28 PM ZQ
	 * @param user
	 * @throws Exception
	 * 
	 */
	private void initLoginLog(HttpSessionBindingEvent httpsessionbindingevent, User user, int type) {
		HttpServletRequest request = null;
		String ip = null;
		String host = null;
		String port = null;
		try {
			// 从容器里拿到session及request
			try {
//				request = ServletActionContext.getRequest();
//				ip = HTTPUtils.getRemoteAddr(request);
				host = request.getServerName();
				port = String.valueOf(request.getServerPort());
			} catch (NullPointerException nullE) {
				ip = "";
				host = "";
				port = "";
			}

//			LoginLog ll = new LoginLog(user.getAccount(), user.getAccount(), new Date(), type, ip, host, port);
//			LoginLogJob.addLoginLog(ll);
			// LoginLogDao lld = new LoginLogDao();
			// lld.add(ll);
		} catch (Exception e) {
			log.error("记录登录信息出错：", e);
		}
	}

	protected boolean isLogin(ServletRequestEvent requestEvent) {
		boolean result = false;
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		String uri = request.getRequestURI();
		int index = uri.indexOf("main/login_");
		if (index > 0) {
			log.debug("------>" + request.getRequestURL() + " " + request.getRequestURI());
			result = true;
		}
		return result;
	}
}
