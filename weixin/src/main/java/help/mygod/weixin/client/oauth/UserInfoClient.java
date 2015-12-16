
package help.mygod.weixin.client.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import help.mygod.weixin.common.util.RestUtil;


/**
 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了
 * @author Administrator
 *
 */
public class UserInfoClient{
	private static Logger logger = LogManager.getLogger(UserInfoClient.class);
	/**
	 * API地址
	 */
	public final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%1$s&openid=%2$s&lang=zh_CN";

	public static UserInfoRsp get(String access_token,String openid) {
		String url = String.format(USER_INFO_URL, access_token,openid);
		
		
		if (logger.isInfoEnabled()) {
			logger.info("第四步：拉取用户信息(需scope为 snsapi_userinfo)");
		}

		return RestUtil.get(url,UserInfoRsp.class);
	}
	
}
