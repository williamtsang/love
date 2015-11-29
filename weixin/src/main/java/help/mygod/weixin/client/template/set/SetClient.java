package help.mygod.weixin.client.template.set;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;
import help.mygod.weixin.common.util.StrUtil;

/**
 * 
 *设置所属行业
 * @author Administrator
 *
 */
public class SetClient {
	private static Logger logger = LogManager.getLogger(SetClient.class);
	/**
	 * API地址
	 */
	public final static String TEMPLATE_SET_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=";
	

	public static void post(Industry industry) throws HttpException, IOException{
		if(logger.isInfoEnabled()){
			logger.info("设置所属行业，请求的参数为:" + StrUtil.reflectObj(industry));
		}
		RestUtil.postJson(TEMPLATE_SET_URL + AccessTokenClient.getInstance().getAccessToken(), JSONObject.toJSONString(industry),String.class);
	}
}
