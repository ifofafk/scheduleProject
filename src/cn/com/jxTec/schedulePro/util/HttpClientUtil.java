package cn.com.jxTec.schedulePro.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by kecoo on 2017/6/2.
 */
public class HttpClientUtil {
    private static int connectionTimeout = 10000;// 连接超时时间,10秒
    private static int soTimeout = 100000;// 读取数据超时时间，100秒
 
    /** HttpClient对象 */
    private static CloseableHttpClient httpclient = HttpClients.
            custom().disableAutomaticRetries().build();
   
    /*** 超时设置 ****/
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(soTimeout)
            .setConnectTimeout(connectionTimeout)
            .build();//设置请求和传输超时时间
    
    
    
    /**
     * 根据给定的URL地址和参数字符串，以Get方法调用，如果成功返回true，如果失败返回false
     *
     * @param url
     *            String url地址，不含参数
     * @param paramKey
     *            String 参数字符串，例如：a=
     * @param paramValue
     *            String 参数字符串，例如：{"sign":"9713519A4935CB9280D687D261D08801"}
     * @throws UnsupportedEncodingException 
     * @author ChenWang
     * 为了解决明文调用api出现  Illegal character in query错误而用
     */
   final public static String executeGetMethod4API(String url, String paramKey, String paramJsonValue) throws UnsupportedEncodingException {
        String strResult = "";
        StringBuffer serverURL = new StringBuffer(url);
        if (paramJsonValue != null && paramJsonValue.length() > 0) {
            serverURL.append("?");
            serverURL.append(paramKey);
            String encode = URLEncoder.encode(paramJsonValue, "UTF-8");
            serverURL.append(encode);
        }
        
        HttpGet httpget = new HttpGet(serverURL.toString());
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response=null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            int iGetResultCode = response.getStatusLine().getStatusCode();
            if (iGetResultCode >= 200 && iGetResultCode < 303) {
                strResult = EntityUtils.toString(entity);
            } else if (iGetResultCode >= 400 && iGetResultCode < 500) {
                strResult = "请求的目标地址不存在：" + iGetResultCode;
            } else {
                strResult = "请求错误：" + iGetResultCode;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(response !=null){
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
   
   final static public String executePostMethod4API(String strURL, Map<String, String> param) {
       String strResult = "";
       HttpPost post;
       post = new HttpPost(strURL);
       post.setConfig(requestConfig);
       List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>(param.size());
       for (Entry<String, String> pEntry : param.entrySet()) {
           if(null != pEntry.getValue()){
               BasicNameValuePair nv = new BasicNameValuePair(pEntry.getKey(), pEntry.getValue());
               paraList.add(nv);
           }
       }
       //使用UTF-8
       UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paraList, Charset.forName("utf-8"));
       post.setEntity(entity);
       CloseableHttpResponse response=null;
       try {
           response = httpclient.execute(post);
           int iGetResultCode = response.getStatusLine().getStatusCode();
           if (HttpStatus.SC_OK == iGetResultCode) {
               HttpEntity responseEntity = response.getEntity();
               strResult = EntityUtils.toString(responseEntity);
           }

       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {
           try {
               if(response!=null) response.close();
           } catch (IOException e) {
           }
       }
       return strResult;
   }

    
    /**
     * 根据给定的URL地址和参数字符串，以Get方法调用，如果成功返回true，如果失败返回false
     *
     * @param url
     *            String url地址，不含参数
     * @param param
     *            String 参数字符串，例如：a=1&b=2&c=3
     * @throws UnsupportedEncodingException 
     */
   final public static String executeGetMethod(String url, String param) throws UnsupportedEncodingException {
        String strResult = "";
        StringBuffer serverURL = new StringBuffer(url);
        if (param != null && param.length() > 0) {
            serverURL.append("?");
            serverURL.append("parameters=");
            String encode = URLEncoder.encode(param, "UTF-8");
            serverURL.append(encode);
        }
        System.out.println("请求完整路径为:" + serverURL);
        
        HttpGet httpget = new HttpGet(serverURL.toString());
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response=null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            int iGetResultCode = response.getStatusLine().getStatusCode();
            if (iGetResultCode >= 200 && iGetResultCode < 303) {
                strResult = EntityUtils.toString(entity);
            } else if (iGetResultCode >= 400 && iGetResultCode < 500) {
                strResult = "请求的目标地址不存在：" + iGetResultCode;
            } else {
                strResult = "请求错误：" + iGetResultCode;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(response !=null){
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    /**
     * 根据给定的URL地址和参数字符串，以POST方法调用，如果成功返回true，如果失败返回false
     *
     * @param strURL
     *            String url地址，不含参数
     * @param param
     *            Map<String, Object> 参数字表单
     * @return boolean true－成功，false失败，如果返回成功可以getStrGetResponseBody()
     *         获取返回内容字符串，如果失败，则可访问getErrorInfo()获取错误提示。
     */
    final static public String executePostMethod(String strURL, Map<String, String> param) {
        String strResult = "";
        HttpPost post;
        post = new HttpPost(strURL);
        post.setConfig(requestConfig);
        List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>(param.size());
        for (Entry<String, String> pEntry : param.entrySet()) {
            if(null != pEntry.getValue()){
                BasicNameValuePair nv = new BasicNameValuePair(pEntry.getKey(), pEntry.getValue());
                paraList.add(nv);
            }
        }
        //使用UTF-8
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paraList, Charset.forName("utf-8"));
        post.setEntity(entity);
        CloseableHttpResponse response=null;
        try {
            response = httpclient.execute(post);
            int iGetResultCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == iGetResultCode) {
                HttpEntity responseEntity = response.getEntity();
                strResult = EntityUtils.toString(responseEntity);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(response!=null) response.close();
            } catch (IOException e) {
            }
        }
        return strResult;
    }

   
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        http://10.73.93.215/search/usergrantSearch.jsp
// ?query=/search/archive_archiveIndex.action@archiveId=1$archiveMark=173405179481122259@420103198001010512
        String url ="http://10.73.93.215/search/usergrantSearch.jsp" ;
        String param ="/search/archive_archiveIndex.action@archiveId=1$archiveMark=173405179481122259@420103197106292019" ;
        DesCryptUtil des = null;
        String query = null ;
        try {
            des = new DesCryptUtil("sungoalLoginGrant");
            query=des.encrypt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(query);
        String string =HttpClientUtil.executeGetMethod(url,"query="+query);
        System.out.println(string);
    }

}
