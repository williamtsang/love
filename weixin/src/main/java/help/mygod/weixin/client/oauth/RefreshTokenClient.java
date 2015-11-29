
package help.mygod.weixin.client.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.util.RestUtil;


/**
 * 第三步：刷新access_token（如果需要）
 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后，需要用户重新授权
 * @author Administrator
 *
 */
public class RefreshTokenClient{
	private static Logger logger = LogManager.getLogger(RefreshTokenClient.class);
	/**
	 * API地址
	 */
	public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+ Constant.APP_ID + "&grant_type=refresh_token&refresh_token=%1$s";

	public static RefreshTokenRsp get(String refresh_token) {
		String url = String.format(REFRESH_TOKEN_URL, refresh_token);
		
		
		if (logger.isInfoEnabled()) {
			logger.info("第三步：刷新access_token（如果需要）");
		}

		String temp = RestUtil.get(url,String.class);
		return JSONObject.parseObject(temp, RefreshTokenRsp.class);
	}
	
}
