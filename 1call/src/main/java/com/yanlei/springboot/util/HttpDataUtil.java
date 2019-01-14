package com.yanlei.springboot.util;

import org.apache.commons.lang3.CharSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @ClassName httpData.java
 * @Description:TODO
 * @author x
 * @date 2017-12-19下午4:16:51
 *
 */
public class HttpDataUtil {
	/**
	 * 调用HTTP接口 POST方式
	 * @Description: TODO
	 * @param @param httpURL URL地址
	 * @param @param mapper  map集合 key是接口接收的key value是传的值
	 * @param @throws Exception
	 * @param mapper
	 * @return String 接口返回的值
	 * @throws
	 * @author x
	 * @date 2017-12-19
	 */
	public static String httpPOST(String httpURL, HashMap<String, String> mapper) throws Exception {
		//请求的webservice的url
		URL url = new URL(httpURL);
		//创建http链接
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		//设置请求的方法类型
		httpURLConnection.setRequestMethod("POST");
		//设置请求的内容类型
		//httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		// 设置通用的请求属性
		httpURLConnection.setRequestProperty("accept", "*/*");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		//设置发送数据
		httpURLConnection.setDoOutput(true);
		//设置接受数据
		httpURLConnection.setDoInput(true);
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setReadTimeout(5000);
		//发送数据,使用输出流
		OutputStream outputStream = httpURLConnection.getOutputStream();
		//发送的soap协议的数据
		//String requestXmlString = requestXml("北京");
		StringBuffer sbf = new StringBuffer();
		for (Entry<String, String> e : mapper.entrySet()) {
			sbf.append("&"+e.getKey()+"="+ URLEncoder.encode(e.getValue(), "utf-8"));

		}
		//发送数据
		outputStream.write(sbf.toString().substring(1, sbf.length()).getBytes());
		//接收数据
		InputStream inputStream = httpURLConnection.getInputStream();
		//定义字节数组
		byte[] b = new byte[1024];
		//定义一个输出流存储接收到的数据
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		//开始接收数据
		int len = 0;
		while (true) {
			len = inputStream.read(b);
			if (len == -1) {
				//数据读完
				break;
			}
			byteArrayOutputStream.write(b, 0, len);
		}

		//从输出流中获取读取到数据(服务端返回的)
		String response = byteArrayOutputStream.toString();

		return response;
	}
	/**
	 * 调用HTTP接口 GET方式
	 * @Description: TODO
	 * @param @param httpURL URL地址
	 * @param @param mapper  map集合 key是接口接收的key value是传的值
	 * @param @throws Exception
	 * @return String 接口返回的值
	 * @throws
	 * @author x
	 * @date 2017-12-19
	 * 会返回乱码不用!! 使用httpGet2乱码处理
	 */
	public static String JsonSMSPost(String postData, String postUrl) {
		System.out.println(postData);
		try {
			//发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();
			//获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("connect failed!");
				return "";
			}
			//获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return "";
	}



	public static String httpGet(String httpURL, Map<String,String> mapper) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = httpURL ;
			StringBuffer sbf = new StringBuffer();
			if(mapper!=null){
				for (Entry<String, String> e : mapper.entrySet()) {
					sbf.append("&"+e.getKey()+"="+ URLEncoder.encode(e.getValue(), "utf-8"));

				}
				if(sbf.length()!=0){
					urlNameString = httpURL+ "?" + sbf.toString().substring(1, sbf.length());
				}
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				//System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


}