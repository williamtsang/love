
package help.mygod.weixin.client.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.util.RestUtil;


/**
 * 第二步：通过code换取网页授权access_token（与基础的access_token不同）
 * @author Administrator
 *
 */
public class AccessTokenClient{
	private static Logger logger = LogManager.getLogger(AccessTokenClient.class);
	/**
	 * API地址
	 */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ Constant.APP_ID + "&secret=" + Constant.APP_SECRET + "&code=%1$s&grant_type=authorization_code";

	public static AccessTokenRsp get(String code) {
		String url = String.format(ACCESS_TOKEN_URL, code);
		
		
		if (logger.isInfoEnabled()) {
			logger.info("第二步：通过code换取网页授权access_token（与基础的access_token不同）");
		}
		String temp = RestUtil.get(url,String.class);
		return JSONObject.parseObject(temp, AccessTokenRsp.class);
	}
	
}
