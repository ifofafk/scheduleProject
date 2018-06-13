package cn.com.jxTec.schedulePro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.jxTec.schedulePro.entity.User;

//import com.sky.common.http.HTTPUtils;

public class SessionFilter implements HandlerInterceptor {

	// private static final String includeips =
	// "10.73.2.216,127.0.0.1,0:0:0:0:0:0:0:1,10.73.39.74";

	private static final Map<String, Boolean> ips = new HashMap<String, Boolean>() {
		{
			put("10.73.2.216", true);
			put("127.0.0.1", true);
			put("0:0:0:0:0:0:0:1", true);
			put("10.73.39.74", true);
			put("10.73.225.178", true);
			put("10.73.225.182", true);
		}
	};

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		HashMap<String, Object> result = new HashMap<>();
		// //开放url是否和mvc:exclue-mapping是重叠的
		String returnUrl = request.getRequestURL().toString();

		// 测试阶段,排除对swagger的拦截
		User user = (User) request.getSession().getAttribute("userinfo");
		if (user == null) {
			// swagger的碧油鸡: swagger的开放条件(下面4个是swagger配置的bug)
			if (returnUrl.contains("swagger") || returnUrl.contains("api-docs") || returnUrl.contains("ResultGson")
					|| returnUrl.contains("PagePublicData") || returnUrl.contains("yjcx/queryYjxxList")
					|| returnUrl.contains("wall")) {
				return true;
			}
			// result.put("success", false);
			// result.put("status", UserEmun.SESSION_EXPIRES.getCode());
			// result.put("msg", "'Login Out Time!'");
			// PrintWriter out = response.getWriter();
			// out.append(result.toString().replaceAll("=", ":"));
			// return false;
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查是否是非法请求
	 * 
	 * @param response
	 * @param user
	 * @param returnUrl
	 * @return
	 * @throws IOException
	 */
	private boolean checkUserRequest(HttpServletResponse response, Object user, String returnUrl) throws IOException {
		if (null == user) {
			if (returnUrl.indexOf("longon") >= 0 || returnUrl.indexOf("home") >= 0 || returnUrl.indexOf("wall") >= 0) {
				return true;
			} else {
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				builder.append("alert(\"非法请求，请登录!\");");
				// TODO 返回登录页面，重定向过去
				
//				builder.append("window.location.href=\"/schedulePro/login.jsp\";");
				builder.append("</script>");
				out.print(builder.toString());
				out.close();
				return false;
			}
		}
		return true;
	}

	/**
	 * @param request
	 * @return Create Date:2013-6-5
	 * @author Shine Description:获取用户ip地址
	 */
	/*
	 * private String getIpAddr(HttpServletRequest request) { String ip =
	 * request.getHeader("x-forwarded-for"); if (ip == null || ip.length() == 0
	 * || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("Proxy-Client-IP"); } if (ip == null || ip.length() ==
	 * 0 || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("WL-Proxy-Client-IP"); } if (ip == null || ip.length()
	 * == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getRemoteAddr(); }
	 * return ip; }
	 */

}
