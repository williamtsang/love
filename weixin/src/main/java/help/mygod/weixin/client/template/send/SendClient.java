package help.mygod.weixin.client.template.send;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;
import help.mygod.weixin.common.util.StrUtil;

/**
 * 发送模板信息
 * 
 * @author Administrator
 * 
 */
public class SendClient {
	private static Logger logger = LogManager.getLogger(SendClient.class);
	/**
	 * API地址
	 */
	public final static String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	public static void post(TemplateMessage templateMessage) throws HttpException, IOException {
		if (logger.isInfoEnabled()) {
			logger.info("发送模板信息，请求的参数为:" + StrUtil.reflectObj(templateMessage));
		}

		RestUtil.postJson(TEMPLATE_SEND_URL + AccessTokenClient.getInstance().getAccessToken(),
						JSONObject.toJSONString(templateMessage),SendRsp.class);

	}

}
