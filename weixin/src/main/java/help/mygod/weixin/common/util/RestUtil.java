package help.mygod.weixin.common.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
		return c.target(url).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.text(jsonString),clazz);
	}

	/**
	 * Post Form
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	/*public static Response postForm(String url, Form form) {
		if (log.isInfoEnabled()) {
			log.info("开始请求调用外部接口 地址为[" + url + "],参数为[" + StrUtil.reflectObj(form) + "]");
		}
		return c.target(url).request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
	}*/

	/**
	 * Get Text
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T get(String url, Class<T> clazz) {
		return c.target(url).request().accept(MediaType.TEXT_PLAIN).get(clazz);
	}

	/**
	 * Get Json
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static <T> T getJson(String url, Class<T> clazz) {
		return c.target(url).request().accept(MediaType.APPLICATION_JSON).get(clazz);
	}

	/**
	 * Post XML
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	/*public static Response postXML(String url, String xmlString) {
		return c.target(url).request(MediaType.APPLICATION_XML).post(Entity.text(xmlString));
	}*/

	/**
	 * Post text
	 * 
	 * @param url
	 * @param jsonString
	 * @return
	 */
	/*public static Response postText(String url, String text) {
		return c.target(url).request(MediaType.TEXT_XML).accept(MediaType.TEXT_XML).post(Entity.text(text));
	}*/

}
