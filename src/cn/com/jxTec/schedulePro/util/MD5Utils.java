package cn.com.jxTec.schedulePro.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* @author ChenWang:
* @version 创建时间：2018年4月26日 下午8:11:12
* 类说明
*/
public class MD5Utils {

	/*** 
     * MD5加码 生成32位md5码 
     * ！智能加密英文和数字，不能加密中文。
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
   
    
    //MD5加密
    public  static String md5String (String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result_array = md.digest(str.getBytes());
            result = new String(result_array);


        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

        return result;
    }
    
    /*
     * 解决汉字MD5加密乱码
     * 注意编码集，固废车辆 伏泰的是gbk
     * 20180511
     */
    public static String md5(String str,String charsetName) {
    	   String result = "";
    	   MessageDigest md5 = null;
    	   try {
    	      md5 = MessageDigest.getInstance("MD5");
    	      md5.update(str.getBytes(charsetName));
    	   } catch (NoSuchAlgorithmException e) {
    	      e.printStackTrace();
    	   } catch (UnsupportedEncodingException e) {
    	      e.printStackTrace();
    	   }
    	   byte b[] = md5.digest();
    	   int i;
    	   StringBuffer buf = new StringBuffer("");
    	   for (int offset = 0; offset < b.length; offset++) {
    	      i = b[offset];
    	      if (i < 0)
    	         i += 256;
    	      if (i < 16)
    	         buf.append("0");
    	      buf.append(Integer.toHexString(i));
    	   }
    	   result = buf.toString();

    	   return result;
    	}
}
