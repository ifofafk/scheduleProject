package cn.com.jxTec.schedulePro.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HttpUtil {

	
	/**
	 * @Description: httpclient的post请求方式
	 * @author zhujinlong
	 * @param url http请求路径
	 * @param json 请求参数
	 * @param encode 请求编码
	 * @return
	 */
	public static String doPost(String strurl, String json, String encode, String paramType)
	{
		//HttpClient client = new DefaultHttpClient();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URL url = null;
		HttpPost post = null;
		StringBuffer content = new StringBuffer();
		try
		{
			url = new URL(strurl);
			URI uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
			post = new HttpPost(uri);
			StringEntity entity = new StringEntity(json, encode);
			if ("xml".equals(paramType))
			{
				entity.setContentType("text/xml;charset=" + encode);
			}else{
				entity.setContentType("application/json;charset=" + encode);
			}
			post.setEntity(entity);
			HttpResponse res = httpClient.execute(post);
			HttpEntity resEntity = res.getEntity();
			InputStream in = resEntity.getContent();
			if (resEntity != null)
			{
				//这里的编码规则要与上面的相对应
				BufferedReader br = new BufferedReader(new InputStreamReader(in, encode));
				String tempbf;
				while ((tempbf = br.readLine()) != null)
				{
					content.append(tempbf + "\n");
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			post.releaseConnection();
		}
		return content.toString();
	}
	
	
	/**
	 * 将文件转成base64 字符串
	 * @param path  文件路径
	 * @return
	 */
	public static String encodeBase64File(String path){
		String result = null;
		File file = new File(path);
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int)file.length()];
				inputFile.read(buffer);
			result = new BASE64Encoder().encode(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 将base64字符流解码保存目标位置
	 * @param base64Code  base64位字符流
	 * @param targetPath  目标路径(如 D:/232.xls)
	 */
	public static void decodeBase64File(String base64Code,String targetPath) {
		byte[] buffer = null;
		FileOutputStream out = null;
		try {
//			byte[] buffer = base64Code.getBytes();目标路径(如 D:/232.txt)这种可以保存为文本
			buffer = new BASE64Decoder().decodeBuffer(base64Code);
			if(buffer != null){
				out = new FileOutputStream(targetPath);
				out.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static String postSysLogInfo(String msg,String urlPath) throws MalformedURLException, IOException, ProtocolException {
		HttpURLConnection httpURLConnection = null;
		URL serviceUrl = new URL(urlPath);
		httpURLConnection = (HttpURLConnection) serviceUrl.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1;SV1)");
		httpURLConnection.setRequestProperty("Content-Length", msg.getBytes("UTF-8").length + "");
		httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		httpURLConnection.connect();
		OutputStream outputStream =  httpURLConnection.getOutputStream();
		byte[] dataBytes = msg.getBytes("UTF-8");
		outputStream.write(dataBytes);
		outputStream.flush();
		outputStream.close();
		
		int code = httpURLConnection.getResponseCode();
		String message = httpURLConnection.getResponseMessage();
		httpURLConnection.getResponseMessage();
		InputStream in = httpURLConnection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String tempbf;
		StringBuffer content = new StringBuffer(); 
		while ((tempbf = br.readLine()) != null)
		{
			content.append(tempbf + "\n");
		}
		System.out.println("----------------------");
		System.out.println("content : " +content.toString());
		System.out.println(" code : " +code);
		System.out.println("message :" +message);
		return content.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
}
