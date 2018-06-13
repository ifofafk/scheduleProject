package cn.com.jxTec.schedulePro.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PubMethod {

	/**
	 * 获取uuid不含-
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 带前缀的uuid(不含-)
	 * 
	 * @param prefix
	 * @return
	 */
	public static String getUUID(String prefix) {
		return prefix + getUUID();
	}

	/**
	 * 根据请求获取ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getCallerIpMe(HttpServletRequest request) {
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
	 * 获取14个区的区名
	 * 
	 * @param key
	 *            6位区号前缀
	 * @return
	 */
	public static Object getDistrict(String key) {
		Map<String, String[]> districts = new HashMap<String, String[]>();

		districts.put("420102", new String[] { "武汉市公安局江岸区分局", "江岸分局" });
		districts.put("420103", new String[] { "武汉市公安局江汉区分局", "江汉分局" });
		districts.put("420104", new String[] { "武汉市公安局硚口区分局", "硚口分局" });
		districts.put("420105", new String[] { "武汉市公安局汉阳区分局", "汉阳分局" });
		districts.put("420106", new String[] { "武汉市公安局武昌区分局", "武昌分局" });
		districts.put("420107", new String[] { "武汉市公安局青山区分局", "青山分局" });
		districts.put("420111", new String[] { "武汉市公安局洪山区分局", "洪山分局" });
		districts.put("420112", new String[] { "武汉市公安局东西湖区分局", "东西湖分局" });
		districts.put("420115", new String[] { "武汉市公安局江夏区分局", "江夏分局" });
		districts.put("420116", new String[] { "武汉市公安局黄陂区分局", "黄陂分局" });
		districts.put("420117", new String[] { "武汉市公安局新洲区分局", "新洲分局" });
		districts.put("420184", new String[] { "武汉经济技术开发区分局", "经开分局" });
		districts.put("420185", new String[] { "东湖新技术开发区分局", "东湖分局" });
		districts.put("420114", new String[] { "武汉市公安局蔡甸分局", "蔡甸分局" });
		districts.put("420100000000", new String[] { "武汉市公安局", "市局" });
		if (StringUtils.isBlank(key)) {
			return districts;
		}
		return districts.get(key);
	}


	
	

	
	/**
	 * 获取session
	 * @return
	 */
	public static HttpSession getSession(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getSession();
	}
	

	/**
	 * 字符串数组转字符串
	 * @param arrays
	 * @return
	 */
	public static  String arrayToStr(String[] arrays)
	{
		String newStr = "";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<arrays.length;i++)
		{
			sb.append(arrays[i]+"_");
		}
		newStr = sb.toString();
		return newStr;
	}
	/**
	 * 合并数组
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] mergeArray(String[] array1,String[] array2)
	{
		int strlen1 = array1.length;
		int strlen2 = array2.length;
		array1 = Arrays.copyOf(array1, strlen1+strlen2);
		System.arraycopy(array2, 0, array1, strlen1, strlen2);
		return array1;
	}
	
	/**
	 * 基于身份证最后一位的校验
	 * @param sfzh
	 * @author WangChen
	 * @return
	 */
	public static boolean IDCardValidate(String sfzh){
		
		if(StringUtils.isBlank(sfzh)) {
			return false;
		}
		
		if(sfzh.length() != 18 || !sfzh.matches("^\\d{17}([0-9]|X|x)$")){
			return false;
		}
		//1-17位相乘因子数组
		int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
		
		//18位随机码数组
		char[] random = "10X98765432".toCharArray();
		
		//计算1-17位与相应因子乘积之和
		int total = 0;
		for(int i = 0; i < 17; i++) {
			total += Character.getNumericValue(sfzh.charAt(i)) * factor[i];
		}
		//判断随机码是否相等
		return random[total % 11] == sfzh.charAt(17);
	}
	
	
	//String.format工具
	/**
	 * 
	 */
	public static void main(String[] args) {

		
//		Date date = new Date();
//		String dateFormat = String.format("%tF %tT ", date, date);
//		System.out.println(dateFormat);
		
		double n = 3.1465926;
		String test = String.format("%1$.2f", n); 
		System.out.println(test);
		
		String sc = "1.525397345954E12";
		sc = sc.replace("E12", "");
		sc = sc.replace(".", "");
		long  aaa = Long.valueOf(sc);
		Date adate = new Date(aaa);
		
		System.out.println(String.format("%tF %tT ", adate,adate));
	}
	
}
