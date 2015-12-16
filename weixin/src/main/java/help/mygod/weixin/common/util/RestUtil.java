package help.mygod.weixin.common.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestUtil {

	private static final Logger log = LogManager.getLogger(RestUtil.class.getName());

	private static final Client c = ClientBuilder.newClient();

	/**
	 * Post JSON
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T postJson(String url, String jsonString,Class<T> clazz) {
		return c.target(url).request(MediaType.APPLICATION_JSON)
				.post(Entity.text(jsonString),clazz);
	}
	public static String postJson(String url, String jsonString) {
		return postJson(url, jsonString,String.class);
	}
	public static <E,T> T postJson(String url, E entity,Class<T> clazz) {
		return c.target(url).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(entity, MediaType.APPLICATION_JSON),clazz);
	}
	public static <T> String postJson(String url, T entity) {
		return postJson(url, entity,String.class);
	}

	/**
	 * Post Form
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T postForm(String url, Form form,Class<T> clazz) {
		if (log.isInfoEnabled()) {
			log.info("开始请求调用外部接口 地址为[" + url + "],参数为[" + StrUtil.reflectObj(form) + "]");
		}
		return c.target(url).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED),clazz);
	}
	public static String postForm(String url, Form form) {
		return postForm(url, form,String.class);
	}


	/**
	 * Post XML
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T postXML(String url, String xmlString, Class<T> clazz) {
		return c.target(url).request(MediaType.APPLICATION_XML).post(Entity.text(xmlString),clazz);
	}
	public static String postXML(String url, String xmlString) {
		return postXML(url, xmlString, String.class);
	}

	/**
	 * Post text
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T postText(String url, String text,Class<T> clazz) {
		return c.target(url).request(MediaType.TEXT_XML).post(Entity.text(text),clazz);
	}
	public static String postText(String url, String text) {
		return postText(url, text, String.class);
	}
	

	/**
	 * Get Text
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T get(String url, Class<T> clazz) {
		return c.target(url).request(MediaType.TEXT_PLAIN).get(clazz);
	}
	public static String get(String url) {
		return get(url, String.class);
	}

	/**
	 * Get Json
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T getJson(String url, Class<T> clazz) {
		return c.target(url).request(MediaType.APPLICATION_JSON).get(clazz);
	}
	public static String getJson(String url) {
		return get(url, String.class);
	}

	public static void main(String[] args) {
		Form form = new Form();
		form.param("bizType", "user_apply");
		form.param("name", "13670202723");
		//System.out.println(get("http://172.20.11.91/sms.do?action=sms_dynamic_code&bizType=user_apply&name=13670202723",String.class));
		String url = "http://172.20.11.91/sms.do?action=sms_dynamic_code";
		System.out.println(postForm(url, form));
	}
}
