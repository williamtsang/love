package help.mygod.weixin.common.util;

import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 使用Http Client 进行接口数据请求类;
 * @author RD_guowei_liu
 *
 */
public class HttpClientUtil {
	
	/**
	 * 使用HttpClient 
	 *  get请求页面并返回json格式数据.
	 * 对方接收的也是json格式数据。
	 * @throws Exception 
	 * 
	 * */
	public static JSONObject doGet(String url) throws Exception  {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		JSONObject json = null;
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				json = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));
			}
		} catch (Exception e) {
			throw e;
			
		} finally{
			//关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
		}
		return json;
	}
	
	public static void main(String[] args) throws JSONException, Exception {
		JSONObject json = new JSONObject();  
		//要传递的参数.
//		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=211.144.76.15" ;
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=192.168.22.92" ;
		//拼接路径字符串将参数包含进去
		json = doGet(url);
		System.out.println(json.get("code"));
		System.out.println(json.get("data"));
//		System.out.println(json.getJSONObject("data").get("city"));
		
	}
}
