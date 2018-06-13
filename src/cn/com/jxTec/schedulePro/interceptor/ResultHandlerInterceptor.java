package cn.com.jxTec.schedulePro.interceptor;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import cn.com.jxTec.schedulePro.entity.JsonResultModel;

/**
 * 对接口返回结果进行处理
 * @author qmsy
 *
 */
public class ResultHandlerInterceptor extends HandlerInterceptorAdapter {
	//时间格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	private static  Map<String,String>  ipMap = new HashMap<String,String>();
	
//	static{
//		ipMap.put("0:0:0:0:0:0:0:1", "0"); //本机
//		ipMap.put("127.0.0.1", "0"); //本机
//		ipMap.put("10.73.93.50", "0"); //本机

//		ipMap.put("10.73.2.119", "0");
//		ipMap.put("10.73.2.174", "0");
//		ipMap.put("10.73.2.146", "0");
//		ipMap.put("10.73.2.40", "0");
//		ipMap.put("10.73.41.106", "0"); //安保机子
//		
//	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		LoginUserDTO objLoginUser = UserSessionVO.get(request.getSession());
		Map<String, Object> result = new HashMap<String, Object>();
		String path = request.getServletPath();//获取请求url
//		User user = (User)request.getSession().getAttribute("userinfo");
			//存入日志
			if(!path.startsWith("/swagger")) {
				if(modelAndView == null) {
					return;
				}
				Map<String, Object> model = modelAndView.getModel();
				HandlerMethod method = (HandlerMethod) handler;
					if (JsonResultModel.class.getName().equals(method.getMethod().getReturnType().getName())) {
						result.putAll((Map<String, Object>) getData(model, method));
					}else{
					result.put("success", true);
					result.put("code", JsonResultModel.SUCCESS_CODE);
					result.put("time", sdf.format(new Date()));
					result.put("data", getData(model, method));
				}
					modelAndView.setView(new MappingJackson2JsonView());
					modelAndView.getModel().clear();
					modelAndView.addAllObjects(result);
			}
		
	}
	
	//获取数据
	private Object getData(Map<String, Object> model, HandlerMethod handler) {
		Map<String, Object> result = new HashMap<String, Object>();
		String tmpKey = null;
		Object tmpValue = null;
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			tmpKey = entry.getKey();
			tmpValue = entry.getValue();
			if (tmpKey.startsWith("org.springframework")) {
				continue;
			} else if (handler.getReturnType().getMethod().getReturnType().getName().contains("Map")) {
				result.put(tmpKey, tmpValue);
			} else if (tmpValue instanceof JsonResultModel) {
				JsonResultModel newModel = (JsonResultModel) tmpValue;
				return newModel.getMap();
			} else {
				return tmpValue;
			}
		}
		return result;
	}
	
	/**
	 * 获取访问者ip
	 * @param request
	 * @return
	 * @author yuqm
	 */
	public static String getCallerIp(HttpServletRequest request) {
        String ip = null;
        
       if (request != null) {
           ip = request.getHeader("x-forwarded-for");
	       if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("Proxy-Client-IP");
	       }
	       if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	              ip = request.getRemoteAddr();
	       }
       }
        return ip;
    }
	
	/**
	 * 获得请求的报文,并作简单的校验
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String getInJson(HttpServletRequest request) throws IOException {

		byte buffer[] = new byte[64 * 1024];
		InputStream in = request.getInputStream();// 获取输入流对象

		int len = in.read(buffer);
		// 必须对数组长度进行判断，否则在new byte[len]会报NegativeArraySizeException异常
		if (len < 0) {
			throw new IOException("请求报文为空");
		}

		String encode = request.getCharacterEncoding();// 获取请求头编码
		// 必须对编码进行校验,否则在new String(data, encode);会报空指针异常
		if (null == encode || encode.trim().length() < 0) {
			throw new IOException("请求报文未指明请求编码");
		}

		byte data[] = new byte[len];

		// 把buffer数组的值复制到data数组
		System.arraycopy(buffer, 0, data, 0, len);

		// 通过使用指定的 charset 解码指定的 byte 数组，构造一个新的 String
		String inJson = new String(data, encode);

		return inJson;
	}

	
	
}

