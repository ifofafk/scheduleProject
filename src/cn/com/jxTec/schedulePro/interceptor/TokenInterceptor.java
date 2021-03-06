package cn.com.jxTec.schedulePro.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	private final Logger logger = Logger.getLogger(getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if(annotation!=null){
				boolean needSaveSession = annotation.save();
				if(needSaveSession){
					request.getSession(true).setAttribute("token", UUID.randomUUID().toString());
				}
				boolean needRemoveSession = annotation.remove();
				if(needRemoveSession){
					if(isRepeatSubmit(request)){
						logger.warn("不能重复提交，url:"+request.getServletPath());
						return false;
					}
					request.getSession(true).removeAttribute("token");
				}
			}
			return true;
		}else{
			return super.preHandle(request, response, handler);
		}
	}
	
	/**
	 * 是否重复提交
	 * @param request
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request){
		String serverToken  = (String)request.getSession(true).getAttribute("Token");
		if(serverToken==null){
			return true;
		}
		String clientToken = request.getParameter("token");
		if(clientToken==null){
			return true;
		}
		if(!serverToken.equals(clientToken)){
			return true;
		}
		return false;
	}
}
